package com.github.nmorel.gwtjackson.rebind;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
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
        source.println();
        source.indent();

        source.println( "@Override" );
        source.println( "protected %s newInstance() {", beanType.getParameterizedQualifiedSourceName() );
        source.indent();
        generateNewInstanceBody( source, beanType );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "protected void initProperties(java.util.Map<java.lang.String, %s<%s>> properties) {", PROPERTY_BEAN_CLASS, beanType
            .getParameterizedQualifiedSourceName() );
        source.indent();
        generateInitProperties( source, beanType );
        source.outdent();
        source.println( "}" );

        source.println();

        source.outdent();
        source.commit( logger );
    }

    private void generateNewInstanceBody( SourceWriter source, JClassType beanType )
    {
        source.println( "return new %s();", beanType.getParameterizedQualifiedSourceName() );
    }

    private void generateInitProperties( SourceWriter source, JClassType beanType ) throws UnableToCompleteException
    {
        BeanInfo info = parseBean( beanType );
        if ( info.isIgnoreAllProperties() )
        {
            logger.log( TreeLogger.Type.DEBUG, "Ignoring all properties of type " + beanType );
            // no properties will be serialized/deserialized
            return;
        }

        Map<String, PropertyInfo> propertiesMap = new LinkedHashMap<String, PropertyInfo>();
        parseFields( beanType, propertiesMap );
        parseMethods( beanType, propertiesMap );

        for ( PropertyInfo property : propertiesMap.values() )
        {
            property.process( info );
            if ( property.isIgnored() )
            {
                logger.log( TreeLogger.Type.DEBUG, "Ignoring field " + property.getFieldName() + " of type " + beanType );
                continue;
            }

            source.println( "properties.put(\"%s\", new " + PROPERTY_BEAN_CLASS + "<%s>() {", property.getPropertyName(), beanType
                .getParameterizedQualifiedSourceName() );
            source.indent();

            writeDecodePropertyMethod( source, beanType, property );

            source.println();

            writeEncodePropertyMethod( source, beanType, property );

            source.outdent();
            source.println( "} );" );
        }
    }

    private BeanInfo parseBean( JClassType beanType )
    {
        BeanInfo info = new BeanInfo();
        if ( beanType.isAnnotationPresent( JsonIgnoreType.class ) )
        {
            info.setIgnoreAllProperties( true );
        }
        if ( beanType.isAnnotationPresent( JsonAutoDetect.class ) )
        {
            JsonAutoDetect autoDetect = beanType.getAnnotation( JsonAutoDetect.class );
            info.setCreatorVisibility( autoDetect.creatorVisibility() );
            info.setFieldVisibility( autoDetect.fieldVisibility() );
            info.setGetterVisibility( autoDetect.getterVisibility() );
            info.setIsGetterVisibility( autoDetect.isGetterVisibility() );
        }
        if ( beanType.isAnnotationPresent( JsonIgnoreProperties.class ) )
        {
            JsonIgnoreProperties ignoreProperties = beanType.getAnnotation( JsonIgnoreProperties.class );
            for ( String ignoreProperty : ignoreProperties.value() )
            {
                info.addIgnoredField( ignoreProperty );
            }
        }
        return info;
    }

    private void parseFields( JClassType beanType, Map<String, PropertyInfo> propertiesMap )
    {
        for ( JField field : beanType.getFields() )
        {
            String fieldName = field.getName();
            PropertyInfo property = propertiesMap.get( fieldName );
            if ( null == property )
            {
                property = new PropertyInfo( fieldName );
                propertiesMap.put( fieldName, property );
            }
            property.setField( field );
        }
    }

    private void parseMethods( JClassType beanType, Map<String, PropertyInfo> propertiesMap )
    {
        for ( JMethod method : beanType.getMethods() )
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
                        PropertyInfo property = propertiesMap.get( fieldName );
                        if ( null == property )
                        {
                            property = new PropertyInfo( fieldName );
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
                        PropertyInfo property = propertiesMap.get( fieldName );
                        if ( null == property )
                        {
                            property = new PropertyInfo( fieldName );
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

    private void writeDecodePropertyMethod( SourceWriter source, JClassType beanType,
                                            PropertyInfo property ) throws UnableToCompleteException
    {
        source.println( "@Override" );
        source.println( "public void decode(%s reader, %s bean, %s ctx) throws java.io.IOException {", JSON_READER_CLASS, beanType
            .getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
        source.indent();

        String setterAccessor = property.getSetterAccessor();
        if ( null == setterAccessor )
        {
            source.println( "throw new %s(\"No setter found for property %s on type %s\")", JSON_DECODING_EXCEPTION_CLASS, property
                .getPropertyName(), beanType.getName() );
        }
        else
        {
            source.println( "bean." + setterAccessor + ";", String.format( "%s.decode(reader, ctx)", createMapperFromType( property
                .getType() ) ) );
        }

        source.outdent();
        source.println( "}" );
    }

    private void writeEncodePropertyMethod( SourceWriter source, JClassType beanType,
                                            PropertyInfo property ) throws UnableToCompleteException
    {
        source.println( "@Override" );
        source.println( "public void encode(%s writer, %s bean, %s ctx) throws java.io.IOException {", JSON_WRITER_CLASS, beanType
            .getParameterizedQualifiedSourceName(), JSON_ENCODING_CONTEXT_CLASS );
        source.indent();

        String getterAccessor = property.getGetterAccessor();
        if ( null == getterAccessor )
        {
            source.println( "throw new %s(\"No getter found for property %s on type %s\")", JSON_ENCODING_EXCEPTION_CLASS, property
                .getPropertyName(), beanType.getName() );
        }
        else
        {
            source.println( "%s.encode(writer, bean.%s, ctx);", createMapperFromType( property.getType() ), getterAccessor );
        }

        source.outdent();
        source.println( "}" );
    }
}
