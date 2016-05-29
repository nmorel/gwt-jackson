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
import java.io.PrintWriter;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.github.nmorel.gwtjackson.client.Deserializer;
import com.github.nmorel.gwtjackson.client.Serializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractDelegationBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractObjectBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractSerializableBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.TypeDeserializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractValueBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.TypeSerializationInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanTypeInfo;
import com.github.nmorel.gwtjackson.rebind.exception.UnsupportedTypeException;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.base.Strings;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.isObject;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.isSerializable;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.parameterizedName;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.rawName;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.typeVariableName;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractBeanJsonCreator extends AbstractCreator {

    protected final BeanJsonMapperInfo mapperInfo;

    protected final BeanInfo beanInfo;

    protected final ImmutableMap<String, PropertyInfo> properties;

    public AbstractBeanJsonCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration, JacksonTypeOracle
            typeOracle, JClassType beanType ) throws UnableToCompleteException {
        super( logger, context, configuration, typeOracle );
        this.mapperInfo = getMapperInfo( beanType );
        this.beanInfo = mapperInfo.getBeanInfo();
        this.properties = mapperInfo.getProperties();
    }

    @Override
    protected final Optional<BeanJsonMapperInfo> getMapperInfo() {
        return Optional.of( mapperInfo );
    }

    /**
     * Creates an implementation of {@link AbstractBeanJsonSerializer} for the type given in
     * parameter
     *
     * @return the information about the created class
     */
    public final BeanJsonMapperInfo create() throws UnableToCompleteException, UnsupportedTypeException {

        final String simpleClassName = isSerializer() ? mapperInfo
                .getSimpleSerializerClassName() : mapperInfo.getSimpleDeserializerClassName();

        PrintWriter printWriter = getPrintWriter( mapperInfo.getPackageName(), simpleClassName );
        // the class already exists, no need to continue
        if ( printWriter == null ) {
            return mapperInfo;
        }

        try {
            TypeSpec type = buildClass( simpleClassName );
            write( mapperInfo.getPackageName(), type, printWriter );
        } finally {
            printWriter.close();
        }

        return mapperInfo;
    }

    /**
     * @return true if we are creating a serializer, false otherwise
     */
    protected abstract boolean isSerializer();

    /**
     * Build the serializer/deserializer class.
     *
     * @param simpleClassName the name of the class
     *
     * @return the {@link TypeSpec}
     * @throws UnableToCompleteException if an uncoverable exception occured
     * @throws UnsupportedTypeException if the type is not supported
     */
    private TypeSpec buildClass( String simpleClassName ) throws UnableToCompleteException, UnsupportedTypeException {

        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder( simpleClassName )
                .addModifiers( Modifier.PUBLIC, Modifier.FINAL );

        // We add the superclass.
        if ( isSerializer() ) {
            Class clazz;
            if ( beanInfo.getValuePropertyInfo().isPresent() ) {
                clazz = AbstractValueBeanJsonSerializer.class;
            } else {
                clazz = AbstractBeanJsonSerializer.class;
            }
            typeBuilder.superclass( parameterizedName( clazz, beanInfo.getType() ) );
        } else {
            if ( isObject( beanInfo.getType() ) ) {
                typeBuilder.superclass( ClassName.get( AbstractObjectBeanJsonDeserializer.class ) );
            } else if ( isSerializable( beanInfo.getType() ) ) {
                typeBuilder.superclass( ClassName.get( AbstractSerializableBeanJsonDeserializer.class ) );
            } else if ( beanInfo.isCreatorDelegation() ) {
                typeBuilder.superclass( parameterizedName( AbstractDelegationBeanJsonDeserializer.class, beanInfo.getType() ) );
            } else {
                typeBuilder.superclass( parameterizedName( AbstractBeanJsonDeserializer.class, beanInfo.getType() ) );
            }
        }

        // If the type is generic, the mapper must have the same parameters.
        if ( null != beanInfo.getType().isGenericType() ) {
            for ( JTypeParameter typeParameter : beanInfo.getType().isGenericType().getTypeParameters() ) {
                typeBuilder.addTypeVariable( typeVariableName( typeParameter ) );
            }
        }

        buildConstructor( typeBuilder );
        typeBuilder.addMethod( buildClassGetterMethod() );

        buildSpecific( typeBuilder );

        return typeBuilder.build();
    }

    /**
     * Build the constructor and the final fields.
     *
     * @param typeBuilder the type builder
     */
    private void buildConstructor( TypeSpec.Builder typeBuilder ) {
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder().addModifiers( Modifier.PUBLIC );

        if ( !beanInfo.getParameterizedTypes().isEmpty() ) {
            Class mapperClass;
            String mapperNameFormat;
            if ( isSerializer() ) {
                mapperClass = Serializer.class;
                mapperNameFormat = TYPE_PARAMETER_SERIALIZER_FIELD_NAME;
            } else {
                mapperClass = Deserializer.class;
                mapperNameFormat = TYPE_PARAMETER_DESERIALIZER_FIELD_NAME;
            }

            for ( int i = 0; i < beanInfo.getParameterizedTypes().size(); i++ ) {
                JClassType argType = beanInfo.getParameterizedTypes().get( i );
                String mapperName = String.format( mapperNameFormat, i );
                TypeName mapperType = parameterizedName( mapperClass, argType );

                FieldSpec field = FieldSpec.builder( mapperType, mapperName, Modifier.PRIVATE, Modifier.FINAL ).build();
                typeBuilder.addField( field );

                ParameterSpec parameter = ParameterSpec.builder( mapperType, mapperName ).build();
                constructorBuilder.addParameter( parameter );
                constructorBuilder.addStatement( "this.$N = $N", field, parameter );
            }
        }

        typeBuilder.addMethod( constructorBuilder.build() );
    }

    /**
     * Build the method that returns the class of the mapped type.
     *
     * @return the method built
     */
    private MethodSpec buildClassGetterMethod() {
        return MethodSpec.methodBuilder( isSerializer() ? "getSerializedType" : "getDeserializedType" )
                .addModifiers( Modifier.PUBLIC )
                .addAnnotation( Override.class )
                .returns( Class.class )
                .addStatement( "return $T.class", rawName( beanInfo.getType() ) )
                .build();
    }

    /**
     * Method implemented by childs to add specific methods to the builder.
     *
     * @param typeBuilder the type builder
     */
    protected abstract void buildSpecific( TypeSpec.Builder typeBuilder ) throws UnableToCompleteException, UnsupportedTypeException;

    /**
     * Build the code to initialize a {@link TypeSerializationInfo} or {@link TypeDeserializationInfo}.
     *
     * @param typeInfo the type information obtained through the {@link JsonTypeInfo} annotation
     *
     * @return the code built
     */
    protected final CodeBlock generateTypeInfo( BeanTypeInfo typeInfo ) {

        Class type;
        ImmutableMap<JClassType, String> mapTypeToMetadata;
        if ( isSerializer() ) {
            type = TypeSerializationInfo.class;
            mapTypeToMetadata = typeInfo.getMapTypeToSerializationMetadata();
        } else {
            type = TypeDeserializationInfo.class;
            mapTypeToMetadata = typeInfo.getMapTypeToDeserializationMetadata();
        }

        CodeBlock.Builder builder = CodeBlock.builder()
                .add( "new $T($T.$L, $S)", type, As.class, typeInfo.getInclude(), typeInfo.getPropertyName() )
                .indent()
                .indent();

        for ( Entry<JClassType, String> entry : mapTypeToMetadata.entrySet() ) {
            builder.add( "\n.addTypeInfo($T.class, $S)", rawName( entry.getKey() ), entry.getValue() );
        }

        return builder.unindent().unindent().build();
    }

    /**
     * Add the common property parameters to the code builder.
     *
     * @param paramBuilder the code builder
     * @param property the information about the property
     */
    protected final void buildCommonPropertyParameters( CodeBlock.Builder paramBuilder, PropertyInfo property ) {
        if ( property.getFormat().isPresent() ) {
            JsonFormat format = property.getFormat().get();

            if ( !Strings.isNullOrEmpty( format.pattern() ) ) {
                paramBuilder.add( "\n.setPattern($S)", format.pattern() );
            }

            paramBuilder.add( "\n.setShape($T.$L)", Shape.class, format.shape().name() );

            if ( !Strings.isNullOrEmpty( format.locale() ) && !JsonFormat.DEFAULT_LOCALE.equals( format.locale() ) ) {
                logger.log( Type.WARN, "JsonFormat.locale is not supported by default" );
                paramBuilder.add( "\n.setLocale($S)", format.locale() );
            }
        }

        if ( property.getIgnoredProperties().isPresent() ) {
            for ( String ignoredProperty : property.getIgnoredProperties().get() ) {
                paramBuilder.add( "\n.addIgnoredProperty($S)", ignoredProperty );
            }
        }
    }

    /**
     * @return the filtered subtypes of the mapped type
     */
    protected final ImmutableList<JClassType> filterSubtypes() {
        if ( isSerializer() ) {
            return CreatorUtils.filterSubtypesForSerialization( logger, configuration, beanInfo.getType() );
        } else {
            return CreatorUtils.filterSubtypesForDeserialization( logger, configuration, beanInfo.getType() );
        }
    }
}
