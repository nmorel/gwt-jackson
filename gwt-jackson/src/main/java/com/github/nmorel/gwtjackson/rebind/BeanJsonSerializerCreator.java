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

import javax.lang.model.element.Modifier;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.ser.RawValueJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractIdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.AnyGetterPropertySerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.BeanPropertySerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.IdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.ObjectIdSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.PropertyIdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer.BeanSubtypeSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer.DefaultSubtypeSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.TypeSerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.map.MapJsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.rebind.bean.BeanIdentityInfo;
import com.github.nmorel.gwtjackson.rebind.exception.UnsupportedTypeException;
import com.github.nmorel.gwtjackson.rebind.property.FieldAccessor.Accessor;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.github.nmorel.gwtjackson.rebind.type.JSerializerType;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.escapeString;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstTypeToApplyPropertyAnnotation;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.DEFAULT_WILDCARD;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.parameterizedName;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.rawName;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.typeName;

/**
 * <p>BeanJsonSerializerCreator class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class BeanJsonSerializerCreator extends AbstractBeanJsonCreator {

    /**
     * <p>Constructor for BeanJsonSerializerCreator.</p>
     *
     * @param logger a {@link com.google.gwt.core.ext.TreeLogger} object.
     * @param context a {@link com.google.gwt.core.ext.GeneratorContext} object.
     * @param configuration a {@link com.github.nmorel.gwtjackson.rebind.RebindConfiguration} object.
     * @param typeOracle a {@link com.github.nmorel.gwtjackson.rebind.JacksonTypeOracle} object.
     * @param beanType a {@link com.google.gwt.core.ext.typeinfo.JClassType} object.
     * @throws com.google.gwt.core.ext.UnableToCompleteException if any.
     */
    public BeanJsonSerializerCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration, JacksonTypeOracle
            typeOracle, JClassType beanType ) throws UnableToCompleteException {
        super( logger, context, configuration, typeOracle, beanType );
    }

    /** {@inheritDoc} */
    @Override
    protected final boolean isSerializer() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected final void buildSpecific( TypeSpec.Builder typeBuilder ) throws UnableToCompleteException {

        if ( !properties.isEmpty() ) {
            if ( beanInfo.getValuePropertyInfo().isPresent() ) {
                typeBuilder.addMethod( buildInitValueSerializerMethod( beanInfo.getValuePropertyInfo().get() ) );
            } else {
                Map<PropertyInfo, JSerializerType> propertiesMap = new LinkedHashMap<PropertyInfo, JSerializerType>();
                for ( PropertyInfo propertyInfo : properties.values() ) {
                    JSerializerType serializerType = getJsonSerializerFromProperty( propertyInfo );
                    if ( null != serializerType ) {
                        propertiesMap.put( propertyInfo, serializerType );
                    }
                }
                if ( !propertiesMap.isEmpty() ) {
                    typeBuilder.addMethod( buildInitSerializersMethod( propertiesMap ) );
                }
            }
        }

        if ( beanInfo.getAnyGetterPropertyInfo().isPresent() ) {
            typeBuilder.addMethod( buildInitAnyGetterPropertySerializerMethod( beanInfo.getAnyGetterPropertyInfo().get() ) );
        }

        if ( beanInfo.getIdentityInfo().isPresent() ) {
            try {
                Optional<JSerializerType> serializerType = getIdentitySerializerType( beanInfo.getIdentityInfo().get() );
                typeBuilder.addMethod( buildInitIdentityInfoMethod( serializerType ) );
            } catch ( UnsupportedTypeException e ) {
                logger.log( Type.WARN, "Identity type is not supported. We ignore it." );
            }
        }

        if ( beanInfo.getTypeInfo().isPresent() ) {
            typeBuilder.addMethod( buildInitTypeInfoMethod() );
        }

        ImmutableList<JClassType> subtypes = filterSubtypes();
        if ( !subtypes.isEmpty() ) {
            typeBuilder.addMethod( buildInitMapSubtypeClassToSerializerMethod( subtypes ) );
        }
    }

    private JSerializerType getJsonSerializerFromProperty( PropertyInfo propertyInfo ) throws UnableToCompleteException {
        if ( null != propertyInfo && propertyInfo.getGetterAccessor().isPresent() && !propertyInfo.isIgnored() ) {
            if ( propertyInfo.isRawValue() ) {
                return new JSerializerType.Builder().type( propertyInfo.getType() ).instance( CodeBlock.builder()
                        .add( "$T.<$T>getInstance()", RawValueJsonSerializer.class, typeName( propertyInfo.getType() ) ).build() )
                        .build();
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

    private MethodSpec buildInitValueSerializerMethod( PropertyInfo propertyInfo ) throws UnableToCompleteException {
        return MethodSpec.methodBuilder( "initValueSerializer" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( ParameterizedTypeName.get(
                        ClassName.get( BeanPropertySerializer.class ), typeName( beanInfo.getType() ), DEFAULT_WILDCARD ) )
                .addStatement( "return $L", buildSerializer( propertyInfo, getJsonSerializerFromProperty( propertyInfo ) ) )
                .build();
    }

    private MethodSpec buildInitSerializersMethod( Map<PropertyInfo, JSerializerType> properties )
            throws UnableToCompleteException {
        MethodSpec.Builder builder = MethodSpec.methodBuilder( "initSerializers" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( ArrayTypeName.of( BeanPropertySerializer.class ) )
                .addStatement( "$T result = new $T[$L]",
                        ArrayTypeName.of( BeanPropertySerializer.class ), BeanPropertySerializer.class, properties.size() );

        int i = 0;
        for ( Entry<PropertyInfo, JSerializerType> entry : properties.entrySet() ) {
            builder.addStatement( "result[$L] = $L", i++, buildSerializer( entry.getKey(), entry.getValue() ) );
        }

        builder.addStatement( "return result" );
        return builder.build();
    }

    private MethodSpec buildInitAnyGetterPropertySerializerMethod( PropertyInfo anyGetterPropertyInfo )
            throws UnableToCompleteException {
        return MethodSpec.methodBuilder( "initAnyGetterPropertySerializer" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( parameterizedName( AnyGetterPropertySerializer.class, beanInfo.getType() ) )
                .addStatement( "return $L",
                        buildSerializer( anyGetterPropertyInfo, getJsonSerializerFromProperty( anyGetterPropertyInfo ) ) )
                .build();
    }

    private TypeSpec buildSerializer( PropertyInfo property, JSerializerType serializerType ) throws UnableToCompleteException {

        TypeSpec.Builder builder;

        String escapedPropertyName = escapeString( property.getPropertyName() );

        if ( property.isAnyGetter() ) {
            builder = TypeSpec.anonymousClassBuilder( "" )
                    .superclass( parameterizedName( AnyGetterPropertySerializer.class, beanInfo.getType() ) );
        } else {
            builder = TypeSpec.anonymousClassBuilder( "\"$L\"", escapedPropertyName )
                    .superclass( parameterizedName( BeanPropertySerializer.class, beanInfo.getType(), property.getType() ) );
        }

        buildBeanPropertySerializerBody( builder, beanInfo.getType(), property, serializerType );

        boolean requireEscaping = !property.getPropertyName().equals( escapedPropertyName );
        if ( property.isUnwrapped() || requireEscaping ) {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder( "serializePropertyName" )
                    .addModifiers( Modifier.PUBLIC )
                    .addAnnotation( Override.class )
                    .addParameter( JsonWriter.class, "writer" )
                    .addParameter( typeName( beanInfo.getType() ), "bean" )
                    .addParameter( JsonSerializationContext.class, "ctx" );
            if ( !property.isUnwrapped() ) {
                methodBuilder.addStatement( "writer.name(propertyName)" );
            }
            builder.addMethod( methodBuilder.build() );
        }

        return builder.build();
    }

    private MethodSpec buildInitIdentityInfoMethod( Optional<JSerializerType> serializerType )
            throws UnableToCompleteException, UnsupportedTypeException {
        return MethodSpec.methodBuilder( "initIdentityInfo" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( parameterizedName( IdentitySerializationInfo.class, beanInfo.getType() ) )
                .addStatement( "return $L",
                        generateIdentifierSerializationInfo( beanInfo.getType(), beanInfo.getIdentityInfo().get(), serializerType ) )
                .build();
    }

    private MethodSpec buildInitTypeInfoMethod() {
        return MethodSpec.methodBuilder( "initTypeInfo" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( parameterizedName( TypeSerializationInfo.class, beanInfo.getType() ) )
                .addStatement( "return $L", generateTypeInfo( beanInfo.getTypeInfo().get() ) )
                .build();
    }

    private MethodSpec buildInitMapSubtypeClassToSerializerMethod( ImmutableList<JClassType> subtypes )
            throws UnableToCompleteException {

        Class[] mapTypes = new Class[]{Class.class, SubtypeSerializer.class};
        TypeName resultType = ParameterizedTypeName.get( Map.class, mapTypes );

        MethodSpec.Builder builder = MethodSpec.methodBuilder( "initMapSubtypeClassToSerializer" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( resultType )
                .addStatement( "$T map = new $T($L)",
                        resultType, ParameterizedTypeName.get( IdentityHashMap.class, mapTypes ), subtypes.size() );

        for ( JClassType subtype : subtypes ) {

            JSerializerType serializerType;
            try {
                serializerType = getJsonSerializerFromType( subtype, true );
            } catch ( UnsupportedTypeException e ) {
                logger.log( Type.WARN, "Subtype '" + subtype.getQualifiedSourceName() + "' is not supported. We ignore it." );
                continue;
            }

            Class subtypeClass;
            TypeName serializerClass;
            if ( configuration.getSerializer( subtype ).isPresent()
                    || null != subtype.isEnum()
                    || Enum.class.getName().equals( subtype.getQualifiedSourceName() ) ) {
                subtypeClass = DefaultSubtypeSerializer.class;
                serializerClass = ParameterizedTypeName.get( ClassName.get( JsonSerializer.class ), DEFAULT_WILDCARD );
            } else {
                subtypeClass = BeanSubtypeSerializer.class;
                serializerClass = ParameterizedTypeName.get( ClassName.get( AbstractBeanJsonSerializer.class ), DEFAULT_WILDCARD );
            }

            TypeSpec subtypeType = TypeSpec.anonymousClassBuilder( "" )
                    .superclass( subtypeClass )
                    .addMethod( MethodSpec.methodBuilder( "newSerializer" )
                                    .addModifiers( Modifier.PROTECTED )
                                    .addAnnotation( Override.class )
                                    .returns( serializerClass )
                                    .addStatement( "return $L", serializerType.getInstance() )
                                    .build()
                    ).build();

            builder.addStatement( "map.put($T.class, $L)", rawName( subtype ), subtypeType );
        }

        builder.addStatement( "return map" );
        return builder.build();
    }

    private Optional<JSerializerType> getIdentitySerializerType( BeanIdentityInfo identityInfo ) throws UnableToCompleteException,
            UnsupportedTypeException {
        if ( identityInfo.isIdABeanProperty() ) {
            return Optional.absent();
        } else {
            return Optional.of( getJsonSerializerFromType( identityInfo.getType().get() ) );
        }
    }

    private TypeSpec generateIdentifierSerializationInfo( JClassType type, BeanIdentityInfo identityInfo,
                                                          Optional<JSerializerType> serializerType ) throws UnableToCompleteException,
            UnsupportedTypeException {

        TypeSpec.Builder builder = TypeSpec
                .anonymousClassBuilder( "$L, $S", identityInfo.isAlwaysAsId(), identityInfo.getPropertyName() );

        if ( identityInfo.isIdABeanProperty() ) {

            BeanJsonMapperInfo mapperInfo = getMapperInfo( type );
            PropertyInfo propertyInfo = mapperInfo.getProperties().get( identityInfo.getPropertyName() );
            JSerializerType propertySerializerType = getJsonSerializerFromType( propertyInfo.getType() );

            builder.superclass( parameterizedName( PropertyIdentitySerializationInfo.class, type, propertyInfo.getType() ) );

            buildBeanPropertySerializerBody( builder, type, propertyInfo, propertySerializerType );

        } else {
            JType qualifiedType = identityInfo.getType().get();

            builder.superclass( parameterizedName( AbstractIdentitySerializationInfo.class, type, qualifiedType ) );

            builder.addMethod( MethodSpec.methodBuilder( "newSerializer" )
                    .addModifiers( Modifier.PROTECTED )
                    .addAnnotation( Override.class )
                    .returns( ParameterizedTypeName.get( ClassName.get( JsonSerializer.class ), DEFAULT_WILDCARD ) )
                    .addStatement( "return $L", serializerType.get().getInstance() )
                    .build() );

            TypeName generatorType = parameterizedName( ObjectIdGenerator.class, qualifiedType );
            TypeName returnType = parameterizedName( ObjectIdSerializer.class, qualifiedType );

            builder.addMethod( MethodSpec.methodBuilder( "getObjectId" )
                    .addModifiers( Modifier.PUBLIC )
                    .addAnnotation( Override.class )
                    .returns( returnType )
                    .addParameter( typeName( type ), "bean" )
                    .addParameter( JsonSerializationContext.class, "ctx" )
                    .addStatement( "$T generator = new $T().forScope($T.class)",
                            generatorType, identityInfo.getGenerator(), identityInfo.getScope() )
                    .addStatement( "$T scopedGen = ctx.findObjectIdGenerator(generator)", generatorType )
                    .beginControlFlow( "if (null == scopedGen)" )
                    .addStatement( "scopedGen = generator.newForSerialization(ctx)" )
                    .addStatement( "ctx.addGenerator(scopedGen)" )
                    .endControlFlow()
                    .addStatement( "return new $T(scopedGen.generateId(bean), getSerializer())", returnType )
                    .build() );
        }

        return builder.build();
    }

    private void buildBeanPropertySerializerBody( TypeSpec.Builder builder, JClassType beanType, PropertyInfo property, JSerializerType
            serializerType ) throws UnableToCompleteException {
        String paramName = "bean";
        Accessor getterAccessor = property.getGetterAccessor().get().getAccessor( paramName );

        MethodSpec.Builder newSerializerMethodBuilder = MethodSpec.methodBuilder( "newSerializer" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .addStatement( "return $L", serializerType.getInstance() );
        if ( property.isAnyGetter() ) {
            newSerializerMethodBuilder.returns( MapJsonSerializer.class );
        } else {
            newSerializerMethodBuilder.returns( ParameterizedTypeName.get( ClassName.get( JsonSerializer.class ), DEFAULT_WILDCARD ) );
        }
        builder.addMethod( newSerializerMethodBuilder.build() );

        Optional<MethodSpec> paramMethod = generatePropertySerializerParameters( property, serializerType );
        if ( paramMethod.isPresent() ) {
            builder.addMethod( paramMethod.get() );
        }

        builder.addMethod( MethodSpec.methodBuilder( "getValue" )
                        .addModifiers( Modifier.PUBLIC )
                        .addAnnotation( Override.class )
                        .returns( typeName( true, property.getType() ) ) // the boxed type is specified so we can't return a primitive
                        .addParameter( typeName( beanType ), paramName )
                        .addParameter( JsonSerializationContext.class, "ctx" )
                        .addStatement( "return $L", getterAccessor.getAccessor() )
                        .build()
        );

        if ( getterAccessor.getAdditionalMethod().isPresent() ) {
            builder.addMethod( getterAccessor.getAdditionalMethod().get() );
        }
    }

    private Optional<MethodSpec> generatePropertySerializerParameters( PropertyInfo property, JSerializerType serializerType )
            throws UnableToCompleteException {

        if ( !property.getFormat().isPresent()
                && !property.getIgnoredProperties().isPresent()
                && !property.getIgnoreUnknown().isPresent()
                && !property.getIdentityInfo().isPresent()
                && !property.getTypeInfo().isPresent()
                && !property.getInclude().isPresent()
                && !property.isUnwrapped() ) {
            // none of the parameter are set so we don't generate the method
            return Optional.absent();
        }

        JClassType annotatedType = findFirstTypeToApplyPropertyAnnotation( serializerType );

        CodeBlock.Builder paramBuilder = CodeBlock.builder()
                .add( "return new $T()", JsonSerializerParameters.class )
                .indent()
                .indent();

        buildCommonPropertyParameters( paramBuilder, property );

        if ( property.getFormat().isPresent() ) {
            JsonFormat format = property.getFormat().get();
            if ( !Strings.isNullOrEmpty( format.timezone() ) && !JsonFormat.DEFAULT_TIMEZONE.equals( format.timezone() ) ) {
                java.util.TimeZone timeZoneJdk = java.util.TimeZone.getTimeZone( format.timezone() );
                // in java the offset is in milliseconds from timezone to GMT
                // in gwt the offset is in minutes from GMT to timezone
                // so we convert the milliseconds in minutes and invert the sign
                int timeZoneOffsetGwt = (timeZoneJdk.getRawOffset() / 1000 / 60) * -1;
                paramBuilder.add( "\n.setTimezone($T.createTimeZone($L))", TimeZone.class, timeZoneOffsetGwt );
            }
        }

        if ( property.getInclude().isPresent() ) {
            paramBuilder.add( "\n.setInclude($T.$L)", Include.class, property.getInclude().get().name() );
        }

        if ( property.getIdentityInfo().isPresent() ) {
            try {
                Optional<JSerializerType> identitySerializerType = getIdentitySerializerType( property.getIdentityInfo().get() );
                paramBuilder.add( "\n.setIdentityInfo($L)",
                        generateIdentifierSerializationInfo( annotatedType, property.getIdentityInfo().get(), identitySerializerType ) );
            } catch ( UnsupportedTypeException e ) {
                logger.log( Type.WARN, "Identity type is not supported. We ignore it." );
            }
        }

        if ( property.getTypeInfo().isPresent() ) {
            paramBuilder.add( "\n.setTypeInfo($L)", generateTypeInfo( property.getTypeInfo().get() ) );
        }

        if ( property.isUnwrapped() ) {
            paramBuilder.add( "\n.setUnwrapped(true)" );
        }

        paramBuilder.add( ";\n" )
                .unindent()
                .unindent();

        return Optional.of( MethodSpec.methodBuilder( "newParameters" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( JsonSerializerParameters.class )
                .addCode( paramBuilder.build() )
                .build() );
    }
}
