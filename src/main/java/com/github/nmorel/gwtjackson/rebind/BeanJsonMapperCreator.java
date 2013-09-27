package com.github.nmorel.gwtjackson.rebind;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.SourceWriter;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstEncounteredAnnotationsOnAllHierarchy;

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
     * @param beanType type of the bean
     * @return the fully qualified name of the created class
     * @throws com.google.gwt.core.ext.UnableToCompleteException
     */
    public BeanJsonMapperInfo create( JClassType beanType ) throws UnableToCompleteException
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

        // retrieve the informations on the beans and its properties
        BeanInfo info = BeanInfo.process( logger, typeOracle, beanType, qualifiedMapperClassName, mapperClassSimpleName );
        Map<String, PropertyInfo> properties = findAllProperties( info );

        BeanJsonMapperInfo result = new BeanJsonMapperInfo( qualifiedMapperClassName, info, properties );

        PrintWriter printWriter = getPrintWriter( packageName, mapperClassSimpleName );
        // the class already exists, no need to continue
        if ( printWriter == null )
        {
            return result;
        }

        SourceWriter source = getSourceWriter( printWriter, packageName, mapperClassSimpleName, info.getSuperclass() + "<" +
            beanType.getParameterizedQualifiedSourceName() + ", " + info.getInstanceBuilderQualifiedName() + ">" );

        writeClassBody( source, info, properties );

        return result;
    }

    private void writeClassBody( SourceWriter source, BeanInfo info, Map<String, PropertyInfo> properties ) throws UnableToCompleteException
    {
        source.println();

        if ( info.isInstantiable() )
        {
            generateInstanceBuilderClass( source, info, properties );
        }

        source.println();

        generateConstructors( source, info );

        source.println();

        // tell if the class is instantiable
        source.println( "@Override" );
        source.println( "protected boolean isInstantiable() {" );
        source.indent();
        source.println( "return %s;", info.isInstantiable() );
        source.outdent();
        source.println( "}" );
        source.println();

        source.println( "@Override" );
        source.println( "protected %s newInstanceBuilder(%s ctx) {", info.getInstanceBuilderQualifiedName(), JSON_DECODING_CONTEXT_CLASS );
        source.indent();
        generateNewInstanceBuilderBody( source, info );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "protected void initDecoders() {", DECODER_PROPERTY_BEAN_CLASS, info.getType()
            .getParameterizedQualifiedSourceName(), info.getInstanceBuilderQualifiedName() );
        if ( info.isInstantiable() )
        {
            source.indent();
            generateInitDecoders( source, info, properties );
            source.outdent();
        }
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "protected void initEncoders() {", ENCODER_PROPERTY_BEAN_CLASS, info.getType()
            .getParameterizedQualifiedSourceName() );
        source.indent();
        generateInitEncoders( source, info, properties );
        source.outdent();
        source.println( "}" );

        if ( info.isInstantiable() )
        {
            source.println();
            generateNewInstanceMethod( source, info, properties );
        }

        source.println();
        generateAdditionalMethods( source, properties );

        source.commit( logger );
    }

    private void generateConstructors( SourceWriter source, BeanInfo info ) throws UnableToCompleteException
    {
        source.println( "public %s() {", info.getMapperClassSimpleName() );
        source.indent();
        source.println( "this(null, null);" );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "public %s(%s<%s> idProperty, %s<%s> superclassInfo) {", info
            .getMapperClassSimpleName(), IDENTITY_PROPERTY_BEAN_CLASS, info.getType()
            .getParameterizedQualifiedSourceName(), SUPERCLASS_INFO_CLASS, info.getType().getParameterizedQualifiedSourceName() );
        source.indent();
        source.println( "super();" );
        if ( null != info.getIdentityInfo() )
        {
            source.println( "if(null == idProperty) {" );
            source.indent();
            source.print( "setIdProperty(" );
            generateIdProperty( source, info.getType(), info.getIdentityInfo() );
            source.println( ");" );
            source.outdent();
            source.println( "} else {" );
        }
        else
        {
            source.println( "if(null != idProperty) {" );
        }
        source.indent();
        source.println( "setIdProperty(idProperty);" );
        source.outdent();
        source.println( "}" );

        if ( info.isHasSubtypes() )
        {
            source.println( "if(null == superclassInfo) {" );
            source.indent();
            source.print( "setSuperclassInfo(" );
            generateDefaultSuperclassInfo( source, info );
            source.println( ");" );
            source.outdent();
            source.println( "} else {" );
        }
        else
        {
            source.println( "if(null != superclassInfo) {" );
        }
        source.indent();
        source.println( "setSuperclassInfo(superclassInfo);" );
        source.outdent();
        source.println( "}" );

        source.outdent();
        source.println( "}" );
    }

    private void generateInstanceBuilderClass( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException
    {
        source.println( "private static class %s implements %s<%s> {", info.getInstanceBuilderSimpleName(), INSTANCE_BUILDER_CLASS, info
            .getType().getParameterizedQualifiedSourceName() );
        source.indent();

        if ( info.isCreatorDefaultConstructor() )
        {
            generateInstanceBuilderClassBodyForDefaultConstructor( source, info, properties );
        }
        else if ( info.isCreatorDelegation() )
        {
            generateInstanceBuilderClassBodyForConstructorOrFactoryMethodDelegation( source, info, properties );
        }
        else
        {
            generateInstanceBuilderClassBodyForConstructorOrFactoryMethod( source, info, properties );
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
    private void generateInstanceBuilderClassBodyForDefaultConstructor( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties )
    {
        // when using default constructor, we can create the instance at the builder instantiation.
        source.println();
        source.println( "private %s %s = %s.newInstance();", info.getType().getParameterizedQualifiedSourceName(), BEAN_INSTANCE_NAME, info
            .getQualifiedMapperClassName() );
        source.println();

        // we initialize a map containing the required properties
        Map<String, PropertyInfo> requiredProperties = new LinkedHashMap<String, PropertyInfo>();
        for ( PropertyInfo property : properties.values() )
        {
            if ( null == property.getBackReference() && property.isRequired() )
            {
                String isSetName = String.format( IS_SET_FORMAT, property.getPropertyName() );
                source.println( "private boolean %s;", isSetName );
                requiredProperties.put( isSetName, property );
            }
        }

        // generate the setter for each property
        for ( PropertyInfo property : properties.values() )
        {
            // backReference don't need setter and identityProperty are handled differently
            if ( null == property.getBackReference() )
            {
                if ( null == property.getManagedReference() )
                {
                    source.println( "private void _%s(%s value, %s ctx) {", property.getPropertyName(), property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
                }
                else
                {
                    source.println( "private void _%s(%s value, %s<%s> mapper, %s ctx) {", property.getPropertyName(), property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_MAPPER_CLASS, property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
                }

                source.indent();
                generateInstanceBuilderSetterBodyForDefaultConstructor( source, property );
                source.outdent();
                source.println( "}" );
                source.println();
            }
        }

        source.println( "@Override" );
        source.println( "public void addCallback(%s callback) {", INSTANCE_BUILDER_CALLBACK_CLASS );
        source.indent();
        source.println( "callback.onInstanceCreated(%s);", BEAN_INSTANCE_NAME );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "public %s build(%s ctx) {", info.getType().getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
        source.indent();
        generateRequiredPropertiesCheck( source, requiredProperties );
        source.println( "return %s;", BEAN_INSTANCE_NAME );
        source.outdent();
        source.println( "}" );
    }

    private void generateInstanceBuilderSetterBodyForDefaultConstructor( SourceWriter source, PropertyInfo property )
    {
        source.println( property.getSetterAccessor() + ";", "value" );
        if ( property.isRequired() )
        {
            source.println( "this.%s = true;", String.format( IS_SET_FORMAT, property.getPropertyName() ) );
        }
        if ( null != property.getManagedReference() )
        {
            source.println( "mapper.setBackReference(\"%s\", %s, value, ctx);", property.getManagedReference(), BEAN_INSTANCE_NAME );
        }
    }

    /**
     * Generate the instance builder class body for a constructor with parameters or factory method. We will declare all the fields and
     * instanciate the bean only on build() method when all properties have been decoded
     *
     * @param source writer
     * @param info info on bean
     * @param properties list of properties
     */
    private void generateInstanceBuilderClassBodyForConstructorOrFactoryMethod( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties )
    {
        // will contain the instance once created
        source.println();
        source.println( "private %s %s;", info.getType().getParameterizedQualifiedSourceName(), BEAN_INSTANCE_NAME, info
            .getQualifiedMapperClassName() );

        // contains the callback to called when the instance is created
        source.println();
        source
            .println( "private final java.util.List<%s<%s>> callbacks = new java.util.ArrayList<%s<%s>>();",
                INSTANCE_BUILDER_CALLBACK_CLASS, info
                .getType().getParameterizedQualifiedSourceName(), INSTANCE_BUILDER_CALLBACK_CLASS, info.getType()
                .getParameterizedQualifiedSourceName() );

        // we initialize a map containing the required properties
        Map<String, PropertyInfo> requiredProperties = new LinkedHashMap<String, PropertyInfo>();
        // generating the builder's fields used to hold the values before we instantiate the result
        for ( PropertyInfo property : properties.values() )
        {
            if ( null == property.getBackReference() )
            {
                source.println();
                source.println( "private %s _%s;", property.getType().getParameterizedQualifiedSourceName(), property.getPropertyName() );
                String isSetName = String.format( IS_SET_FORMAT, property.getPropertyName() );
                source.println( "private boolean %s;", isSetName );
                if ( property.isRequired() )
                {
                    requiredProperties.put( isSetName, property );
                }
                if ( null != property.getManagedReference() )
                {
                    source.println( "private %s<%s> %s;", JSON_MAPPER_CLASS, property.getType()
                        .getParameterizedQualifiedSourceName(), String.format( BUILDER_MAPPER_FORMAT, property.getPropertyName() ) );
                }
            }
        }

        source.println();

        for ( PropertyInfo property : properties.values() )
        {
            if ( null == property.getBackReference() )
            {
                if ( null == property.getManagedReference() )
                {
                    source.println( "private void _%s(%s value, %s ctx) {", property.getPropertyName(), property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
                }
                else
                {
                    source.println( "private void _%s(%s value, %s<%s> mapper, %s ctx) {", property.getPropertyName(), property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_MAPPER_CLASS, property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
                }

                source.indent();
                generateInstanceBuilderSetterBodyForConstructorOrFactoryMethod( source, info, property.getPropertyName(), info
                    .getCreatorParameters().containsKey( property.getPropertyName() ) );
                if ( null != property.getManagedReference() )
                {
                    source.println( "this.%s = mapper;", String.format( BUILDER_MAPPER_FORMAT, property.getPropertyName() ) );
                }
                source.outdent();
                source.println( "}" );
                source.println();
            }
        }

        source.println( "@Override" );
        source.println( "public void addCallback(%s callback) {", INSTANCE_BUILDER_CALLBACK_CLASS );
        source.indent();
        source.println( "if(null == this.%s) {", BEAN_INSTANCE_NAME );
        source.indent();
        source.println( "callbacks.add(callback);" );
        source.outdent();
        source.println( "} else {" );
        source.indent();
        source.println( "callback.onInstanceCreated(%s);", BEAN_INSTANCE_NAME );
        source.outdent();
        source.println( "}" );
        source.outdent();
        source.println( "}" );

        source.println();

        generateInstanceBuilderCreateInstanceForConstructorOrFactoryMethod( source, info );

        source.println( "@Override" );
        source.println( "public %s build(%s ctx) {", info.getType().getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
        source.indent();

        generateRequiredPropertiesCheck( source, requiredProperties );

        source.println( "if(null == this.%s) {", BEAN_INSTANCE_NAME );
        source.indent();
        source.println( "createInstance(ctx);" );
        source.outdent();
        source.println( "}" );

        // Writing the rest of the properties
        for ( Map.Entry<String, PropertyInfo> propertyEntry : properties.entrySet() )
        {
            if ( !info.getCreatorParameters().containsKey( propertyEntry.getKey() ) && null == propertyEntry.getValue().getBackReference() )
            {
                source.println( "if (this.%s) {", String.format( IS_SET_FORMAT, propertyEntry.getKey() ) );
                source.indent();
                source.println( propertyEntry.getValue().getSetterAccessor() + ";", "this._" + propertyEntry.getKey() );
                if ( null != propertyEntry.getValue().getManagedReference() )
                {
                    source.println( "%s.setBackReference(%s, %s, this._%s, ctx);", String.format( BUILDER_MAPPER_FORMAT, propertyEntry
                        .getKey() ), propertyEntry.getKey(), propertyEntry.getValue()
                        .getManagedReference(), BEAN_INSTANCE_NAME, propertyEntry.getKey() );
                }
                source.outdent();
                source.println( "}" );
            }
        }
        source.println( "return %s;", BEAN_INSTANCE_NAME );

        source.outdent();
        source.println( "}" );
    }

    private void generateInstanceBuilderSetterBodyForConstructorOrFactoryMethod( SourceWriter source, BeanInfo info, String fieldName,
                                                                                 boolean creatorOrIdentityProperty )
    {
        source.println( "this._%s = value;", fieldName );
        source.println( "this.%s = true;", String.format( IS_SET_FORMAT, fieldName ) );
        if ( creatorOrIdentityProperty )
        {
            StringBuilder ifBuilder = new StringBuilder();
            for ( Map.Entry<String, JParameter> parameterEntry : info.getCreatorParameters().entrySet() )
            {
                if ( ifBuilder.length() > 0 )
                {
                    ifBuilder.append( " && " );
                }
                ifBuilder.append( String.format( IS_SET_FORMAT, parameterEntry.getKey() ) );
            }
            source.println( "if(null == this.%s && %s) {", BEAN_INSTANCE_NAME, ifBuilder.toString() );
            source.indent();
            source.println( "createInstance(ctx);" );
            source.outdent();
            source.println( "}" );
        }
    }

    private void generateInstanceBuilderCreateInstanceForConstructorOrFactoryMethod( SourceWriter source, BeanInfo info )
    {
        // we compute the creator args
        StringBuilder parametersBuilder = new StringBuilder();
        for ( Map.Entry<String, JParameter> parameterEntry : info.getCreatorParameters().entrySet() )
        {
            if ( parametersBuilder.length() > 0 )
            {
                parametersBuilder.append( ", " );
            }
            parametersBuilder.append( "_" ).append( parameterEntry.getKey() );
        }

        source.println( "private void createInstance(%s ctx) {", JSON_DECODING_CONTEXT_CLASS );
        source.indent();
        source.println( "this.%s = %s.newInstance(%s);", BEAN_INSTANCE_NAME, info.getQualifiedMapperClassName(), parametersBuilder
            .toString() );
        source.println( "if(!callbacks.isEmpty()) {" );
        source.indent();
        source.println( "for(%s<%s> callback : callbacks) {", INSTANCE_BUILDER_CALLBACK_CLASS, info.getType()
            .getParameterizedQualifiedSourceName() );
        source.indent();
        source.println( "callback.onInstanceCreated(this.%s);", BEAN_INSTANCE_NAME );
        source.outdent();
        source.println( "}" );
        source.println( "callbacks.clear();" );
        source.outdent();
        source.println( "}" );
        source.outdent();
        source.println( "}" );
        source.println();
    }

    /**
     * Generate the instance builder class body for a constructor or factory method with delegation.
     *
     * @param source writer
     * @param info info on bean
     * @param properties list of properties
     */
    private void generateInstanceBuilderClassBodyForConstructorOrFactoryMethodDelegation( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException
    {
        // FIXME @JsonCreator with delegation
        logger.log( TreeLogger.Type.ERROR, "The delegation is not supported yet" );
        throw new UnableToCompleteException();
    }

    /** Generates the if statement for required properties. */
    private void generateRequiredPropertiesCheck( SourceWriter source, Map<String, PropertyInfo> requiredProperties )
    {
        if ( requiredProperties.isEmpty() )
        {
            return;
        }

        source.print( "if(" );
        boolean first = true;
        for ( String isSetName : requiredProperties.keySet() )
        {
            if ( !first )
            {
                source.print( " || " );
            }
            source.print( "!" );
            source.print( isSetName );
            first = false;
        }
        source.println( ") {" );
        source.indent();
        // TODO we could specify the name of the missing properties
        source.println( "throw ctx.traceError(\"A required property is missing\");" );
        source.outdent();
        source.println( "}" );
    }

    private void generateDefaultSuperclassInfo( SourceWriter source, BeanInfo info ) throws UnableToCompleteException
    {
        source.print( "new %s(", SUPERCLASS_INFO_CLASS );
        // gives the information about how to read and write the type info
        if ( null != info.getTypeInfo() )
        {
            String typeInfoProperty = null;
            if ( JsonTypeInfo.As.PROPERTY.equals( info.getTypeInfo().include() ) )
            {
                typeInfoProperty = "\"" + (info.getTypeInfo().property().isEmpty() ? info.getTypeInfo().use()
                    .getDefaultPropertyName() : info.getTypeInfo().property()) + "\"";
            }
            source.print( "com.fasterxml.jackson.annotation.JsonTypeInfo.As.%s, %s", info.getTypeInfo().include(), typeInfoProperty );
        }
        source.println( ")" );
        source.indent();

        generateSubtypeMappers( source, info );

        source.outdent();
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
        String typeMetadata;
        if ( null == info.getTypeInfo() )
        {
            typeMetadata = null;
        }
        else
        {
            typeMetadata = "\"" + extractTypeMetadata( info, subtype ) + "\"";
        }

        source.println( ".addSubtypeMapper( new %s<%s>() {", SUBTYPE_MAPPER_CLASS, subtype.getQualifiedSourceName() );
        source.indent();
        source.indent();

        source.println();
        source.println( "private %s<%s, ?> mapper;", ABSTRACT_BEAN_JSON_MAPPER_CLASS, subtype.getQualifiedSourceName() );
        source.println();

        source.println( "@Override" );
        source.println( "public %s<%s, ?> getMapper( %s ctx ) {", ABSTRACT_BEAN_JSON_MAPPER_CLASS, subtype
            .getQualifiedSourceName(), JSON_MAPPING_CONTEXT_CLASS );
        source.indent();
        String mapper = info.getType() == subtype ? info.getQualifiedMapperClassName() + ".this" : getMapperFromType( subtype );
        source.println( "if(null == this.mapper) {", mapper );
        source.indent();
        source.println( "this.mapper = %s;", mapper );
        source.outdent();
        source.println( "}" );
        source.println( "return this.mapper;" );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "public %s decodeObject( %s reader, %s ctx ) throws java.io.IOException {", subtype
            .getQualifiedSourceName(), JSON_READER_CLASS, JSON_DECODING_CONTEXT_CLASS );
        source.indent();
        source.println( "return getMapper(ctx).decodeObject( reader, ctx );" );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "public void encodeObject( %s writer, %s bean, %s ctx ) throws java.io.IOException {", JSON_WRITER_CLASS, subtype
            .getParameterizedQualifiedSourceName(), JSON_ENCODING_CONTEXT_CLASS );
        source.indent();
        source.println( "getMapper(ctx).encodeObject( writer, bean, ctx );" );
        source.outdent();
        source.println( "}" );

        source.outdent();
        source.println( "}, %s.class, %s )", subtype.getQualifiedSourceName(), typeMetadata );
        source.println();

        source.outdent();
    }

    private String extractTypeMetadata( BeanInfo info, JClassType subtype ) throws UnableToCompleteException
    {
        switch ( info.getTypeInfo().use() )
        {
            case NAME:
                JsonSubTypes jsonSubTypes = findFirstEncounteredAnnotationsOnAllHierarchy( info.getType(), JsonSubTypes.class );
                if ( null != jsonSubTypes && jsonSubTypes.value().length > 0 )
                {
                    for ( JsonSubTypes.Type type : jsonSubTypes.value() )
                    {
                        if ( !type.name().isEmpty() && type.value().getName().equals( subtype.getQualifiedBinaryName() ) )
                        {
                            return type.name();
                        }
                    }
                }
                JsonTypeName typeName = findFirstEncounteredAnnotationsOnAllHierarchy( subtype, JsonTypeName.class );
                if ( null != typeName && null != typeName.value() && !typeName.value().isEmpty() )
                {
                    return typeName.value();
                }
                else
                {
                    String simpleBinaryName = subtype.getQualifiedBinaryName();
                    int indexLastDot = simpleBinaryName.lastIndexOf( '.' );
                    if ( indexLastDot != -1 )
                    {
                        simpleBinaryName = simpleBinaryName.substring( indexLastDot + 1 );
                    }
                    return simpleBinaryName;
                }
            case MINIMAL_CLASS:
                if ( !info.getType().getPackage().isDefault() )
                {
                    String basePackage = info.getType().getPackage().getName();
                    if ( subtype.getQualifiedBinaryName().startsWith( basePackage + "." ) )
                    {
                        return subtype.getQualifiedBinaryName().substring( basePackage.length() );
                    }
                }
            case CLASS:
                return subtype.getQualifiedBinaryName();
            default:
                logger.log( TreeLogger.Type.ERROR, "JsonTypeInfo.Id." + info.getTypeInfo().use() + " is not supported" );
                throw new UnableToCompleteException();
        }
    }

    private void generateNewInstanceBuilderBody( SourceWriter source, BeanInfo info )
    {
        if ( info.isInstantiable() )
        {
            source.println( "return new %s();", info.getInstanceBuilderQualifiedName() );
        }
        else
        {
            source.println( "throw ctx.traceError(\"Cannot instantiate the type \" + %s.class.getName());", info.getType()
                .getQualifiedSourceName() );
        }
    }

    private void generateInitDecoders( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException
    {
        for ( PropertyInfo property : properties.values() )
        {
            String setterAccessor = property.getSetterAccessor();
            if ( null == setterAccessor )
            {
                // there is no setter visible
                continue;
            }

            if ( null == property.getBackReference() )
            {
                // this is not a back reference, we add the default decoder
                source.println( "addProperty(\"%s\", new " + DECODER_PROPERTY_BEAN_CLASS + "<%s, %s>() {", property.getPropertyName(), info
                    .getType().getParameterizedQualifiedSourceName(), info.getInstanceBuilderQualifiedName() );

                source.indent();
                source.println( "@Override" );
                source.println( "public java.lang.Object decode(%s reader, %s builder, %s ctx) {", JSON_READER_CLASS, info
                    .getInstanceBuilderQualifiedName(), JSON_DECODING_CONTEXT_CLASS );
                source.indent();

                source.println( "%s<%s> mapper = %s;", JSON_MAPPER_CLASS, getQualifiedBoxedName( property
                    .getType() ), getMapperFromType( property.getType(), property ) );
                source.println( "%s value = mapper.decode(reader, ctx);", property.getType().getParameterizedQualifiedSourceName() );
                if ( null == property.getManagedReference() )
                {
                    source.println( "builder._%s(value, ctx);", property.getPropertyName() );
                }
                else
                {
                    // it's a managed reference, we have to give the mapper to the builder. It needs it to call the setBackReference
                    // method once the bean is instantiated.
                    source.println( "builder._%s(value, mapper, ctx);", property.getPropertyName() );
                }
                source.println( "return value;" );
            }
            else
            {
                // this is a back reference, we add the special back reference property that will be called by the parent
                source.println( "addProperty(\"%s\", new " + BACK_REFERENCE_PROPERTY_BEAN_CLASS + "<%s, %s>() {", property
                    .getBackReference(), info.getType().getParameterizedQualifiedSourceName(), property.getType()
                    .getParameterizedQualifiedSourceName() );

                source.indent();
                source.println( "@Override" );
                source.println( "public void setBackReference(%s %s, %s reference, %s ctx) {", info.getType()
                    .getParameterizedQualifiedSourceName(), BEAN_INSTANCE_NAME, property.getType()
                    .getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
                source.indent();

                source.println( property.getSetterAccessor() + ";", "reference" );
            }

            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "} );" );
        }
    }

    private void generateInitEncoders( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException
    {
        for ( PropertyInfo property : properties.values() )
        {
            String getterAccessor = property.getGetterAccessor();
            if ( null == getterAccessor )
            {
                // there is no getter visible or the property is used as identity and is handled separately
                continue;
            }

            source.println( "if(null == getIdProperty() || !getIdProperty().getPropertyName().equals(\"%s\")) {",
                property.getPropertyName() );
            source.indent();

            source.println( "addProperty(\"%s\", new " + ENCODER_PROPERTY_BEAN_CLASS + "<%s>() {", property.getPropertyName(), info
                .getType().getParameterizedQualifiedSourceName() );

            source.indent();
            source.println( "@Override" );
            source.println( "public void encode(%s writer, %s bean, %s ctx) {", JSON_WRITER_CLASS, info.getType()
                .getParameterizedQualifiedSourceName(), JSON_ENCODING_CONTEXT_CLASS );
            source.indent();

            source.println( "%s.encode(writer, %s, ctx);", getMapperFromType( property.getType(), property ), getterAccessor );

            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "} );" );

            source.outdent();
            source.println( "}" );
        }
    }

    private void generateAdditionalMethods( SourceWriter source, Map<String, PropertyInfo> properties )
    {
        for ( PropertyInfo property : properties.values() )
        {
            for ( PropertyInfo.AdditionalMethod method : property.getAdditionalMethods() )
            {
                method.write( source );
                source.println();
            }
        }
    }

    private Map<String, PropertyInfo> findAllProperties( BeanInfo info ) throws UnableToCompleteException
    {
        Map<String, FieldAccessors> fieldsMap = new LinkedHashMap<String, FieldAccessors>();
        parseFields( info.getType(), fieldsMap );
        parseMethods( info.getType(), fieldsMap );

        // Processing all the properties accessible via field, getter or setter
        Map<String, PropertyInfo> propertiesMap = new LinkedHashMap<String, PropertyInfo>();
        for ( FieldAccessors field : fieldsMap.values() )
        {
            PropertyInfo property = PropertyInfo.process( logger, typeOracle, field, info );
            if ( property.isIgnored() )
            {
                logger.log( TreeLogger.Type.DEBUG, "Ignoring field " + field.getFieldName() + " of type " + info.getType() );
            }
            else
            {
                propertiesMap.put( property.getPropertyName(), property );
            }
        }

        // We look if there is any constructor parameters not found yet
        if ( !info.getCreatorParameters().isEmpty() )
        {
            for ( Map.Entry<String, JParameter> entry : info.getCreatorParameters().entrySet() )
            {
                PropertyInfo property = propertiesMap.get( entry.getKey() );
                if ( null == property )
                {
                    propertiesMap.put( entry.getKey(), PropertyInfo.process( logger, typeOracle, entry.getKey(), entry.getValue(), info ) );
                }
                else if ( entry.getValue().getAnnotation( JsonProperty.class ).required() )
                {
                    property.setRequired( true );
                }
            }
        }

        Map<String, PropertyInfo> result = new LinkedHashMap<String, PropertyInfo>();

        // we first add the properties defined in order
        for ( String orderedProperty : info.getPropertyOrderList() )
        {
            // we remove the entry to have the map with only properties with natural or alphabetic order
            PropertyInfo property = propertiesMap.remove( orderedProperty );
            if ( null != property )
            {
                result.put( property.getPropertyName(), property );
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
                result.put( entry.getKey(), entry.getValue() );
            }
        }
        else
        {
            for ( Map.Entry<String, PropertyInfo> entry : propertiesMap.entrySet() )
            {
                result.put( entry.getKey(), entry.getValue() );
            }
        }

        findIdPropertyInfo( result, info.getIdentityInfo() );

        return result;
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

    private void generateNewInstanceMethod( SourceWriter source, BeanInfo info, Map<String, PropertyInfo> properties )
    {
        JAbstractMethod method = info.getCreatorMethod();

        StringBuilder parametersBuilder = new StringBuilder();
        StringBuilder parametersNameBuilder = new StringBuilder();
        for ( Map.Entry<String, JParameter> parameterEntry : info.getCreatorParameters().entrySet() )
        {
            if ( parametersBuilder.length() > 0 )
            {
                parametersBuilder.append( ", " );
                parametersNameBuilder.append( ", " );
            }
            PropertyInfo property = properties.get( parameterEntry.getKey() );

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
