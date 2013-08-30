package com.github.nmorel.gwtjackson.rebind;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
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

        SourceWriter source = getSourceWriter( packageName, mapperClassSimpleName, ABSTRACT_BEAN_JSON_MAPPER_CLASS + "<" +
            beanType.getParameterizedQualifiedSourceName() + ">" );

        // the class already exists, no need to continue
        if ( source == null )
        {
            return qualifiedMapperClassName;
        }

        writeClassBody( source, beanType );

        return qualifiedMapperClassName;
    }

    private void writeClassBody( SourceWriter source, JClassType beanType ) throws UnableToCompleteException
    {
        BeanInfo info = BeanInfo.process( beanType );

        source.println();
        source.indent();

        source.println( "@Override" );
        source.println( "protected %s newInstance() {", beanType.getParameterizedQualifiedSourceName() );
        source.indent();
        generateNewInstanceBody( source, info );
        source.outdent();
        source.println( "}" );

        source.println();

        List<PropertyInfo> properties;
        if ( info.isIgnoreAllProperties() )
        {
            logger.log( TreeLogger.Type.DEBUG, "Ignoring all properties of type " + beanType );
            properties = null;
        }
        else
        {
            properties = findAllProperties( info );
        }

        source.println( "@Override" );
        source
            .println( "protected void initDecoders(java.util.Map<java.lang.String, %s<%s>> decoders) {", DECODER_PROPERTY_BEAN_CLASS,
                beanType
                .getParameterizedQualifiedSourceName() );
        if ( !info.isIgnoreAllProperties() )
        {
            source.indent();
            generateInitDecoders( source, info, properties );
            source.outdent();
        }
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source
            .println( "protected void initEncoders(java.util.Map<java.lang.String, %s<%s>> encoders) {", ENCODER_PROPERTY_BEAN_CLASS,
                beanType
                .getParameterizedQualifiedSourceName() );
        if ( !info.isIgnoreAllProperties() )
        {
            source.indent();
            generateInitEncoders( source, info, properties );
            source.outdent();
        }
        source.println( "}" );

        source.println();

        if ( !info.isIgnoreAllProperties() )
        {
            generateAdditionalMethods( source, properties );
        }

        source.outdent();
        source.commit( logger );
    }

    private void generateNewInstanceBody( SourceWriter source, BeanInfo info )
    {
        source.println( "return new %s();", info.getType().getParameterizedQualifiedSourceName() );
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

            source.println( "decoders.put(\"%s\", new " + DECODER_PROPERTY_BEAN_CLASS + "<%s>() {", property.getPropertyName(), info
                .getType().getParameterizedQualifiedSourceName() );

            source.indent();
            source.println( "@Override" );
            source.println( "public void decode(%s reader, %s bean, %s ctx) throws java.io.IOException {", JSON_READER_CLASS, info.getType()
                .getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
            source.indent();

            source.println( setterAccessor + ";", String.format( "%s.decode(reader, ctx)", createMapperFromType( property.getType() ) ) );

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
        parseFields( info, fieldsMap );
        parseMethods( info, fieldsMap );

        List<PropertyInfo> properties = new ArrayList<PropertyInfo>();
        for ( FieldAccessors field : fieldsMap.values() )
        {
            PropertyInfo property = PropertyInfo.process( field, info );
            if ( property.isIgnored() )
            {
                logger.log( TreeLogger.Type.DEBUG, "Ignoring field " + field.getFieldName() + " of type " + info.getType() );
            }
            else
            {
                properties.add( property );
            }
        }

        return properties;
    }

    private void parseFields( BeanInfo info, Map<String, FieldAccessors> propertiesMap )
    {
        for ( JField field : info.getType().getFields() )
        {
            String fieldName = field.getName();
            FieldAccessors property = propertiesMap.get( fieldName );
            if ( null == property )
            {
                property = new FieldAccessors( fieldName );
                propertiesMap.put( fieldName, property );
            }
            property.setField( field );
        }
    }

    private void parseMethods( BeanInfo info, Map<String, FieldAccessors> propertiesMap )
    {
        for ( JMethod method : info.getType().getMethods() )
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
                        property.setSetter( method );
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
                        property.setGetter( method );
                    }
                }
            }
        }
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
}
