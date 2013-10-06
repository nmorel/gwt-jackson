package com.github.nmorel.gwtjackson.rebind;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Nicolas Morel
 */
public class BeanJsonDeserializerCreator extends AbstractBeanJsonCreator {

    public static final String INSTANCE_BUILDER_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean.InstanceBuilder";

    private static final String INSTANCE_BUILDER_CALLBACK_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean.InstanceBuilderCallback";

    private static final String BEAN_PROPERTY_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean" + "" +
        ".BeanPropertyDeserializer";

    private static final String BACK_REFERENCE_PROPERTY_BEAN_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean.BackReferenceProperty";

    private static final String SUPERCLASS_DESERIALIZATION_INFO_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean" + "" +
        ".SuperclassDeserializationInfo";

    private static final String SUBTYPE_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean.SubtypeDeserializer";

    public BeanJsonDeserializerCreator( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle ) {
        super( logger, context, typeOracle );
    }

    @Override
    protected boolean isSerializer() {
        return false;
    }

    @Override
    protected void writeClassBody( SourceWriter source, BeanInfo beanInfo, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException {
        source.println();

        if ( beanInfo.isInstantiable() ) {
            generateInstanceBuilderClass( source, beanInfo, properties );
        }

        source.println();

        generateConstructors( source, beanInfo, properties );

        source.println();

        // tell if the class is instantiable
        source.println( "@Override" );
        source.println( "protected boolean isInstantiable() {" );
        source.indent();
        source.println( "return %s;", beanInfo.isInstantiable() );
        source.outdent();
        source.println( "}" );
        source.println();

        source.println( "@Override" );
        source.println( "protected %s newInstanceBuilder(%s ctx) {", beanInfo
            .getInstanceBuilderQualifiedName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
        source.indent();
        generateNewInstanceBuilderBody( source, beanInfo );
        source.outdent();
        source.println( "}" );

        if ( beanInfo.isInstantiable() ) {
            source.println();
            generateNewInstanceMethod( source, beanInfo, properties );
        }

        source.println();
        generateAdditionalMethods( source, properties );

        source.commit( logger );
    }

    private void generateConstructors( SourceWriter source, BeanInfo beanInfo, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException {
        source.println( "public %s() {", getSimpleClassName() );
        source.indent();
        source.println( "this(null, null);" );
        source.outdent();
        source.println( "}" );

        source.println();

        source
            .println( "public %s(%s idProperty, %s<%s> superclassInfo) {", getSimpleClassName(), IDENTITY_DESERIALIZATION_INFO_CLASS,
                SUPERCLASS_DESERIALIZATION_INFO_CLASS, beanInfo
                .getType().getParameterizedQualifiedSourceName() );
        source.indent();
        source.println( "super();" );
        if ( null != beanInfo.getIdentityInfo() ) {
            source.println( "if(null == idProperty) {" );
            source.indent();
            source.print( "setIdentityInfo(" );
            generateIdentifierDeserializationInfo( source, beanInfo.getIdentityInfo() );
            source.println( ");" );
            source.outdent();
            source.println( "} else {" );
        } else {
            source.println( "if(null != idProperty) {" );
        }
        source.indent();
        source.println( "setIdentityInfo(idProperty);" );
        source.outdent();
        source.println( "}" );

        if ( beanInfo.isHasSubtypes() ) {
            source.println( "if(null == superclassInfo) {" );
            source.indent();
            source.print( "setSuperclassInfo(" );
            generateDefaultSuperclassInfo( source, beanInfo );
            source.println( ");" );
            source.outdent();
            source.println( "} else {" );
        } else {
            source.println( "if(null != superclassInfo) {" );
        }
        source.indent();
        source.println( "setSuperclassInfo(superclassInfo);" );
        source.outdent();
        source.println( "}" );

        source.println();

        if ( beanInfo.isInstantiable() ) {
            generatePropertyDeserializers( source, beanInfo, properties );
        }

        source.outdent();
        source.println( "}" );
    }

    private void generateInstanceBuilderClass( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException {
        source.println( "private static class %s implements %s<%s> {", info.getInstanceBuilderSimpleName(), INSTANCE_BUILDER_CLASS, info
            .getType().getParameterizedQualifiedSourceName() );
        source.indent();

        if ( info.isCreatorDefaultConstructor() ) {
            generateInstanceBuilderClassBodyForDefaultConstructor( source, info, properties );
        } else if ( info.isCreatorDelegation() ) {
            generateInstanceBuilderClassBodyForConstructorOrFactoryMethodDelegation( source, info, properties );
        } else {
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
        PropertyInfo> properties ) {
        // when using default constructor, we can create the instance at the builder instantiation.
        source.println();
        source.println( "private %s %s = %s.newInstance();", info.getType()
            .getParameterizedQualifiedSourceName(), BEAN_INSTANCE_NAME, getQualifiedClassName() );
        source.println();

        // we initialize a map containing the required properties
        Map<String, PropertyInfo> requiredProperties = new LinkedHashMap<String, PropertyInfo>();
        for ( PropertyInfo property : properties.values() ) {
            if ( !property.isIgnored() && null == property.getBackReference() && property.isRequired() ) {
                String isSetName = String.format( IS_SET_FORMAT, property.getPropertyName() );
                source.println( "private boolean %s;", isSetName );
                requiredProperties.put( isSetName, property );
            }
        }

        // generate the setter for each property
        for ( PropertyInfo property : properties.values() ) {
            // backReference don't need setter and identityProperty are handled differently
            if ( !property.isIgnored() && null == property.getBackReference() ) {
                if ( null == property.getManagedReference() ) {
                    source.println( "private void _%s(%s value, %s ctx) {", property.getPropertyName(), property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
                } else {
                    source.println( "private void _%s(%s value, %s<%s> deserializer, %s ctx) {", property.getPropertyName(), property
                        .getType().getParameterizedQualifiedSourceName(), JSON_DESERIALIZER_CLASS, property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
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
        source.println( "public %s build(%s ctx) {", info.getType()
            .getParameterizedQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
        source.indent();
        generateRequiredPropertiesCheck( source, requiredProperties );
        source.println( "return %s;", BEAN_INSTANCE_NAME );
        source.outdent();
        source.println( "}" );
    }

    private void generateInstanceBuilderSetterBodyForDefaultConstructor( SourceWriter source, PropertyInfo property ) {
        source.println( property.getSetterAccessor() + ";", "value" );
        if ( property.isRequired() ) {
            source.println( "this.%s = true;", String.format( IS_SET_FORMAT, property.getPropertyName() ) );
        }
        if ( null != property.getManagedReference() ) {
            source.println( "deserializer.setBackReference(\"%s\", %s, value, ctx);", property.getManagedReference(), BEAN_INSTANCE_NAME );
        }
    }

    /**
     * Generate the instance builder class body for a constructor with parameters or factory method. We will declare all the fields and
     * instanciate the bean only on build() method when all properties have been deserialiazed
     *
     * @param source writer
     * @param info info on bean
     * @param properties list of properties
     */
    private void generateInstanceBuilderClassBodyForConstructorOrFactoryMethod( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties ) {
        // will contain the instance once created
        source.println();
        source.println( "private %s %s;", info.getType()
            .getParameterizedQualifiedSourceName(), BEAN_INSTANCE_NAME, getQualifiedClassName() );

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
        for ( PropertyInfo property : properties.values() ) {
            if ( null == property.getBackReference() ) {
                source.println();
                source.println( "private %s _%s;", property.getType().getParameterizedQualifiedSourceName(), property.getPropertyName() );
                String isSetName = String.format( IS_SET_FORMAT, property.getPropertyName() );
                source.println( "private boolean %s;", isSetName );
                if ( property.isRequired() ) {
                    requiredProperties.put( isSetName, property );
                }
                if ( null != property.getManagedReference() ) {
                    source.println( "private %s<%s> %s;", JSON_DESERIALIZER_CLASS, property.getType()
                        .getParameterizedQualifiedSourceName(), String.format( BUILDER_DESERIALIZER_FORMAT, property.getPropertyName() ) );
                }
            }
        }

        source.println();

        for ( PropertyInfo property : properties.values() ) {
            if ( null == property.getBackReference() ) {
                if ( null == property.getManagedReference() ) {
                    source.println( "private void _%s(%s value, %s ctx) {", property.getPropertyName(), property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
                } else {
                    source.println( "private void _%s(%s value, %s<%s> deserializer, %s ctx) {", property.getPropertyName(), property
                        .getType().getParameterizedQualifiedSourceName(), JSON_DESERIALIZER_CLASS, property.getType()
                        .getParameterizedQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
                }

                source.indent();
                generateInstanceBuilderSetterBodyForConstructorOrFactoryMethod( source, info, property.getPropertyName(), info
                    .getCreatorParameters().containsKey( property.getPropertyName() ) );
                if ( null != property.getManagedReference() ) {
                    source.println( "this.%s = deserializer;", String.format( BUILDER_DESERIALIZER_FORMAT, property.getPropertyName() ) );
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
        source.println( "public %s build(%s ctx) {", info.getType()
            .getParameterizedQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
        source.indent();

        generateRequiredPropertiesCheck( source, requiredProperties );

        source.println( "if(null == this.%s) {", BEAN_INSTANCE_NAME );
        source.indent();
        source.println( "createInstance(ctx);" );
        source.outdent();
        source.println( "}" );

        // Writing the rest of the properties
        for ( Map.Entry<String, PropertyInfo> propertyEntry : properties.entrySet() ) {
            if ( !info.getCreatorParameters().containsKey( propertyEntry.getKey() ) && null == propertyEntry.getValue()
                .getBackReference() ) {
                source.println( "if (this.%s) {", String.format( IS_SET_FORMAT, propertyEntry.getKey() ) );
                source.indent();
                source.println( propertyEntry.getValue().getSetterAccessor() + ";", "this._" + propertyEntry.getKey() );
                if ( null != propertyEntry.getValue().getManagedReference() ) {
                    source.println( "%s.setBackReference(%s, %s, this._%s, ctx);", String.format( BUILDER_DESERIALIZER_FORMAT, propertyEntry
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
                                                                                 boolean creatorOrIdentityProperty ) {
        source.println( "this._%s = value;", fieldName );
        source.println( "this.%s = true;", String.format( IS_SET_FORMAT, fieldName ) );
        if ( creatorOrIdentityProperty ) {
            StringBuilder ifBuilder = new StringBuilder();
            for ( Map.Entry<String, JParameter> parameterEntry : info.getCreatorParameters().entrySet() ) {
                if ( ifBuilder.length() > 0 ) {
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

    private void generateInstanceBuilderCreateInstanceForConstructorOrFactoryMethod( SourceWriter source, BeanInfo info ) {
        // we compute the creator args
        StringBuilder parametersBuilder = new StringBuilder();
        for ( Map.Entry<String, JParameter> parameterEntry : info.getCreatorParameters().entrySet() ) {
            if ( parametersBuilder.length() > 0 ) {
                parametersBuilder.append( ", " );
            }
            parametersBuilder.append( "_" ).append( parameterEntry.getKey() );
        }

        source.println( "private void createInstance(%s ctx) {", JSON_DESERIALIZATION_CONTEXT_CLASS );
        source.indent();
        source.println( "this.%s = %s.newInstance(%s);", BEAN_INSTANCE_NAME, getQualifiedClassName(), parametersBuilder.toString() );
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
        PropertyInfo> properties ) throws UnableToCompleteException {
        // FIXME @JsonCreator with delegation
        logger.log( TreeLogger.Type.ERROR, "The delegation is not supported yet" );
        throw new UnableToCompleteException();
    }

    /**
     * Generates the if statement for required properties.
     */
    private void generateRequiredPropertiesCheck( SourceWriter source, Map<String, PropertyInfo> requiredProperties ) {
        if ( requiredProperties.isEmpty() ) {
            return;
        }

        source.print( "if(" );
        boolean first = true;
        for ( String isSetName : requiredProperties.keySet() ) {
            if ( !first ) {
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

    private void generateDefaultSuperclassInfo( SourceWriter source, BeanInfo info ) throws UnableToCompleteException {
        source.print( "new %s(", SUPERCLASS_DESERIALIZATION_INFO_CLASS );
        // gives the information about how to read and write the type info
        if ( null != info.getTypeInfo() ) {
            String typeInfoProperty = null;
            if ( JsonTypeInfo.As.PROPERTY.equals( info.getTypeInfo().include() ) ) {
                typeInfoProperty = "\"" + (info.getTypeInfo().property().isEmpty() ? info.getTypeInfo().use()
                    .getDefaultPropertyName() : info.getTypeInfo().property()) + "\"";
            }
            source.print( "com.fasterxml.jackson.annotation.JsonTypeInfo.As.%s, %s", info.getTypeInfo().include(), typeInfoProperty );
        }
        source.println( ")" );
        source.indent();

        generateSubtypeDeserializers( source, info );

        source.outdent();
    }

    private void generateSubtypeDeserializers( SourceWriter source, BeanInfo info ) throws UnableToCompleteException {
        if ( info.isInstantiable() ) {
            generateSubtypeDeserializer( source, info, info.getType() );
        }
        for ( JClassType subtype : info.getType().getSubtypes() ) {
            generateSubtypeDeserializer( source, info, subtype );
        }
    }

    private void generateSubtypeDeserializer( SourceWriter source, BeanInfo info, JClassType subtype ) throws UnableToCompleteException {
        String typeMetadata;
        if ( null == info.getTypeInfo() ) {
            typeMetadata = null;
        } else {
            typeMetadata = "\"" + extractTypeMetadata( info, subtype ) + "\"";
        }

        source.println( ".addSubtypeDeserializer( new %s<%s>() {", SUBTYPE_DESERIALIZER_CLASS, subtype.getQualifiedSourceName() );
        source.indent();
        source.indent();

        source.println( "@Override" );
        source.println( "public %s<%s, ?> newDeserializer(%s ctx) {", ABSTRACT_BEAN_JSON_DESERIALIZER_CLASS, subtype
            .getQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
        source.indent();
        String deserializer = info.getType() == subtype ? getQualifiedClassName() + ".this" : getJsonDeserializerFromType( subtype );
        source.println( "return %s;", deserializer );
        source.outdent();
        source.println( "}" );

        source.outdent();
        source.println( "}, %s.class, %s )", subtype.getQualifiedSourceName(), typeMetadata );
        source.println();

        source.outdent();
    }

    private void generateNewInstanceBuilderBody( SourceWriter source, BeanInfo info ) {
        if ( info.isInstantiable() ) {
            source.println( "return new %s();", info.getInstanceBuilderQualifiedName() );
        } else {
            source.println( "throw ctx.traceError(\"Cannot instantiate the type \" + %s.class.getName());", info.getType()
                .getQualifiedSourceName() );
        }
    }

    private void generatePropertyDeserializers( SourceWriter source, BeanInfo info, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException {
        for ( PropertyInfo property : properties.values() ) {

            if ( property.isIgnored() ) {
                // we add the name of the property to the ignoredProperties list
                source.println( "addIgnoredProperty(\"%s\");", property.getPropertyName() );
                continue;
            }

            String setterAccessor = property.getSetterAccessor();
            if ( null == setterAccessor ) {
                // there is no setter visible
                continue;
            }

            if ( null == property.getBackReference() ) {
                // this is not a back reference, we add the default deserializer
                source.println( "addProperty(\"%s\", new " + BEAN_PROPERTY_DESERIALIZER_CLASS + "<%s, %s, %s>() {", property
                    .getPropertyName(), info.getType().getParameterizedQualifiedSourceName(), info
                    .getInstanceBuilderQualifiedName(), getQualifiedClassName( property.getType() ) );

                source.indent();
                source.println( "@Override" );
                source.println( "protected %s<%s> newDeserializer(%s ctx) {", JSON_DESERIALIZER_CLASS, getQualifiedClassName( property
                    .getType() ), JSON_DESERIALIZATION_CONTEXT_CLASS );
                source.indent();
                source.println( "return %s;", getJsonDeserializerFromType( property.getType(), property ) );
                source.outdent();
                source.println( "}" );

                source.println( "@Override" );
                source.println( "public %s deserialize(%s reader, %s builder, %s ctx) {", getQualifiedClassName( property
                    .getType() ), JSON_READER_CLASS, info.getInstanceBuilderQualifiedName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
                source.indent();

                source.println( "%s<%s> deserializer = getDeserializer(ctx);", JSON_DESERIALIZER_CLASS, getQualifiedClassName( property
                    .getType() ) );
                source.println( "%s value = deserializer.deserialize(reader, ctx);", property.getType()
                    .getParameterizedQualifiedSourceName() );
                if ( null == property.getManagedReference() ) {
                    source.println( "builder._%s(value, ctx);", property.getPropertyName() );
                } else {
                    // it's a managed reference, we have to give the deserializer to the builder. It needs it to call the
                    // setBackReference
                    // method once the bean is instantiated.
                    source.println( "builder._%s(value, deserializer, ctx);", property.getPropertyName() );
                }
                source.println( "return value;" );
            } else {
                // this is a back reference, we add the special back reference property that will be called by the parent
                source.println( "addProperty(\"%s\", new " + BACK_REFERENCE_PROPERTY_BEAN_CLASS + "<%s, %s>() {", property
                    .getBackReference(), info.getType().getParameterizedQualifiedSourceName(), getQualifiedClassName( property
                    .getType() ) );

                source.indent();
                source.println( "@Override" );
                source.println( "public void setBackReference(%s %s, %s reference, %s ctx) {", info.getType()
                    .getParameterizedQualifiedSourceName(), BEAN_INSTANCE_NAME, property.getType()
                    .getParameterizedQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
                source.indent();

                source.println( property.getSetterAccessor() + ";", "reference" );
            }

            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "} );" );
        }
    }

    private void generateAdditionalMethods( SourceWriter source, Map<String, PropertyInfo> properties ) {
        for ( PropertyInfo property : properties.values() ) {
            for ( PropertyInfo.AdditionalMethod method : property.getAdditionalDeserializationMethods() ) {
                method.write( source );
                source.println();
            }
        }
    }

    private void generateNewInstanceMethod( SourceWriter source, BeanInfo info, Map<String, PropertyInfo> properties ) {
        JAbstractMethod method = info.getCreatorMethod();

        StringBuilder parametersBuilder = new StringBuilder();
        StringBuilder parametersNameBuilder = new StringBuilder();
        for ( Map.Entry<String, JParameter> parameterEntry : info.getCreatorParameters().entrySet() ) {
            if ( parametersBuilder.length() > 0 ) {
                parametersBuilder.append( ", " );
                parametersNameBuilder.append( ", " );
            }
            PropertyInfo property = properties.get( parameterEntry.getKey() );

            parametersBuilder.append( property.getType().getParameterizedQualifiedSourceName() ).append( " " ).append( property
                .getPropertyName() );
            parametersNameBuilder.append( property.getPropertyName() );
        }

        if ( method.isPrivate() ) {
            // private method, we use jsni
            source.println( "private static native %s newInstance(%s) /*-{", info.getType()
                .getParameterizedQualifiedSourceName(), parametersBuilder.toString() );
            source.indent();

            if ( null != method.isConstructor() ) {
                JConstructor constructor = method.isConstructor();
                source.println( "return %s(%s);", constructor.getJsniSignature(), parametersNameBuilder.toString() );
            } else {
                JMethod factory = method.isMethod();
                source.println( "return %s(%s);", factory.getJsniSignature(), parametersNameBuilder.toString() );
            }

            source.outdent();
            source.println( "}-*/;" );
        } else {
            source.println( "private static %s newInstance(%s) {", info.getType().getParameterizedQualifiedSourceName(), parametersBuilder
                .toString() );
            source.indent();

            if ( null != method.isConstructor() ) {
                source.println( "return new %s(%s);", info.getType().getParameterizedQualifiedSourceName(), parametersNameBuilder
                    .toString() );
            } else {
                source.println( "return %s.%s(%s);", info.getType().getQualifiedSourceName(), method.getName(), parametersNameBuilder
                    .toString() );
            }

            source.outdent();
            source.println( "}" );
        }
    }
}
