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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.RawValueJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.IdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer.BeanSubtypeSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer.EnumSubtypeSerializer;
import com.github.nmorel.gwtjackson.rebind.FieldAccessor.Accessor;
import com.github.nmorel.gwtjackson.rebind.type.JSerializerType;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.thirdparty.guava.common.base.Predicate;
import com.google.gwt.thirdparty.guava.common.collect.Collections2;
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

        generateConstructors( source, typeParameters );

        source.println();

        if ( !properties.isEmpty() ) {
            Collection<PropertyInfo> propertyInfoList = Collections2.filter( properties.values(), new Predicate<PropertyInfo>() {
                @Override
                public boolean apply( @Nullable PropertyInfo propertyInfo ) {
                    return null != propertyInfo && propertyInfo.getGetterAccessor().isPresent() && !propertyInfo.isIgnored();
                }
            } );
            if ( !propertyInfoList.isEmpty() ) {
                generateInitSerializersMethod( source, beanInfo, propertyInfoList );
                source.println();
            }
        }

        if ( beanInfo.getIdentityInfo().isPresent() ) {
            generateInitIdentityInfoMethod( source, beanInfo );
            source.println();
        }

        if ( beanInfo.getTypeInfo().isPresent() ) {
            generateInitTypeInfoMethod( source, beanInfo );
            source.println();
        }

        if ( beanInfo.getType().getSubtypes().length > 0 ) {
            List<JClassType> subtypes = new ArrayList<JClassType>();
            for ( JClassType subtype : beanInfo.getType().getSubtypes() ) {
                if ( null == subtype.isInterface() && !subtype.isAbstract() ) {
                    subtypes.add( subtype );
                }
            }

            if ( !subtypes.isEmpty() ) {
                generateInitMapSubtypeClassToSerializerMethod( source, subtypes );
                source.println();
            }
        }

        generateClassGetterMethod( source, beanInfo );
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

    private void generateInitSerializersMethod( SourceWriter source, BeanInfo beanInfo, Collection<PropertyInfo> properties ) throws
            UnableToCompleteException {
        String mapType = String.format( "<%s, %s<%s, ?>>", String.class.getCanonicalName(), BEAN_PROPERTY_SERIALIZER_CLASS, beanInfo
                .getType().getParameterizedQualifiedSourceName() );
        String resultType = String.format( "%s%s", Map.class.getCanonicalName(), mapType );

        source.println( "@Override" );
        source.println( "protected %s initSerializers() {", resultType );
        source.indent();

        source.println( "%s map = new %s%s(%s);", resultType, LinkedHashMap.class.getCanonicalName(), mapType, properties.size() );
        source.println();
        for ( PropertyInfo property : properties ) {
            Accessor getterAccessor = property.getGetterAccessor().get().getAccessor( "bean", true );

            source.println( "map.put(\"%s\", new %s<%s, %s>() {", property
                    .getPropertyName(), BEAN_PROPERTY_SERIALIZER_CLASS, getQualifiedClassName( beanInfo
                    .getType() ), getQualifiedClassName( property.getType() ) );

            JSerializerType serializerType;
            if ( property.isRawValue() ) {
                serializerType = new JSerializerType.Builder().type( property.getType() ).instance( String
                        .format( "%s.<%s>getInstance()", RawValueJsonSerializer.class.getCanonicalName(), property.getType()
                                .getParameterizedQualifiedSourceName() ) ).build();
            } else {
                serializerType = getJsonSerializerFromType( property.getType() );
            }

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

        source.println( "return map;" );
        source.outdent();
        source.println( "}" );
    }

    private void generatePropertySerializerParameters( SourceWriter source, PropertyInfo property,
                                                       JSerializerType serializerType ) throws UnableToCompleteException {
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

    private void generateInitIdentityInfoMethod( SourceWriter source, BeanInfo beanInfo ) throws UnableToCompleteException {
        source.println( "@Override" );
        source.println( "protected %s<%s> initIdentityInfo() {", IdentitySerializationInfo.class.getCanonicalName(), beanInfo.getType()
                .getParameterizedQualifiedSourceName() );
        source.indent();
        source.print( "return " );
        generateIdentifierSerializationInfo( source, beanInfo.getType(), beanInfo.getIdentityInfo().get() );
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

    private void generateInitMapSubtypeClassToSerializerMethod( SourceWriter source, List<JClassType> subtypes ) throws
            UnableToCompleteException {

        String mapTypes = String.format( "<%s, %s>", Class.class.getCanonicalName(), SubtypeSerializer.class.getName() );
        String resultType = String.format( "%s%s", Map.class.getCanonicalName(), mapTypes );

        source.println( "@Override" );
        source.println( "protected %s initMapSubtypeClassToSerializer() {", resultType );

        source.indent();

        source.println( "%s map = new %s%s(%s);", resultType, IdentityHashMap.class.getCanonicalName(), mapTypes, subtypes.size() );
        source.println();

        for ( JClassType subtype : subtypes ) {
            String subtypeClass;
            String serializerClass;
            if ( null == subtype.isEnum() ) {
                subtypeClass = BeanSubtypeSerializer.class.getCanonicalName();
                serializerClass = String.format( "%s<?>", ABSTRACT_BEAN_JSON_SERIALIZER_CLASS );
            } else {
                // enum can be a subtype for interface. In this case, we handle them differently
                subtypeClass = EnumSubtypeSerializer.class.getCanonicalName();
                serializerClass = String.format( "%s<%s>", EnumJsonSerializer.class.getName(), getQualifiedClassName( subtype ) );
            }

            source.println( "map.put( %s.class, new %s<%s>() {", subtype
                    .getQualifiedSourceName(), subtypeClass, getQualifiedClassName( subtype ) );
            source.indent();

            source.println( "@Override" );
            source.println( "protected %s newSerializer() {", serializerClass );
            source.indent();
            source.println( "return %s;", getJsonSerializerFromType( subtype ).getInstance() );
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
