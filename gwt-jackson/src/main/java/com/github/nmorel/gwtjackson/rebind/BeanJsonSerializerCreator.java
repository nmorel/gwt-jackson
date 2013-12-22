/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.rebind;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer;
import com.github.nmorel.gwtjackson.rebind.FieldAccessor.Accessor;
import com.github.nmorel.gwtjackson.rebind.type.JSerializerType;
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

    private static final String JSON_SERIALIZER_PARAMETERS_CLASS = "com.github.nmorel.gwtjackson.client.JsonSerializerParameters";

    public BeanJsonSerializerCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration,
                                      JacksonTypeOracle typeOracle ) {
        super( logger, context, configuration, typeOracle );
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

        generateClassGetterMethod( source, beanInfo );
    }

    private void generateConstructors( SourceWriter source, BeanInfo beanInfo, Map<String, PropertyInfo> properties,
                                       TypeParameters typeParameters ) throws UnableToCompleteException {

        source.print( "public %s(", getSimpleClassName() );
        if ( null != typeParameters ) {
            source.print( typeParameters.getJoinedTypeParameterMappersWithType() );
        }
        source.println( ") {" );
        source.indent();
        source.print( "super(" );

        if ( beanInfo.getIdentityInfo().isPresent() ) {
            generateIdentifierSerializationInfo( source, beanInfo.getType(), beanInfo.getIdentityInfo().get() );
        } else {
            source.print( "null" );
        }
        source.print( ", " );

        if ( beanInfo.getTypeInfo().isPresent() ) {
            generateTypeInfo( source, beanInfo.getTypeInfo().get(), true );
        } else {
            source.print( "null" );
        }
        source.println( ");" );

        if ( null != typeParameters ) {
            source.println();
            for ( String parameterizedSerializer : typeParameters.getTypeParameterMapperNames() ) {
                source.println( "this.%s = %s%s;", parameterizedSerializer, TYPE_PARAMETER_PREFIX, parameterizedSerializer );
            }
        }

        source.println();

        generatePropertySerializers( source, beanInfo, properties );

        generateSubtypeSerializers( source, beanInfo );

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

            source.println( "addPropertySerializer(\"%s\", new " + BEAN_PROPERTY_SERIALIZER_CLASS + "<%s, %s>() {", property
                .getPropertyName(), getQualifiedClassName( beanInfo.getType() ), getQualifiedClassName( property.getType() ) );

            JSerializerType serializerType;
            if ( property.isRawValue() ) {
                serializerType = new JSerializerType.Builder().type( property.getType() ).instance( String
                    .format( "ctx" + ".<%s>getRawValueJsonSerializer()", property.getType().getParameterizedQualifiedSourceName() ) )
                    .build();
            } else {
                serializerType = getJsonSerializerFromType( property.getType() );
            }

            source.indent();
            source.println( "@Override" );
            source.println( "protected %s<%s> newSerializer(%s ctx) {", JSON_SERIALIZER_CLASS, getQualifiedClassName( property
                .getType() ), JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", serializerType.getInstance() );
            source.outdent();
            source.println( "}" );

            generatePropertySerializerParameters( source, property, serializerType );

            source.println();

            source.println( "@Override" );
            source.println( "public %s getValue(%s bean, %s ctx) {", getQualifiedClassName( property
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
            source.println();
        }
    }

    private void generatePropertySerializerParameters( SourceWriter source, PropertyInfo property,
                                                       JSerializerType serializerType ) throws UnableToCompleteException {
        if ( property.getFormat().isPresent() || property.getIgnoredProperties().isPresent() || property.getIgnoreUnknown().isPresent() ||
            property.getIdentityInfo().isPresent() || property.getTypeInfo().isPresent() || property.getInclude().isPresent() ) {

            JClassType annotatedType = findFirstTypeToApplyPropertyAnnotation( serializerType );

            source.println();

            source.println( "@Override" );
            source.println( "protected %s newParameters(%s ctx) {", JSON_SERIALIZER_PARAMETERS_CLASS, JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.print( "return new %s()", JSON_SERIALIZER_PARAMETERS_CLASS );

            source.indent();

            generateCommonPropertyParameters( source, property, serializerType );

            if ( property.getInclude().isPresent() ) {
                source.println();
                source.print( ".setInclude(%s.%s)", Include.class.getCanonicalName(), property.getInclude().get().name() );
            }

            if ( property.getIdentityInfo().isPresent() ) {
                source.println();
                source.print( ".setIdentityInfo(" );
                generateIdentifierSerializationInfo( source, annotatedType, property.getIdentityInfo().get() );
                source.print( ")" );
            }

            if ( property.getTypeInfo().isPresent() ) {
                source.println();
                source.print( ".setTypeInfo(" );
                generateTypeInfo( source, property.getTypeInfo().get(), true );
                source.print( ")" );
            }

            source.println( ";" );
            source.outdent();

            source.outdent();
            source.println( "}" );
        }
    }

    private void generateSubtypeSerializers( SourceWriter source, BeanInfo beanInfo ) throws UnableToCompleteException {
        JClassType[] subtypes = beanInfo.getType().getSubtypes();
        if ( subtypes.length == 0 ) {
            return;
        }

        for ( JClassType subtype : subtypes ) {
            source.println( "addSubtypeSerializer( %s.class, new %s<%s>() {", subtype.getQualifiedSourceName(), SubtypeSerializer.class
                .getName(), getQualifiedClassName( subtype ) );
            source.indent();

            source.println( "@Override" );
            source
                .println( "protected %s<%s> newSerializer(%s ctx) {", ABSTRACT_BEAN_JSON_SERIALIZER_CLASS,
                    getQualifiedClassName( subtype ), JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", getJsonSerializerFromType( subtype ).getInstance() );
            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "});" );
            source.println();
        }
    }

    private void generateClassGetterMethod( SourceWriter source, BeanInfo beanInfo ) {
        source.println( "@Override" );
        source.println( "public %s getSerializedType() {", Class.class.getName() );
        source.indent();
        source.println( "return %s.class;", beanInfo.getType().getQualifiedSourceName() );
        source.outdent();
        source.println( "}" );
    }
}
