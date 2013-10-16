package com.github.nmorel.gwtjackson.rebind;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Nicolas Morel
 */
public class BeanJsonSerializerCreator extends AbstractBeanJsonCreator {

    private static final String BEAN_PROPERTY_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean.BeanPropertySerializer";

    private static final String SUPERCLASS_SERIALIZATION_INFO_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean" + "" +
        ".SuperclassSerializationInfo";

    private static final String SUBTYPE_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer";

    public BeanJsonSerializerCreator( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle ) {
        super( logger, context, typeOracle );
    }

    @Override
    protected boolean isSerializer() {
        return true;
    }

    @Override
    protected void writeClassBody( SourceWriter source, BeanInfo beanInfo, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException {
        source.println();

        TypeParameters typeParameters = generateTypeParameterMapperFields( source, beanInfo, JSON_SERIALIZER_CLASS,
            TYPE_PARAMETER_SERIALIZER_FIELD_NAME );

        if ( null != typeParameters ) {
            source.println();
        }

        generateConstructors( source, beanInfo, properties, typeParameters );

        source.println();

        generateAdditionalMethods( source, properties );

        source.commit( logger );
    }

    private void generateConstructors( SourceWriter source, BeanInfo beanInfo, Map<String, PropertyInfo> properties,
                                       TypeParameters typeParameters ) throws UnableToCompleteException {

        source.print( "public %s(", getSimpleClassName() );
        if ( null != typeParameters ) {
            source.print( typeParameters.getJoinedTypeParameterMappersWithType() );
        }
        source.println( ") {" );
        source.indent();
        source.print( "this(" );
        if ( null != typeParameters ) {
            source.print( "%s, ", typeParameters.getJoinedTypeParameterMappersWithoutType() );
        }
        source.println( "null, null);" );
        source.outdent();
        source.println( "}" );

        source.println();

        source.print( "public %s(", getSimpleClassName() );
        if ( null != typeParameters ) {
            source.print( "%s, ", typeParameters.getJoinedTypeParameterMappersWithType() );
        }
        source.println( "%s<%s, ?> idProperty, %s<%s> superclassInfo) {", IDENTITY_SERIALIZATION_INFO_CLASS, beanInfo.getType()
            .getParameterizedQualifiedSourceName(), SUPERCLASS_SERIALIZATION_INFO_CLASS, beanInfo.getType()
            .getParameterizedQualifiedSourceName() );
        source.indent();
        source.println( "super();" );

        source.println();

        if ( null != typeParameters ) {
            for ( String parameterizedSerializer : typeParameters.getTypeParameterMapperNames() ) {
                source.println( "this.%s = %s%s;", parameterizedSerializer, TYPE_PARAMETER_PREFIX, parameterizedSerializer );
            }
            source.println();
        }

        if ( null != beanInfo.getIdentityInfo() ) {
            source.println( "if(null == idProperty) {" );
            source.indent();
            source.print( "setIdentityInfo(" );
            generateIdentifierSerializationInfo( source, beanInfo.getType(), beanInfo.getIdentityInfo() );
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

        generatePropertySerializers( source, beanInfo, properties );

        source.println();

        source.outdent();
        source.println( "}" );
    }

    private void generateDefaultSuperclassInfo( SourceWriter source, BeanInfo info ) throws UnableToCompleteException {
        source.print( "new %s(", SUPERCLASS_SERIALIZATION_INFO_CLASS );
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

        generateSubtypeSerializers( source, info );

        source.outdent();
    }

    private void generateSubtypeSerializers( SourceWriter source, BeanInfo info ) throws UnableToCompleteException {
        if ( info.isInstantiable() ) {
            generateSerializer( source, info, info.getType() );
        }
        for ( JClassType subtype : info.getType().getSubtypes() ) {
            generateSerializer( source, info, subtype );
        }
    }

    private void generateSerializer( SourceWriter source, BeanInfo info, JClassType subtype ) throws UnableToCompleteException {
        String typeMetadata;
        if ( null == info.getTypeInfo() ) {
            typeMetadata = null;
        } else {
            typeMetadata = "\"" + extractTypeMetadata( info, subtype ) + "\"";
        }

        source.println( ".addSubtypeSerializer( new %s<%s>() {", SUBTYPE_SERIALIZER_CLASS, subtype.getQualifiedSourceName() );
        source.indent();
        source.indent();

        source.println( "@Override" );
        source.println( "protected %s<%s> newSerializer( %s ctx ) {", ABSTRACT_BEAN_JSON_SERIALIZER_CLASS, subtype
            .getQualifiedSourceName(), JSON_SERIALIZATION_CONTEXT_CLASS );
        source.indent();
        String serializer = info.getType() == subtype ? getQualifiedClassName() + ".this" : getJsonSerializerFromType( subtype );
        source.println( "return %s;", serializer );
        source.outdent();
        source.println( "}" );

        source.outdent();
        source.println( "}, %s.class, %s )", subtype.getQualifiedSourceName(), typeMetadata );
        source.println();

        source.outdent();
    }

    private void generatePropertySerializers( SourceWriter source, BeanInfo beanInfo, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException {
        for ( PropertyInfo property : properties.values() ) {
            String getterAccessor = property.getGetterAccessor();
            if ( null == getterAccessor || property.isIgnored() ) {
                // there is no getter visible or the property is ignored
                continue;
            }

            source.println( "if(null == getIdentityInfo() || !getIdentityInfo().getPropertyName().equals(\"%s\")) {", property
                .getPropertyName() );
            source.indent();

            source.println( "addPropertySerializer(\"%s\", new " + BEAN_PROPERTY_SERIALIZER_CLASS + "<%s, %s>() {", property
                .getPropertyName(), getQualifiedClassName( beanInfo.getType() ), getQualifiedClassName( property.getType() ) );

            source.indent();
            source.println( "@Override" );
            source.println( "protected %s<%s> newSerializer(%s ctx) {", JSON_SERIALIZER_CLASS, getQualifiedClassName( property
                .getType() ), JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", getJsonSerializerFromType( property.getType(), property ) );
            source.outdent();
            source.println( "}" );

            source.println( "@Override" );
            source.println( "protected %s getValue(%s bean, %s ctx) {", getQualifiedClassName( property
                .getType() ), getQualifiedClassName( beanInfo.getType() ), JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", getterAccessor );
            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "} );" );

            source.outdent();
            source.println( "}" );
        }
    }

    private void generateAdditionalMethods( SourceWriter source, Map<String, PropertyInfo> properties ) {
        for ( PropertyInfo property : properties.values() ) {
            for ( PropertyInfo.AdditionalMethod method : property.getAdditionalSerializationMethods() ) {
                method.write( source );
                source.println();
            }
        }
    }
}
