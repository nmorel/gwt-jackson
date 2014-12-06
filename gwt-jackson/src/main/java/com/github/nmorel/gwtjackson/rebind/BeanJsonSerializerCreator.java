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

import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.RawValueJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.IdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer.BeanSubtypeSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer.DefaultSubtypeSerializer;
import com.github.nmorel.gwtjackson.rebind.bean.BeanInfo;
import com.github.nmorel.gwtjackson.rebind.exception.UnsupportedTypeException;
import com.github.nmorel.gwtjackson.rebind.property.FieldAccessor.Accessor;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.github.nmorel.gwtjackson.rebind.type.JSerializerType;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Nicolas Morel
 */
public class BeanJsonSerializerCreator extends AbstractBeanJsonCreator {

    private static final String BEAN_PROPERTY_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean.BeanPropertySerializer";

    private static final String JSON_SERIALIZER_PARAMETERS_CLASS = "com.github.nmorel.gwtjackson.client.JsonSerializerParameters";

    public BeanJsonSerializerCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration, JacksonTypeOracle
            typeOracle ) {
        super( logger, context, configuration, typeOracle );
    }

    @Override
    protected boolean isSerializer() {
        return true;
    }

    @Override
    protected void writeClassBody( SourceWriter source, BeanInfo beanInfo, ImmutableMap<String, PropertyInfo> properties ) throws
            UnableToCompleteException {
        source.println();

        TypeParameters typeParameters = generateTypeParameterMapperFields( source, beanInfo, JSON_SERIALIZER_CLASS,
                TYPE_PARAMETER_SERIALIZER_FIELD_NAME );

        if ( null != typeParameters ) {
            source.println();
        }

        generateConstructors( source, typeParameters );

        source.println();

        if ( !properties.isEmpty() ) {
            if ( beanInfo.getValuePropertyInfo().isPresent() ) {
                generateInitValueSerializerMethod( source, beanInfo, beanInfo.getValuePropertyInfo().get() );
            } else {
                Map<PropertyInfo, JSerializerType> propertiesMap = new LinkedHashMap<PropertyInfo, JSerializerType>();
                for ( PropertyInfo propertyInfo : properties.values() ) {
                    JSerializerType serializerType = getJsonSerializerFromProperty( propertyInfo );
                    if ( null != serializerType ) {
                        propertiesMap.put( propertyInfo, serializerType );
                    }
                }
                if ( !propertiesMap.isEmpty() ) {
                    generateInitSerializersMethod( source, beanInfo, propertiesMap );
                    source.println();
                }
            }
        }

        if ( beanInfo.getIdentityInfo().isPresent() ) {
            try {
                Optional<JSerializerType> serializerType = getIdentitySerializerType( beanInfo.getIdentityInfo().get() );
                generateInitIdentityInfoMethod( source, beanInfo, serializerType );
                source.println();
            } catch ( UnsupportedTypeException e ) {
                logger.log( Type.WARN, "Identity type is not supported. We ignore it." );
            }
        }

        if ( beanInfo.getTypeInfo().isPresent() ) {
            generateInitTypeInfoMethod( source, beanInfo );
            source.println();
        }

        ImmutableList<JClassType> subtypes = filterSubtypes( beanInfo );
        if ( !subtypes.isEmpty() ) {
            generateInitMapSubtypeClassToSerializerMethod( source, subtypes );
            source.println();
        }

        generateClassGetterMethod( source, beanInfo );
    }

    private JSerializerType getJsonSerializerFromProperty( PropertyInfo propertyInfo ) throws UnableToCompleteException {
        if ( null != propertyInfo && propertyInfo.getGetterAccessor().isPresent() && !propertyInfo.isIgnored() ) {
            if ( propertyInfo.isRawValue() ) {
                return new JSerializerType.Builder().type( propertyInfo.getType() ).instance( String
                        .format( "%s.<%s>getInstance()", RawValueJsonSerializer.class.getCanonicalName(), propertyInfo.getType()
                                .getParameterizedQualifiedSourceName() ) ).build();
            } else {
                try {
                    return getJsonSerializerFromType( propertyInfo.getType() );
                } catch ( UnsupportedTypeException e ) {
                    logger.log( Type.WARN, "Property '" + propertyInfo.getPropertyName() + "' is ignored." );
                }
            }
        }
        return null;
    }

    private void generateConstructors( SourceWriter source, TypeParameters typeParameters ) throws UnableToCompleteException {

        source.print( "public %s(", getSimpleClassName() );
        if ( null != typeParameters ) {
            source.print( typeParameters.getJoinedTypeParameterMappersWithType() );
        }
        source.print( ") {" );

        if ( null != typeParameters ) {
            source.println();
            source.indent();
            for ( String parameterizedSerializer : typeParameters.getTypeParameterMapperNames() ) {
                source.println( "this.%s = %s%s;", parameterizedSerializer, TYPE_PARAMETER_PREFIX, parameterizedSerializer );
            }
            source.outdent();
        }

        source.println( "}" );
    }

    private void generateInitValueSerializerMethod( SourceWriter source, BeanInfo beanInfo, PropertyInfo propertyInfo ) throws
            UnableToCompleteException {
        source.println( "@Override" );
        source.println( "protected %s<%s, ?> initValueSerializer() {", BEAN_PROPERTY_SERIALIZER_CLASS, beanInfo.getType()
                .getParameterizedQualifiedSourceName() );
        source.indent();

        source.print( "return " );
        generateSerializer( source, beanInfo, propertyInfo, getJsonSerializerFromProperty( propertyInfo ) );
        source.println( ";" );

        source.outdent();
        source.println( "}" );
        source.println();
    }

    private void generateInitSerializersMethod( SourceWriter source, BeanInfo beanInfo, Map<PropertyInfo, JSerializerType> properties )
            throws UnableToCompleteException {
        String mapType = String.format( "<%s, %s<%s, ?>>", String.class.getCanonicalName(), BEAN_PROPERTY_SERIALIZER_CLASS, beanInfo
                .getType().getParameterizedQualifiedSourceName() );
        String resultType = String.format( "%s%s", Map.class.getCanonicalName(), mapType );

        source.println( "@Override" );
        source.println( "protected %s initSerializers() {", resultType );
        source.indent();

        source.println( "%s map = new %s%s(%s);", resultType, LinkedHashMap.class.getCanonicalName(), mapType, properties.size() );
        source.println();

        for ( Entry<PropertyInfo, JSerializerType> entry : properties.entrySet() ) {
            source.print( "map.put(\"%s\", ", entry.getKey().getPropertyName() );
            generateSerializer( source, beanInfo, entry.getKey(), entry.getValue() );
            source.println( ");" );
            source.println();
        }

        source.println( "return map;" );
        source.outdent();
        source.println( "}" );
    }

    private void generateSerializer( SourceWriter source, BeanInfo beanInfo, PropertyInfo property, JSerializerType serializerType )
            throws UnableToCompleteException {
        Accessor getterAccessor = property.getGetterAccessor().get().getAccessor( "bean" );

        source.println( "new %s<%s, %s>() {", BEAN_PROPERTY_SERIALIZER_CLASS, getParameterizedQualifiedClassName( beanInfo
                .getType() ), getParameterizedQualifiedClassName( property.getType() ) );

        source.indent();
        source.println( "@Override" );
        source.println( "protected %s<?> newSerializer() {", JSON_SERIALIZER_CLASS );
        source.indent();
        source.println( "return %s;", serializerType.getInstance() );
        source.outdent();
        source.println( "}" );

        generatePropertySerializerParameters( source, property, serializerType );

        source.println();

        source.println( "@Override" );
        source.println( "public %s getValue(%s bean, %s ctx) {", getParameterizedQualifiedClassName( property
                .getType() ), getParameterizedQualifiedClassName( beanInfo.getType() ), JSON_SERIALIZATION_CONTEXT_CLASS );
        source.indent();
        source.println( "return %s;", getterAccessor.getAccessor() );
        source.outdent();
        source.println( "}" );

        if ( getterAccessor.getAdditionalMethod().isPresent() ) {
            source.println();
            getterAccessor.getAdditionalMethod().get().write( source );
        }

        source.outdent();
        source.print( "}" );
    }

    private void generatePropertySerializerParameters( SourceWriter source, PropertyInfo property, JSerializerType serializerType )
            throws UnableToCompleteException {
        if ( property.getFormat().isPresent() || property.getIgnoredProperties().isPresent() || property.getIgnoreUnknown().isPresent() ||
                property.getIdentityInfo().isPresent() || property.getTypeInfo().isPresent() || property.getInclude().isPresent() ) {

            JClassType annotatedType = findFirstTypeToApplyPropertyAnnotation( serializerType );

            source.println();

            source.println( "@Override" );
            source.println( "protected %s newParameters() {", JSON_SERIALIZER_PARAMETERS_CLASS );
            source.indent();
            source.print( "return new %s()", JSON_SERIALIZER_PARAMETERS_CLASS );

            source.indent();

            generateCommonPropertyParameters( source, property, serializerType );

            if ( property.getInclude().isPresent() ) {
                source.println();
                source.print( ".setInclude(%s.%s)", Include.class.getCanonicalName(), property.getInclude().get().name() );
            }

            if ( property.getIdentityInfo().isPresent() ) {
                try {
                    Optional<JSerializerType> identitySerializerType = getIdentitySerializerType( property.getIdentityInfo().get() );
                    source.println();
                    source.print( ".setIdentityInfo(" );
                    generateIdentifierSerializationInfo( source, annotatedType, property.getIdentityInfo().get(), identitySerializerType );
                    source.print( ")" );
                } catch ( UnsupportedTypeException e ) {
                    logger.log( Type.WARN, "Identity type is not supported. We ignore it." );
                }
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

    private void generateInitIdentityInfoMethod( SourceWriter source, BeanInfo beanInfo, Optional<JSerializerType> serializerType )
            throws UnableToCompleteException {
        source.println( "@Override" );
        source.println( "protected %s<%s> initIdentityInfo() {", IdentitySerializationInfo.class.getCanonicalName(), beanInfo.getType()
                .getParameterizedQualifiedSourceName() );
        source.indent();
        source.print( "return " );
        generateIdentifierSerializationInfo( source, beanInfo.getType(), beanInfo.getIdentityInfo().get(), serializerType );
        source.println( ";" );
        source.outdent();
        source.println( "}" );
    }

    private void generateInitTypeInfoMethod( SourceWriter source, BeanInfo beanInfo ) throws UnableToCompleteException {
        source.println( "@Override" );
        source.println( "protected %s<%s> initTypeInfo() {", TYPE_SERIALIZATION_INFO_CLASS, beanInfo.getType()
                .getParameterizedQualifiedSourceName() );
        source.indent();
        source.print( "return " );
        generateTypeInfo( source, beanInfo.getTypeInfo().get(), true );
        source.println( ";" );
        source.outdent();
        source.println( "}" );
    }

    private void generateInitMapSubtypeClassToSerializerMethod( SourceWriter source, ImmutableList<JClassType> subtypes ) throws
            UnableToCompleteException {

        String mapTypes = String.format( "<%s, %s>", Class.class.getCanonicalName(), SubtypeSerializer.class.getName() );
        String resultType = String.format( "%s%s", Map.class.getCanonicalName(), mapTypes );

        source.println( "@Override" );
        source.println( "protected %s initMapSubtypeClassToSerializer() {", resultType );

        source.indent();

        source.println( "%s map = new %s%s(%s);", resultType, IdentityHashMap.class.getCanonicalName(), mapTypes, subtypes.size() );
        source.println();

        for ( JClassType subtype : subtypes ) {

            JSerializerType serializerType;
            try {
                serializerType = getJsonSerializerFromType( subtype, true );
            } catch ( UnsupportedTypeException e ) {
                logger.log( Type.WARN, "Subtype '" + subtype.getQualifiedSourceName() + "' is not supported. We ignore it." );
                continue;
            }

            String subtypeClass;
            String serializerClass;
            if ( configuration.getSerializer( subtype ).isPresent() || null != subtype.isEnum() || Enum.class.getName().equals( subtype
                    .getQualifiedSourceName() ) ) {
                subtypeClass = DefaultSubtypeSerializer.class.getCanonicalName();
                serializerClass = String.format( "%s<?>", JsonSerializer.class.getName() );
            } else {
                subtypeClass = BeanSubtypeSerializer.class.getCanonicalName();
                serializerClass = String.format( "%s<?>", ABSTRACT_BEAN_JSON_SERIALIZER_CLASS );
            }

            source.println( "map.put( %s.class, new %s() {", subtype.getQualifiedSourceName(), subtypeClass );
            source.indent();

            source.println( "@Override" );
            source.println( "protected %s newSerializer() {", serializerClass );
            source.indent();
            source.println( "return %s;", serializerType.getInstance() );
            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "});" );
            source.println();
        }

        source.println( "return map;" );

        source.outdent();
        source.println( "}" );
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
