package com.github.nmorel.gwtjackson.rebind;

import java.util.Map;

import com.github.nmorel.gwtjackson.rebind.FieldAccessor.Accessor;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Nicolas Morel
 */
public class BeanJsonSerializerCreator extends AbstractBeanJsonCreator {

    private static final String BEAN_PROPERTY_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean.BeanPropertySerializer";

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

        source.println();

        if ( beanInfo.getTypeInfo().isPresent() || beanInfo.getType().getSubtypes().length > 0 ) {
            source.println( "if(null == superclassInfo) {" );
            source.indent();
            source.print( "setSuperclassInfo(" );
            generateSuperclassSerializationInfo( source, beanInfo, beanInfo.getTypeInfo() );
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

    private void generatePropertySerializers( SourceWriter source, BeanInfo beanInfo, Map<String,
        PropertyInfo> properties ) throws UnableToCompleteException {
        for ( PropertyInfo property : properties.values() ) {
            if ( !property.getGetterAccessor().isPresent() || property.isIgnored() ) {
                // there is no getter visible or the property is ignored
                continue;
            }

            Accessor getterAccessor = property.getGetterAccessor().get().getAccessor( "bean", true );

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

            source.println();

            source.println( "@Override" );
            source.println( "protected %s getValue(%s bean, %s ctx) {", getQualifiedClassName( property
                .getType() ), getQualifiedClassName( beanInfo.getType() ), JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", getterAccessor.getAccessor() );
            source.outdent();
            source.println( "}" );

            if ( getterAccessor.getAdditionalMethod().isPresent() ) {
                source.println();
                getterAccessor.getAdditionalMethod().get().write( source );
            }

            source.outdent();
            source.println( "});" );

            source.outdent();
            source.println( "}" );
        }
    }
}
