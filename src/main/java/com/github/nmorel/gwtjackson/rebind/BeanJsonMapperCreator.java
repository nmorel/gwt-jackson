package com.github.nmorel.gwtjackson.rebind;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.SourceWriter;

/** @author Nicolas Morel */
public class BeanJsonMapperCreator extends AbstractJsonMapperCreator
{

    public BeanJsonMapperCreator( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle )
    {
        super( logger, context, typeOracle );
    }

    /**
     * Creates an implementation of {@link com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper} for the type given in
     * parameter
     *
     * @param beanName name of the bean
     * @return the fully qualified name of the created class
     * @throws com.google.gwt.core.ext.UnableToCompleteException
     */
    public String create( String beanName ) throws UnableToCompleteException
    {
        JClassType beanType = typeOracle.getType( beanName );
        return create( beanType );
    }

    /**
     * Creates an implementation of {@link com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper} for the type given in
     * parameter
     *
     * @param beanType type of the bean
     * @return the fully qualified name of the created class
     * @throws com.google.gwt.core.ext.UnableToCompleteException
     */
    public String create( JClassType beanType ) throws UnableToCompleteException
    {
        // we concatenate the name of all the enclosing class
        StringBuilder builder = new StringBuilder( beanType.getSimpleSourceName() + "BeanJsonMapperImpl" );
        JClassType enclosingType = beanType.getEnclosingType();
        while ( null != enclosingType )
        {
            builder.insert( 0, enclosingType.getSimpleSourceName() + "_" );
            enclosingType = enclosingType.getEnclosingType();
        }

        String mapperClassSimpleName = builder.toString();
        String packageName = beanType.getPackage().getName();
        String qualifiedMapperClassName = packageName + "." + mapperClassSimpleName;

        PrintWriter printWriter = getPrintWriter( packageName, mapperClassSimpleName );
        // the class already exists, no need to continue
        if ( printWriter == null )
        {
            return qualifiedMapperClassName;
        }

        BeanInfo info = BeanInfo.process( logger, beanType, qualifiedMapperClassName, mapperClassSimpleName );

        SourceWriter source = getSourceWriter( printWriter, packageName, mapperClassSimpleName, info.getSuperclass() + "<" +
            beanType.getParameterizedQualifiedSourceName() + ", " + info.getInstanceBuilderQualifiedName() + ">" );

        writeClassBody( source, info );

        return qualifiedMapperClassName;
    }

    private void writeClassBody( SourceWriter source, BeanInfo info ) throws UnableToCompleteException
    {
        source.println();
        source.indent();

        List<PropertyInfo> properties;
        if ( info.isIgnoreAllProperties() )
        {
            logger.log( TreeLogger.Type.DEBUG, "Ignoring all properties of type " + info.getType() );
            properties = Collections.emptyList();
        }
        else
        {
            properties = findAllProperties( info );
        }

        if ( info.isInstantiable() )
        {
            generateInstanceBuilderClass( source, info, properties );
        }

        source.println();

        if ( info.isHasSubtypes() )
        {
            generateSubtypesMethods( source, info );
            source.println();
        }

        source.println( "@Override" );
        source.println( "protected %s newInstanceBuilder() {", info.getInstanceBuilderQualifiedName() );
        source.indent();
        generateNewInstanceBuilderBody( source, info );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source
            .println( "protected void initDecoders(java.util.Map<java.lang.String, %s<%s, %s>> decoders) {", DECODER_PROPERTY_BEAN_CLASS,
                info
                .getType().getParameterizedQualifiedSourceName(), info.getInstanceBuilderQualifiedName() );
        if ( !info.isIgnoreAllProperties() && info.isInstantiable() )
        {
            source.indent();
            generateInitDecoders( source, info, properties );
            source.outdent();
        }
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "protected void initEncoders(java.util.Map<java.lang.String, %s<%s>> encoders) {", ENCODER_PROPERTY_BEAN_CLASS, info
            .getType().getParameterizedQualifiedSourceName() );
        if ( !info.isIgnoreAllProperties() )
        {
            source.indent();
            generateInitEncoders( source, info, properties );
            source.outdent();
        }
        source.println( "}" );

        if ( info.isInstantiable() )
        {
            source.println();
            generateNewInstanceMethod( source, info, properties );
        }

        if ( !info.isIgnoreAllProperties() )
        {
            source.println();
            generateAdditionalMethods( source, properties );
        }

        source.outdent();
        source.commit( logger );
    }

    private void generateInstanceBuilderClass( SourceWriter source, BeanInfo info, List<PropertyInfo> properties ) throws
        UnableToCompleteException
    {
        source.println( "public static class %s implements %s<%s> {", info.getInstanceBuilderSimpleName(), INSTANCE_BUILDER_CLASS, info
            .getType().getParameterizedQualifiedSourceName() );
        source.indent();

        if ( null != info.getCreatorDefaultConstructor() )
        {
            generateInstanceBuilderClassBodyForDefaultConstructor( source, info, properties );
        }
        else
        {
            JAbstractMethod method = null != info.getCreatorConstructor() ? info.getCreatorConstructor() : info.getCreatorFactory();
            if ( method.getParameters().length == 1 && !method.getParameters()[0].isAnnotationPresent( JsonProperty.class ) )
            {
                generateInstanceBuilderClassBodyForConstructorOrFactoryMethodDelegation( source, info, properties, method );
            }
            else
            {
                generateInstanceBuilderClassBodyForConstructorOrFactoryMethod( source, info, properties, method );
            }
        }

        source.outdent();
        source.println( "}" );
    }

    /**
     * Generate the instance builder class body for a default constructor. We directly instantiate the bean at the builder creation and we
     * set the properties to it
     *
     * @param source writer
     * @param info info on bean
     * @param properties list of properties
     */
    private void generateInstanceBuilderClassBodyForDefaultConstructor( SourceWriter source, BeanInfo info, List<PropertyInfo> properties )
    {
        source.println();
        source.println( "private %s %s = %s.newInstance();", info.getType().getParameterizedQualifiedSourceName(), BEAN_INSTANCE_NAME, info
            .getQualifiedMapperClassName() );
        source.println();

        for ( PropertyInfo property : properties )
        {
            source.println( "private void _%s(%s value) {", property.getPropertyName(), property.getType()
                .getParameterizedQualifiedSourceName() );
            source.indent();
            source.println( property.getSetterAccessor() + ";", "value" );
            source.outdent();
            source.println( "}" );
            source.println();
        }

        source.println( "@Override" );
        source.println( "public %s build() {", info.getType().getParameterizedQualifiedSourceName() );
        source.indent();
        source.println( "return %s;", BEAN_INSTANCE_NAME );
        source.outdent();
        source.println( "}" );

        source.outdent();
        source.println( "}" );
    }

    /**
     * Generate the instance builder class body for a constructor with parameters or factory method. We will declare all the fields and
     * instanciate the bean only on build() method when all properties have been decoded
     *
     * @param source writer
     * @param info info on bean
     * @param properties list of properties
     * @param method constructor or factory method
     */
    private void generateInstanceBuilderClassBodyForConstructorOrFactoryMethod( SourceWriter source, BeanInfo info,
                                                                                List<PropertyInfo> properties, JAbstractMethod method )
    {
        for ( PropertyInfo property : properties )
        {
            source.println( "private %s _%s;", property.getType().getParameterizedQualifiedSourceName(), property.getPropertyName() );
        }

        source.println();

        for ( PropertyInfo property : properties )
        {
            source.println( "private void _%s(%s value) {", property.getPropertyName(), property.getType()
                .getParameterizedQualifiedSourceName() );
            source.indent();
            source.println( "this._%s = value;", property.getPropertyName() );
            source.outdent();
            source.println( "}" );
            source.println();
        }

        source.println( "@Override" );
        source.println( "public %s build() {", info.getType().getParameterizedQualifiedSourceName() );
        source.indent();

        StringBuilder parametersBuilder = new StringBuilder();
        for ( int i = 0; i < method.getParameters().length; i++ )
        {
            if ( i > 0 )
            {
                parametersBuilder.append( ", " );
            }
            parametersBuilder.append( "_" ).append( properties.get( i ).getPropertyName() );
        }
        source.println( "%s %s = %s.newInstance(%s);", info.getType().getParameterizedQualifiedSourceName(), BEAN_INSTANCE_NAME, info
            .getQualifiedMapperClassName(), parametersBuilder.toString() );
        for ( int i = method.getParameters().length; i < properties.size(); i++ )
        {
            PropertyInfo property = properties.get( i );
            source.println( property.getSetterAccessor() + ";", "this._" + property.getPropertyName() );
        }
        source.println( "return %s;", BEAN_INSTANCE_NAME );

        source.outdent();
        source.println( "}" );
    }

    /**
     * Generate the instance builder class body for a constructor or factory method with delegation.
     *
     * @param source writer
     * @param info info on bean
     * @param properties list of properties
     * @param method constructor or factory method
     */
    private void generateInstanceBuilderClassBodyForConstructorOrFactoryMethodDelegation( SourceWriter source, BeanInfo info,
                                                                                          List<PropertyInfo> properties,
                                                                                          JAbstractMethod method ) throws
        UnableToCompleteException
    {
        // FIXME @JsonCreator with delegation
        logger.log( TreeLogger.Type.ERROR, "The delegation is not supported yet" );
        throw new UnableToCompleteException();
    }

    private void generateSubtypesMethods( SourceWriter source, BeanInfo info ) throws UnableToCompleteException
    {
        // gives the information about how to read and write the type info
        if ( null != info.getTypeInfo() )
        {
            String typeInfoProperty = null;
            if ( JsonTypeInfo.As.PROPERTY.equals( info.getTypeInfo().include() ) )
            {
                typeInfoProperty = "\"" + (info.getTypeInfo().property().isEmpty() ? info.getTypeInfo().use()
                    .getDefaultPropertyName() : info.getTypeInfo().property()) + "\"";
            }

            source.println( "public %s() {", info.getMapperClassSimpleName() );
            source.indent();
            source.println( "super(com.fasterxml.jackson.annotation.JsonTypeInfo.As.%s, %s);", info.getTypeInfo()
                .include(), typeInfoProperty );
            source.outdent();
            source.println( "}" );
            source.println();
        }

        // tell if the class is instantiable
        source.println( "@Override" );
        source.println( "protected boolean isInstantiable() {" );
        source.indent();
        source.println( "return %s;", info.isInstantiable() );
        source.outdent();
        source.println( "}" );
        source.println();

        // generate the subtype mappers
        source.println( "@Override" );
        source.println( "protected void initSubtypeMappers() {" );
        source.indent();

        generateSubtypeMappers( source, info );

        source.outdent();
        source.println( "}" );
    }

    private void generateSubtypeMappers( SourceWriter source, BeanInfo info ) throws UnableToCompleteException
    {
        if ( info.isInstantiable() )
        {
            generateSubtypeMapper( source, info, info.getType() );
        }
        for ( JClassType subtype : info.getType().getSubtypes() )
        {
            generateSubtypeMapper( source, info, subtype );
        }
    }

    private void generateSubtypeMapper( SourceWriter source, BeanInfo info, JClassType subtype ) throws UnableToCompleteException
    {
        String typeInfo;
        if ( null == info.getTypeInfo() )
        {
            typeInfo = null;
        }
        else
        {
            switch ( info.getTypeInfo().use() )
            {
                case CLASS:
                    typeInfo = "\"" + subtype.getQualifiedBinaryName() + "\"";
                    break;
                case NAME:
                    String simpleBinaryName = subtype.getQualifiedBinaryName();
                    int indexLastDot = simpleBinaryName.lastIndexOf( '.' );
                    if ( indexLastDot != -1 )
                    {
                        simpleBinaryName = simpleBinaryName.substring( indexLastDot + 1 );
                    }
                    typeInfo = "\"" + simpleBinaryName + "\"";
                    break;
                default:
                    logger.log( TreeLogger.Type.ERROR, "JsonTypeInfo.Id." + info.getTypeInfo().use() + " is not supported" );
                    throw new UnableToCompleteException();
            }
        }

        String mapper = info.getType() == subtype ? info.getQualifiedMapperClassName() + ".this" : createMapperFromType( subtype );

        source.println( "addSubtypeMapper( new %s<%s>() {", SUBTYPE_MAPPER_CLASS, subtype.getQualifiedSourceName() );
        source.indent();

        source.println( "@Override" );
        source.println( "public %s decodeObject( %s reader, %s ctx ) throws java.io.IOException {", subtype
            .getQualifiedSourceName(), JSON_READER_CLASS, JSON_DECODING_CONTEXT_CLASS );
        source.indent();
        source.println( "return %s.decodeObject( reader, ctx );", mapper );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "public void encodeObject( %s writer, %s bean, %s ctx ) throws java.io.IOException {", JSON_WRITER_CLASS, subtype
            .getParameterizedQualifiedSourceName(), JSON_ENCODING_CONTEXT_CLASS );
        source.indent();
        source.println( "%s.encodeObject( writer, bean, ctx );", mapper );
        source.outdent();
        source.println( "}" );

        source.outdent();
        source.println( "}, %s.class, %s );", subtype.getQualifiedSourceName(), typeInfo );
        source.println();
    }

    private void generateNewInstanceBuilderBody( SourceWriter source, BeanInfo info )
    {
        if ( info.isInstantiable() )
        {
            source.println( "return new %s();", info.getInstanceBuilderQualifiedName() );
        }
        else
        {
            source.println( "throw new %s(\"Cannot instantiate the type \" + %s.class.getName());", JSON_DECODING_EXCEPTION_CLASS, info
                .getType().getQualifiedSourceName() );
        }
    }

    private void generateInitDecoders( SourceWriter source, BeanInfo info, List<PropertyInfo> properties ) throws UnableToCompleteException
    {
        for ( PropertyInfo property : properties )
        {
            String setterAccessor = property.getSetterAccessor();
            if ( null == setterAccessor )
            {
                continue;
            }

            source.println( "decoders.put(\"%s\", new " + DECODER_PROPERTY_BEAN_CLASS + "<%s, %s>() {", property.getPropertyName(), info
                .getType().getParameterizedQualifiedSourceName(), info.getInstanceBuilderQualifiedName() );

            source.indent();
            source.println( "@Override" );
            source.println( "public void decode(%s reader, %s builder, %s ctx) throws java.io.IOException {", JSON_READER_CLASS, info
                .getInstanceBuilderQualifiedName(), JSON_DECODING_CONTEXT_CLASS );
            source.indent();

            source.println( "builder._%s(%s);", property.getPropertyName(), String
                .format( "%s.decode(reader, ctx)", createMapperFromType( property.getType() ) ) );

            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "} );" );
        }
    }

    private void generateInitEncoders( SourceWriter source, BeanInfo info, List<PropertyInfo> properties ) throws UnableToCompleteException
    {
        for ( PropertyInfo property : properties )
        {
            String getterAccessor = property.getGetterAccessor();
            if ( null == getterAccessor )
            {
                continue;
            }

            source.println( "encoders.put(\"%s\", new " + ENCODER_PROPERTY_BEAN_CLASS + "<%s>() {", property.getPropertyName(), info
                .getType().getParameterizedQualifiedSourceName() );

            source.indent();
            source.println( "@Override" );
            source.println( "public void encode(%s writer, %s bean, %s ctx) throws java.io.IOException {", JSON_WRITER_CLASS, info.getType()
                .getParameterizedQualifiedSourceName(), JSON_ENCODING_CONTEXT_CLASS );
            source.indent();

            source.println( "%s.encode(writer, %s, ctx);", createMapperFromType( property.getType() ), getterAccessor );

            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "} );" );
        }
    }

    private void generateAdditionalMethods( SourceWriter source, List<PropertyInfo> properties )
    {
        for ( PropertyInfo property : properties )
        {
            for ( PropertyInfo.AdditionalMethod method : property.getAdditionalMethods() )
            {
                method.write( source );
                source.println();
            }
        }
    }

    private List<PropertyInfo> findAllProperties( BeanInfo info )
    {
        Map<String, FieldAccessors> fieldsMap = new LinkedHashMap<String, FieldAccessors>();
        parseFields( info.getType(), fieldsMap );
        parseMethods( info.getType(), fieldsMap );

        Map<String, PropertyInfo> propertiesMap = new LinkedHashMap<String, PropertyInfo>();
        for ( FieldAccessors field : fieldsMap.values() )
        {
            PropertyInfo property = PropertyInfo.process( field, info );
            if ( property.isIgnored() )
            {
                logger.log( TreeLogger.Type.DEBUG, "Ignoring field " + field.getFieldName() + " of type " + info.getType() );
            }
            else
            {
                propertiesMap.put( property.getPropertyName(), property );
            }
        }

        List<PropertyInfo> properties = new ArrayList<PropertyInfo>();

        // we first add the properties defined in order
        for ( String orderedProperty : info.getPropertyOrderList() )
        {
            // we remove the entry to have the map with only properties with natural or alphabetic order
            PropertyInfo property = propertiesMap.remove( orderedProperty );
            if ( null != property )
            {
                properties.add( property );
            }
        }

        // if the user asked for an alphbetic order, we sort the rest of the properties
        if ( info.isPropertyOrderAlphabetic() )
        {
            List<Map.Entry<String, PropertyInfo>> entries = new ArrayList<Map.Entry<String, PropertyInfo>>( propertiesMap.entrySet() );
            Collections.sort( entries, new Comparator<Map.Entry<String, PropertyInfo>>()
            {
                public int compare( Map.Entry<String, PropertyInfo> a, Map.Entry<String, PropertyInfo> b )
                {
                    return a.getKey().compareTo( b.getKey() );
                }
            } );
            for ( Map.Entry<String, PropertyInfo> entry : entries )
            {
                properties.add( entry.getValue() );
            }
        }
        else
        {
            properties.addAll( propertiesMap.values() );
        }

        return properties;
    }

    private void parseFields( JClassType type, Map<String, FieldAccessors> propertiesMap )
    {
        if ( null == type || type.getQualifiedSourceName().equals( "java.lang.Object" ) )
        {
            return;
        }

        for ( JField field : type.getFields() )
        {
            String fieldName = field.getName();
            FieldAccessors property = propertiesMap.get( fieldName );
            if ( null == property )
            {
                property = new FieldAccessors( fieldName );
                propertiesMap.put( fieldName, property );
            }
            if ( null == property.getField() )
            {
                property.setField( field );
            }
            else
            {
                // we found an other field with the same name on a superclass. we ignore it
                logger.log( TreeLogger.Type.WARN, "A field with the same name as " + field
                    .getName() + " has already been found on child class" );
            }
        }
        parseFields( type.getSuperclass(), propertiesMap );
    }

    private void parseMethods( JClassType type, Map<String, FieldAccessors> propertiesMap )
    {
        if ( null == type || type.getQualifiedSourceName().equals( "java.lang.Object" ) )
        {
            return;
        }

        for ( JMethod method : type.getMethods() )
        {
            if ( null != method.isConstructor() || method.isStatic() )
            {
                continue;
            }

            JType returnType = method.getReturnType();
            if ( null != returnType.isPrimitive() && JPrimitiveType.VOID.equals( returnType.isPrimitive() ) )
            {
                // might be a setter
                if ( method.getParameters().length == 1 )
                {
                    String methodName = method.getName();
                    if ( methodName.startsWith( "set" ) && methodName.length() > 3 )
                    {
                        // it's a setter method
                        String fieldName = extractFieldNameFromGetterSetterMethodName( methodName );
                        FieldAccessors property = propertiesMap.get( fieldName );
                        if ( null == property )
                        {
                            property = new FieldAccessors( fieldName );
                            propertiesMap.put( fieldName, property );
                        }
                        property.addSetter( method );
                    }
                }
            }
            else
            {
                // might be a getter
                if ( method.getParameters().length == 0 )
                {
                    String methodName = method.getName();
                    if ( (methodName.startsWith( "get" ) && methodName.length() > 3) || (methodName.startsWith( "is" ) && methodName
                        .length() > 2 && null != returnType.isPrimitive() && JPrimitiveType.BOOLEAN.equals( returnType.isPrimitive() )) )
                    {
                        // it's a getter method
                        String fieldName = extractFieldNameFromGetterSetterMethodName( methodName );
                        FieldAccessors property = propertiesMap.get( fieldName );
                        if ( null == property )
                        {
                            property = new FieldAccessors( fieldName );
                            propertiesMap.put( fieldName, property );
                        }
                        property.addGetter( method );
                    }
                }
            }
        }

        for ( JClassType interf : type.getImplementedInterfaces() )
        {
            parseMethods( interf, propertiesMap );
        }

        parseMethods( type.getSuperclass(), propertiesMap );
    }

    private String extractFieldNameFromGetterSetterMethodName( String methodName )
    {
        if ( methodName.startsWith( "is" ) )
        {
            return methodName.substring( 2, 3 ).toLowerCase() + methodName.substring( 3 );
        }
        else
        {
            return methodName.substring( 3, 4 ).toLowerCase() + methodName.substring( 4 );
        }
    }

    private void generateNewInstanceMethod( SourceWriter source, BeanInfo info, List<PropertyInfo> properties )
    {
        JAbstractMethod method;
        if ( null != info.getCreatorConstructor() )
        {
            method = info.getCreatorConstructor();
        }
        else if ( null != info.getCreatorFactory() )
        {
            method = info.getCreatorFactory();
        }
        else if ( null != info.getCreatorDefaultConstructor() )
        {
            method = info.getCreatorDefaultConstructor();
        }
        else
        {
            // should not happen
            return;
        }

        StringBuilder parametersBuilder = new StringBuilder();
        StringBuilder parametersNameBuilder = new StringBuilder();
        for ( int i = 0; i < method.getParameters().length; i++ )
        {
            if ( i > 0 )
            {
                parametersBuilder.append( ", " );
                parametersNameBuilder.append( ", " );
            }
            PropertyInfo property = properties.get( i );

            parametersBuilder.append( property.getType().getParameterizedQualifiedSourceName() ).append( " " ).append( property
                .getPropertyName() );
            parametersNameBuilder.append( property.getPropertyName() );
        }

        if ( method.isPrivate() )
        {
            // private method, we use jsni
            source.println( "private static native %s newInstance(%s) /*-{", info.getType()
                .getParameterizedQualifiedSourceName(), parametersBuilder.toString() );
            source.indent();

            if ( null != method.isConstructor() )
            {
                JConstructor constructor = method.isConstructor();
                source.println( "return %s(%s);", constructor.getJsniSignature(), parametersNameBuilder.toString() );
            }
            else
            {
                JMethod factory = method.isMethod();
                source.println( "return %s(%s);", factory.getJsniSignature(), parametersNameBuilder.toString() );
            }

            source.outdent();
            source.println( "}-*/;" );
        }
        else
        {
            source.println( "private static %s newInstance(%s) {", info.getType().getParameterizedQualifiedSourceName(), parametersBuilder
                .toString() );
            source.indent();

            if ( null != method.isConstructor() )
            {
                source.println( "return new %s(%s);", info.getType().getParameterizedQualifiedSourceName(), parametersNameBuilder
                    .toString() );
            }
            else
            {
                source.println( "return %s.%s(%s);", info.getType().getQualifiedSourceName(), method.getName(), parametersNameBuilder
                    .toString() );
            }

            source.outdent();
            source.println( "}" );
        }
    }
}
