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
import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractIdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.bean.AnySetterDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.BackReferenceProperty;
import com.github.nmorel.gwtjackson.client.deser.bean.BeanPropertyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.HasDeserializerAndParameters;
import com.github.nmorel.gwtjackson.client.deser.bean.IdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.bean.Instance;
import com.github.nmorel.gwtjackson.client.deser.bean.InstanceBuilder;
import com.github.nmorel.gwtjackson.client.deser.bean.PropertyIdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.bean.SimpleStringMap;
import com.github.nmorel.gwtjackson.client.deser.bean.SubtypeDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.SubtypeDeserializer.BeanSubtypeDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.SubtypeDeserializer.DefaultSubtypeDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.TypeDeserializationInfo;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.rebind.bean.BeanIdentityInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanTypeInfo;
import com.github.nmorel.gwtjackson.rebind.exception.UnsupportedTypeException;
import com.github.nmorel.gwtjackson.rebind.property.FieldAccessor;
import com.github.nmorel.gwtjackson.rebind.property.FieldAccessor.Accessor;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.github.nmorel.gwtjackson.rebind.type.JDeserializerType;
import com.github.nmorel.gwtjackson.rebind.writer.JTypeName;
import com.github.nmorel.gwtjackson.rebind.writer.JsniCodeBlockBuilder;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JAbstractMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Function;
import com.google.gwt.thirdparty.guava.common.base.Joiner;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.Collections2;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.findFirstTypeToApplyPropertyAnnotation;
import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.getDefaultValueForType;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.DEFAULT_WILDCARD;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.parameterizedName;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.rawName;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.typeName;

/**
 * @author Nicolas Morel
 */
public class BeanJsonDeserializerCreator extends AbstractBeanJsonCreator {

    private static final String INSTANCE_BUILDER_VARIABLE_FORMAT = "property_%d";

    public static final String DELEGATION_PARAM_NAME = "delegation";

    private static final String INSTANCE_BUILDER_DESERIALIZER_PREFIX = "deserializer_";

    public BeanJsonDeserializerCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration, JacksonTypeOracle
            typeOracle, JClassType beanType ) throws UnableToCompleteException {
        super( logger, context, configuration, typeOracle, beanType );
    }

    @Override
    protected final boolean isSerializer() {
        return false;
    }

    @Override
    protected final void buildSpecific( TypeSpec.Builder typeBuilder ) throws UnableToCompleteException, UnsupportedTypeException {

        if ( beanInfo.getBuilder().isPresent() || beanInfo.getCreatorMethod().isPresent() ) {
            typeBuilder.addMethod( buildInitInstanceBuilderMethod() );
        }

        // no need to generate properties for non instantiable class
        if ( (beanInfo.getCreatorMethod().isPresent() && !beanInfo.isCreatorDelegation()) &&
                (!properties.isEmpty() || beanInfo
                        .getAnySetterPropertyInfo().isPresent()) ) {
            buildInitPropertiesMethods( typeBuilder );
        }

        if ( beanInfo.getIdentityInfo().isPresent() ) {
            try {
                typeBuilder.addMethod( buildInitIdentityInfoMethod( beanInfo.getIdentityInfo().get() ) );
            } catch ( UnsupportedTypeException e ) {
                logger.log( Type.WARN, "Identity type is not supported. We ignore it." );
            }
        }

        if ( beanInfo.getTypeInfo().isPresent() ) {
            typeBuilder.addMethod( buildInitTypeInfoMethod( beanInfo.getTypeInfo().get() ) );
        }

        ImmutableList<JClassType> subtypes = filterSubtypes();
        if ( !subtypes.isEmpty() ) {
            typeBuilder.addMethod( buildInitMapSubtypeClassToDeserializerMethod( subtypes ) );
        }

        if ( beanInfo.isIgnoreUnknown() ) {
            typeBuilder.addMethod( buildIsDefaultIgnoreUnknownMethod() );
        }
    }

    private MethodSpec buildInitInstanceBuilderMethod() throws UnableToCompleteException, UnsupportedTypeException {

        MethodSpec.Builder initInstanceBuilderMethodBuilder = MethodSpec.methodBuilder( "initInstanceBuilder" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( parameterizedName( InstanceBuilder.class, beanInfo.getType() ) );

        TypeName deserializersMapTypeName = ParameterizedTypeName.get( ClassName.get( SimpleStringMap.class ),
                ClassName.get( HasDeserializerAndParameters.class ) );

        if ( null != beanInfo.getCreatorParameters() && !beanInfo.getCreatorParameters().isEmpty() ) {

            initInstanceBuilderMethodBuilder
                    .addStatement( "final $T deserializers = $T.createObject().cast()", deserializersMapTypeName, SimpleStringMap.class );

            // for each constructor parameters, we initialize its deserializer.
            int index = 0;
            for ( Entry<String, JParameter> entry : beanInfo.getCreatorParameters().entrySet() ) {

                TypeName typeName = typeName( true, entry.getValue().getType() );
                TypeName deserializerTypeName = ParameterizedTypeName.get( ClassName.get( HasDeserializerAndParameters.class ), typeName,
                        ParameterizedTypeName.get( ClassName.get( JsonDeserializer.class ), typeName ) );

                TypeSpec.Builder deserializerBuilder = TypeSpec.anonymousClassBuilder( "" )
                        .superclass( deserializerTypeName );
                List<MethodSpec> commonMethods = buildCommonPropertyDeserializerMethods( properties.get( entry.getKey() ) );
                for ( MethodSpec method : commonMethods ) {
                    deserializerBuilder.addMethod( method );
                }

                String deserializerName = INSTANCE_BUILDER_DESERIALIZER_PREFIX + String
                        .format( INSTANCE_BUILDER_VARIABLE_FORMAT, index++ );
                initInstanceBuilderMethodBuilder
                        .addStatement( "final $T $L = $L", deserializerTypeName, deserializerName, deserializerBuilder.build() );
                initInstanceBuilderMethodBuilder.addStatement( "deserializers.put($S, $L)", entry.getKey(), deserializerName );
            }
        } else {
            initInstanceBuilderMethodBuilder.addStatement( "final $T deserializers = null", deserializersMapTypeName );
        }

        if ( beanInfo.getBuilder().isPresent() ) {
            // we create the deserializer for the builder class itself
            JDeserializerType builderType = getJsonDeserializerFromType( beanInfo.getBuilder().get() );
            initInstanceBuilderMethodBuilder.addStatement( "final $T builderDeserializer = $L",
                    JTypeName.parameterizedName( AbstractBeanJsonDeserializer.class, beanInfo.getBuilder().get() ),
                    builderType.getInstance() );
        }

        initInstanceBuilderMethodBuilder.addCode( "\n" );

        return initInstanceBuilderMethodBuilder
                .addStatement( "return $L", buildInstanceBuilderClass() )
                .build();
    }

    private TypeSpec buildInstanceBuilderClass() {
        MethodSpec createMethod = null;
        if ( !beanInfo.getBuilder().isPresent() ) {
            createMethod = buildInstanceBuilderCreateMethod();
        }

        MethodSpec.Builder newInstanceMethodBuilder = MethodSpec.methodBuilder( "newInstance" )
                .addModifiers( Modifier.PUBLIC )
                .addAnnotation( Override.class )
                .returns( parameterizedName( Instance.class, beanInfo.getType() ) )
                .addParameter( JsonReader.class, "reader" )
                .addParameter( JsonDeserializationContext.class, "ctx" )
                .addParameter( JsonDeserializerParameters.class, "params" )
                .addParameter( ParameterizedTypeName.get( Map.class, String.class, String.class ), "bufferedProperties" )
                .addParameter( ParameterizedTypeName.get( Map.class, String.class, Object.class ), "bufferedPropertiesValues" );

        if ( beanInfo.getBuilder().isPresent() ) {
            buildNewInstanceMethodForBuilder( newInstanceMethodBuilder );
        } else if ( beanInfo.isCreatorDefaultConstructor() ) {
            buildNewInstanceMethodForDefaultConstructor( newInstanceMethodBuilder, createMethod );
        } else if ( beanInfo.isCreatorDelegation() ) {
            buildNewInstanceMethodForConstructorOrFactoryMethodDelegation( newInstanceMethodBuilder, createMethod );
        } else {
            buildNewInstanceMethodForConstructorOrFactoryMethod( newInstanceMethodBuilder, createMethod );
        }

        MethodSpec.Builder deserializersGetter = MethodSpec.methodBuilder( "getParametersDeserializer" )
                .addModifiers( Modifier.PUBLIC )
                .addAnnotation( Override.class )
                .addStatement( "return deserializers" )
                .returns( ParameterizedTypeName.get( ClassName.get( SimpleStringMap.class ),
                        ClassName.get( HasDeserializerAndParameters.class ) ) );

        TypeSpec.Builder instanceBuilder = TypeSpec.anonymousClassBuilder( "" )
                .addSuperinterface( parameterizedName( InstanceBuilder.class, beanInfo.getType() ) )
                .addMethod( newInstanceMethodBuilder.build() )
                .addMethod( deserializersGetter.build() );

        if ( null != createMethod ) {
            instanceBuilder.addMethod( createMethod );
        }

        return instanceBuilder.build();
    }

    /**
     * Generate the instance builder class body for a builder.
     *
     * @param newInstanceMethodBuilder builder for the
     * {@link InstanceBuilder#newInstance(JsonReader, JsonDeserializationContext, JsonDeserializerParameters, Map, Map)}
     * method
     */
    private void buildNewInstanceMethodForBuilder( MethodSpec.Builder newInstanceMethodBuilder ) {
        newInstanceMethodBuilder.addStatement( "return new $T(builderDeserializer.deserializeInline(reader, ctx, params, null, null, null, bufferedProperties).build(), bufferedProperties)",
                parameterizedName( Instance.class, beanInfo.getType() ) );
    }

    /**
     * Generate the instance builder class body for a default constructor. We directly instantiate the bean at the builder creation and we
     * set the properties to it
     *
     * @param newInstanceMethodBuilder builder for the
     * {@link InstanceBuilder#newInstance(JsonReader, JsonDeserializationContext, JsonDeserializerParameters, Map, Map)}
     * method
     * @param createMethod the create method
     */
    private void buildNewInstanceMethodForDefaultConstructor( MethodSpec.Builder newInstanceMethodBuilder, MethodSpec createMethod ) {
        newInstanceMethodBuilder.addStatement( "return new $T($N(), bufferedProperties)",
                parameterizedName( Instance.class, beanInfo.getType() ), createMethod );
    }

    /**
     * Generate the instance builder class body for a constructor with parameters or factory method. We will declare all the fields and
     * instanciate the bean only on build() method when all properties have been deserialiazed
     *
     * @param newInstanceMethodBuilder builder for the
     * {@link InstanceBuilder#newInstance(JsonReader, JsonDeserializationContext, JsonDeserializerParameters, Map, Map)} method
     * @param createMethod the create method
     */
    private void buildNewInstanceMethodForConstructorOrFactoryMethod( MethodSpec.Builder newInstanceMethodBuilder,
                                                                      MethodSpec createMethod ) {
        // we don't use directly the property name to name our variable in case it contains invalid character
        ImmutableMap.Builder<String, String> propertyNameToVariableBuilder = ImmutableMap.builder();

        List<String> requiredProperties = new ArrayList<String>();
        int propertyIndex = 0;
        for ( String name : beanInfo.getCreatorParameters().keySet() ) {
            String variableName = String.format( INSTANCE_BUILDER_VARIABLE_FORMAT, propertyIndex++ );
            propertyNameToVariableBuilder.put( name, variableName );
            PropertyInfo propertyInfo = properties.get( name );

            newInstanceMethodBuilder.addCode( "$T $L = $L; // property '$L'\n",
                    typeName( propertyInfo.getType() ), variableName, getDefaultValueForType( propertyInfo.getType() ), name );

            if ( propertyInfo.isRequired() ) {
                requiredProperties.add( name );
            }
        }
        newInstanceMethodBuilder.addCode( "\n" );
        ImmutableMap<String, String> propertyNameToVariable = propertyNameToVariableBuilder.build();

        newInstanceMethodBuilder.addStatement( "int nbParamToFind = $L", beanInfo.getCreatorParameters().size() );

        if ( !requiredProperties.isEmpty() ) {
            CodeBlock code = CodeBlock.builder()
                    .add( Joiner.on( ", " ).join( Collections2.transform( requiredProperties, new Function<String, Object>() {
                        @Nullable
                        @Override
                        public Object apply( String s ) {
                            return "$S";
                        }
                    } ) ), requiredProperties.toArray() ).build();

            newInstanceMethodBuilder.addStatement( "$T requiredProperties = new $T($T.asList($L))",
                    ParameterizedTypeName.get( Set.class, String.class ),
                    ParameterizedTypeName.get( HashSet.class, String.class ),
                    Arrays.class, code );
        }

        newInstanceMethodBuilder.addCode( "\n" );

        newInstanceMethodBuilder.beginControlFlow( "if (null != bufferedPropertiesValues)" );
        newInstanceMethodBuilder.addStatement( "Object value" );
        for ( String name : beanInfo.getCreatorParameters().keySet() ) {
            String variableName = propertyNameToVariable.get( name );
            PropertyInfo propertyInfo = properties.get( name );

            newInstanceMethodBuilder.addCode( "\n" );
            newInstanceMethodBuilder.addStatement( "value = bufferedPropertiesValues.remove($S)", name );
            newInstanceMethodBuilder.beginControlFlow( "if (null != value)" );
            newInstanceMethodBuilder.addStatement( "$L = ($T) value", variableName, typeName( true, propertyInfo.getType() ) );
            newInstanceMethodBuilder.addStatement( "nbParamToFind--" );
            if ( propertyInfo.isRequired() ) {
                newInstanceMethodBuilder.addStatement( "requiredProperties.remove($S)", name );
            }
            newInstanceMethodBuilder.endControlFlow();
        }
        newInstanceMethodBuilder.endControlFlow();

        newInstanceMethodBuilder.addCode( "\n" );

        newInstanceMethodBuilder.beginControlFlow( "if (null != bufferedProperties)" );
        newInstanceMethodBuilder.addStatement( "String value" );
        for ( String name : beanInfo.getCreatorParameters().keySet() ) {
            String variableName = propertyNameToVariable.get( name );
            PropertyInfo propertyInfo = properties.get( name );

            newInstanceMethodBuilder.addCode( "\n" );
            newInstanceMethodBuilder.addStatement( "value = bufferedProperties.remove($S)", name );
            newInstanceMethodBuilder.beginControlFlow( "if (null != value)" );
            if ( null != propertyInfo.getType().isPrimitive() ) {
                newInstanceMethodBuilder.addStatement( "$L = ($T) $L.deserialize(ctx.newJsonReader(value), ctx)",
                        variableName, typeName( true, propertyInfo.getType() ), INSTANCE_BUILDER_DESERIALIZER_PREFIX + variableName );
            } else {
                newInstanceMethodBuilder.addStatement( "$L = $L.deserialize(ctx.newJsonReader(value), ctx)",
                        variableName, INSTANCE_BUILDER_DESERIALIZER_PREFIX + variableName );
            }
            newInstanceMethodBuilder.addStatement( "nbParamToFind--" );
            if ( propertyInfo.isRequired() ) {
                newInstanceMethodBuilder.addStatement( "requiredProperties.remove($S)", name );
            }
            newInstanceMethodBuilder.endControlFlow();
        }
        newInstanceMethodBuilder.endControlFlow();

        newInstanceMethodBuilder.addCode( "\n" );

        newInstanceMethodBuilder.addStatement( "String name" );
        newInstanceMethodBuilder.beginControlFlow( "while (nbParamToFind > 0 && $T.NAME == reader.peek())", JsonToken.class );

        newInstanceMethodBuilder.addStatement( "name = reader.nextName()" );
        newInstanceMethodBuilder.addCode( "\n" );

        for ( String name : beanInfo.getCreatorParameters().keySet() ) {
            String variableName = propertyNameToVariable.get( name );
            PropertyInfo propertyInfo = properties.get( name );

            newInstanceMethodBuilder.beginControlFlow( "if ($S.equals(name))", name );
            newInstanceMethodBuilder.addStatement( "$L = $L.deserialize(reader, ctx)",
                    variableName, INSTANCE_BUILDER_DESERIALIZER_PREFIX + variableName );
            newInstanceMethodBuilder.addStatement( "nbParamToFind--" );
            if ( propertyInfo.isRequired() ) {
                newInstanceMethodBuilder.addStatement( "requiredProperties.remove($S)", name );
            }
            newInstanceMethodBuilder.addStatement( "continue" );
            newInstanceMethodBuilder.endControlFlow();

            newInstanceMethodBuilder.addCode( "\n" );
        }

        newInstanceMethodBuilder.beginControlFlow( "if (null == bufferedProperties)" );
        newInstanceMethodBuilder.addStatement( "bufferedProperties = new $T()",
                ParameterizedTypeName.get( HashMap.class, String.class, String.class ) );
        newInstanceMethodBuilder.endControlFlow();
        newInstanceMethodBuilder.addStatement( "bufferedProperties.put(name, reader.nextValue())" );

        newInstanceMethodBuilder.endControlFlow();

        newInstanceMethodBuilder.addCode( "\n" );

        if ( !requiredProperties.isEmpty() ) {
            newInstanceMethodBuilder.beginControlFlow( "if (!requiredProperties.isEmpty())" );
            newInstanceMethodBuilder
                    .addStatement( "throw ctx.traceError(\"Required properties are missing : \" + requiredProperties, reader)" );
            newInstanceMethodBuilder.endControlFlow();
            newInstanceMethodBuilder.addCode( "\n" );
        }

        newInstanceMethodBuilder.addStatement( "return new $T($N($L), bufferedProperties)",
                parameterizedName( Instance.class, beanInfo.getType() ),
                createMethod,
                Joiner.on( ", " ).join( propertyNameToVariable.values() ) );
    }

    /**
     * Generate the instance builder class body for a constructor or factory method with delegation.
     *
     * @param newInstanceMethodBuilder builder for the
     * {@link InstanceBuilder#newInstance(JsonReader, JsonDeserializationContext, JsonDeserializerParameters, Map, Map)}
     * method
     * @param createMethod the create method
     */
    private void buildNewInstanceMethodForConstructorOrFactoryMethodDelegation( MethodSpec.Builder newInstanceMethodBuilder,
                                                                                MethodSpec createMethod ) {
        String param = String.format( "%s%s.deserialize(reader, ctx)", INSTANCE_BUILDER_DESERIALIZER_PREFIX, String
                .format( INSTANCE_BUILDER_VARIABLE_FORMAT, 0 ) );

        newInstanceMethodBuilder.addStatement( "return new $T($N($L), bufferedProperties)",
                parameterizedName( Instance.class, beanInfo.getType() ), createMethod, param );
    }

    private MethodSpec buildInstanceBuilderCreateMethod() {
        JAbstractMethod method = beanInfo.getCreatorMethod().get();

        MethodSpec.Builder builder = MethodSpec.methodBuilder( "create" )
                .addModifiers( Modifier.PRIVATE )
                .returns( typeName( beanInfo.getType() ) );

        StringBuilder parametersNameBuilder = new StringBuilder();
        int index = 0;
        for ( Map.Entry<String, JParameter> parameterEntry : beanInfo.getCreatorParameters().entrySet() ) {
            if ( index > 0 ) {
                parametersNameBuilder.append( ", " );
            }
            PropertyInfo property = properties.get( parameterEntry.getKey() );

            String variableName = String.format( INSTANCE_BUILDER_VARIABLE_FORMAT, index++ );
            builder.addParameter( typeName( property.getType() ), variableName );
            parametersNameBuilder.append( variableName );
        }
        String parametersName = parametersNameBuilder.toString();

        if ( method.isPrivate() || (!method.isPublic() && !mapperInfo.isSamePackage()) ) {
            // private method, we use jsni
            builder.addModifiers( Modifier.NATIVE );
            builder.addCode( JsniCodeBlockBuilder.builder()
                    .addStatement( "return $L($L)", method.getJsniSignature(), parametersName )
                    .build() );
        } else {
            if ( null != method.isConstructor() ) {
                builder.addStatement( "return new $T($L)", typeName( beanInfo.getType() ), parametersName );
            } else {
                builder.addStatement( "return $T.$L($L)", typeName( beanInfo.getType() ), method.getName(), parametersName );
            }
        }

        return builder.build();
    }

    private void buildInitPropertiesMethods( TypeSpec.Builder typeBuilder ) throws UnableToCompleteException {

        List<PropertyInfo> ignoredProperties = new ArrayList<PropertyInfo>();
        List<PropertyInfo> requiredProperties = new ArrayList<PropertyInfo>();
        Map<PropertyInfo, JDeserializerType> deserializerProperties = new LinkedHashMap<PropertyInfo, JDeserializerType>();
        List<PropertyInfo> backReferenceProperties = new ArrayList<PropertyInfo>();

        for ( PropertyInfo property : properties.values() ) {
            if ( null != beanInfo.getCreatorParameters() && beanInfo.getCreatorParameters().containsKey( property.getPropertyName() ) ) {
                // properties used in constructor are deserialized inside instance builder
                continue;
            }

            if ( property.isIgnored() ) {
                ignoredProperties.add( property );
                continue;
            }

            if ( !property.getSetterAccessor().isPresent() ) {
                // there is no setter visible
                continue;
            }

            if ( !property.getBackReference().isPresent() ) {
                try {
                    JDeserializerType deserializerType = getJsonDeserializerFromType( property.getType() );
                    deserializerProperties.put( property, deserializerType );
                    if ( property.isRequired() ) {
                        requiredProperties.add( property );
                    }
                } catch ( UnsupportedTypeException e ) {
                    logger.log( Type.WARN, "Property '" + property.getPropertyName() + "' is ignored" );
                    ignoredProperties.add( property );
                }
            } else {
                backReferenceProperties.add( property );
            }
        }

        if ( !deserializerProperties.isEmpty() ) {
            typeBuilder.addMethod( buildInitDeserializersMethod( deserializerProperties ) );
        }

        if ( !backReferenceProperties.isEmpty() ) {
            typeBuilder.addMethod( buildInitBackReferenceDeserializersMethod( backReferenceProperties ) );
        }

        if ( !ignoredProperties.isEmpty() ) {
            typeBuilder.addMethod( buildInitIgnoredPropertiesMethod( ignoredProperties ) );
        }

        if ( !requiredProperties.isEmpty() ) {
            typeBuilder.addMethod( buildInitRequiredPropertiesMethod( requiredProperties ) );
        }

        if ( beanInfo.getAnySetterPropertyInfo().isPresent() ) {
            Optional<MethodSpec> method = buildInitAnySetterDeserializerMethod( beanInfo.getAnySetterPropertyInfo().get() );
            if ( method.isPresent() ) {
                typeBuilder.addMethod( method.get() );
            }
        }
    }

    private Optional<MethodSpec> buildInitAnySetterDeserializerMethod( PropertyInfo anySetterPropertyInfo )
            throws UnableToCompleteException {

        FieldAccessor fieldAccessor = anySetterPropertyInfo.getSetterAccessor().get();
        JType type = fieldAccessor.getMethod().get().getParameterTypes()[1];

        JDeserializerType deserializerType;
        try {
            deserializerType = getJsonDeserializerFromType( type );
        } catch ( UnsupportedTypeException e ) {
            logger.log( Type.WARN, "Method '" + fieldAccessor.getMethod().get()
                    .getName() + "' annotated with @JsonAnySetter has an unsupported type" );
            return Optional.absent();
        }

        return Optional.of( MethodSpec.methodBuilder( "initAnySetterDeserializer" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( ParameterizedTypeName.get(
                        ClassName.get( AnySetterDeserializer.class ), typeName( beanInfo.getType() ), DEFAULT_WILDCARD ) )
                .addStatement( "return $L", buildDeserializer( anySetterPropertyInfo, type, deserializerType ) )
                .build() );
    }

    private MethodSpec buildInitDeserializersMethod( Map<PropertyInfo, JDeserializerType> properties ) throws UnableToCompleteException {

        TypeName resultType = ParameterizedTypeName.get( ClassName.get( SimpleStringMap.class ),
                ParameterizedTypeName.get( ClassName.get( BeanPropertyDeserializer.class ),
                        typeName( beanInfo.getType() ), DEFAULT_WILDCARD ) );

        MethodSpec.Builder builder = MethodSpec.methodBuilder( "initDeserializers" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( resultType )
                .addStatement( "$T map = $T.createObject().cast()", resultType, SimpleStringMap.class );

        for ( Entry<PropertyInfo, JDeserializerType> entry : properties.entrySet() ) {
            PropertyInfo property = entry.getKey();
            JDeserializerType deserializerType = entry.getValue();

            builder.addStatement( "map.put($S, $L)",
                    property.getPropertyName(), buildDeserializer( property, property.getType(), deserializerType ) );
        }

        builder.addStatement( "return map" );
        return builder.build();
    }

    private TypeSpec buildDeserializer( PropertyInfo property, JType propertyType, JDeserializerType deserializerType )
            throws UnableToCompleteException {
        final String paramValue = "value";
        final String paramBean = "bean";
        final String paramProperty = "propertyName";

        final Accessor accessor;
        final Class superclass;
        if ( property.isAnySetter() ) {
            accessor = property.getSetterAccessor().get().getAccessor( paramBean, paramProperty, paramValue );
            superclass = AnySetterDeserializer.class;
        } else {
            accessor = property.getSetterAccessor().get().getAccessor( paramBean, paramValue );
            superclass = BeanPropertyDeserializer.class;
        }

        TypeSpec.Builder builder = TypeSpec.anonymousClassBuilder( "" )
                .superclass( ParameterizedTypeName
                        .get( ClassName.get( superclass ), typeName( true, beanInfo.getType() ), rawName( true, propertyType ) ) );

        List<MethodSpec> commonMethods = buildCommonPropertyDeserializerMethods( property, deserializerType );
        for ( MethodSpec commonMethod : commonMethods ) {
            builder.addMethod( commonMethod );
        }

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder( "setValue" )
                .addModifiers( Modifier.PUBLIC )
                .addAnnotation( Override.class )
                .addParameter( typeName( beanInfo.getType() ), paramBean );
        if ( property.isAnySetter() ) {
            methodBuilder.addParameter( String.class, paramProperty );
        }
        methodBuilder.addParameter( rawName( true, propertyType ), paramValue )
                .addParameter( JsonDeserializationContext.class, "ctx" )
                .addStatement( "$L", accessor.getAccessor() );

        if ( property.getManagedReference().isPresent() ) {
            methodBuilder.addStatement( "getDeserializer().setBackReference($S, $L, $L, ctx)",
                    property.getManagedReference().get(), paramBean, paramValue );
        }

        builder.addMethod( methodBuilder.build() );

        if ( accessor.getAdditionalMethod().isPresent() ) {
            builder.addMethod( accessor.getAdditionalMethod().get() );
        }

        return builder.build();
    }

    private List<MethodSpec> buildCommonPropertyDeserializerMethods( PropertyInfo property )
            throws UnableToCompleteException, UnsupportedTypeException {
        return buildCommonPropertyDeserializerMethods( property, getJsonDeserializerFromType( property.getType() ) );
    }

    private List<MethodSpec> buildCommonPropertyDeserializerMethods( PropertyInfo property, JDeserializerType deserializerType )
            throws UnableToCompleteException {
        List<MethodSpec> result = new ArrayList<MethodSpec>();

        result.add( MethodSpec.methodBuilder( "newDeserializer" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( ParameterizedTypeName.get( ClassName.get( JsonDeserializer.class ), DEFAULT_WILDCARD ) )
                .addStatement( "return $L", deserializerType.getInstance() )
                .build() );

        Optional<MethodSpec> paramMethod = buildPropertyDeserializerParameters( property, deserializerType );
        if ( paramMethod.isPresent() ) {
            result.add( paramMethod.get() );
        }

        return result;
    }

    private Optional<MethodSpec> buildPropertyDeserializerParameters( PropertyInfo property, JDeserializerType deserializerType )
            throws UnableToCompleteException {

        if ( !property.getFormat().isPresent()
                && !property.getIgnoredProperties().isPresent()
                && !property.getIgnoreUnknown().isPresent()
                && !property.getIdentityInfo().isPresent()
                && !property.getTypeInfo().isPresent() ) {
            // none of the parameter are set so we don't generate the method
            return Optional.absent();
        }

        JClassType annotatedType = findFirstTypeToApplyPropertyAnnotation( deserializerType );

        CodeBlock.Builder paramBuilder = CodeBlock.builder()
                .add( "return new $T()", JsonDeserializerParameters.class )
                .indent()
                .indent();

        buildCommonPropertyParameters( paramBuilder, property );

        if ( property.getIgnoreUnknown().isPresent() ) {
            paramBuilder.add( "\n.setIgnoreUnknown($L)", Boolean.toString( property.getIgnoreUnknown().get() ) );
        }

        if ( property.getIdentityInfo().isPresent() ) {
            try {
                BeanIdentityInfo identityInfo = property.getIdentityInfo().get();
                if ( identityInfo.isIdABeanProperty() ) {
                    paramBuilder.add( "\n.setIdentityInfo($L)", buildPropertyIdentifierDeserializationInfo( annotatedType, identityInfo ) );
                } else {
                    paramBuilder.add( "\n.setIdentityInfo($L)", buildIdentifierDeserializationInfo( annotatedType, identityInfo,
                            getJsonDeserializerFromType( identityInfo.getType().get() ) ) );
                }
            } catch ( UnsupportedTypeException e ) {
                logger.log( Type.WARN, "Identity type is not supported. We ignore it." );
            }
        }

        if ( property.getTypeInfo().isPresent() ) {
            paramBuilder.add( "\n.setTypeInfo($L)", generateTypeInfo( property.getTypeInfo().get() ) );
        }

        paramBuilder.add( ";\n" )
                .unindent()
                .unindent();

        return Optional.of( MethodSpec.methodBuilder( "newParameters" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .addCode( paramBuilder.build() )
                .returns( JsonDeserializerParameters.class )
                .build() );
    }

    private MethodSpec buildInitBackReferenceDeserializersMethod( List<PropertyInfo> properties )
            throws UnableToCompleteException {
        final String paramBean = "bean";
        final String paramReference = "reference";

        TypeName resultType = ParameterizedTypeName.get( ClassName.get( SimpleStringMap.class ),
                ParameterizedTypeName.get( ClassName.get( BackReferenceProperty.class ),
                        typeName( beanInfo.getType() ), DEFAULT_WILDCARD ) );

        MethodSpec.Builder builder = MethodSpec.methodBuilder( "initBackReferenceDeserializers" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( resultType )
                .addStatement( "$T map = $T.createObject().cast()", resultType, SimpleStringMap.class );

        for ( PropertyInfo property : properties ) {

            Accessor accessor = property.getSetterAccessor().get().getAccessor( paramBean, paramReference );

            TypeSpec.Builder anonymBuilder = TypeSpec.anonymousClassBuilder( "" )
                    .superclass( parameterizedName( BackReferenceProperty.class, beanInfo.getType(), property.getType() ) )
                    .addMethod( MethodSpec.methodBuilder( "setBackReference" )
                            .addModifiers( Modifier.PUBLIC )
                            .addAnnotation( Override.class )
                            .addParameter( typeName( beanInfo.getType() ), paramBean )
                            .addParameter( typeName( property.getType() ), paramReference )
                            .addParameter( JsonDeserializationContext.class, "ctx" )
                            .addStatement( "$L", accessor.getAccessor() )
                            .build()
                    );

            if ( accessor.getAdditionalMethod().isPresent() ) {
                anonymBuilder.addMethod( accessor.getAdditionalMethod().get() );
            }

            builder.addStatement( "map.put($S, $L)", property.getBackReference().get(), anonymBuilder.build() );
        }

        builder.addStatement( "return map" );
        return builder.build();
    }

    private MethodSpec buildInitIgnoredPropertiesMethod( List<PropertyInfo> properties ) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder( "initIgnoredProperties" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( ParameterizedTypeName.get( Set.class, String.class ) )
                .addStatement( "$T col = new $T($L)",
                        ParameterizedTypeName.get( HashSet.class, String.class ),
                        ParameterizedTypeName.get( HashSet.class, String.class ),
                        properties.size()
                );
        for ( PropertyInfo property : properties ) {
            builder.addStatement( "col.add($S)", property.getPropertyName() );
        }
        builder.addStatement( "return col" );
        return builder.build();
    }

    private MethodSpec buildInitRequiredPropertiesMethod( List<PropertyInfo> properties ) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder( "initRequiredProperties" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( ParameterizedTypeName.get( Set.class, String.class ) )
                .addStatement( "$T col = new $T($L)",
                        ParameterizedTypeName.get( HashSet.class, String.class ),
                        ParameterizedTypeName.get( HashSet.class, String.class ),
                        properties.size()
                );
        for ( PropertyInfo property : properties ) {
            builder.addStatement( "col.add($S)", property.getPropertyName() );
        }
        builder.addStatement( "return col" );
        return builder.build();
    }

    private MethodSpec buildInitIdentityInfoMethod( BeanIdentityInfo identityInfo )
            throws UnableToCompleteException, UnsupportedTypeException {

        MethodSpec.Builder builder = MethodSpec.methodBuilder( "initIdentityInfo" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( parameterizedName( IdentityDeserializationInfo.class, beanInfo.getType() ) );

        if ( identityInfo.isIdABeanProperty() ) {
            builder.addStatement( "return $L", buildPropertyIdentifierDeserializationInfo( beanInfo.getType(), identityInfo ) );
        } else {
            builder.addStatement( "return $L",
                    buildIdentifierDeserializationInfo( beanInfo.getType(), identityInfo,
                            getJsonDeserializerFromType( identityInfo.getType().get() ) ) );
        }

        return builder.build();
    }

    private CodeBlock buildPropertyIdentifierDeserializationInfo( JClassType type, BeanIdentityInfo identityInfo ) {
        return CodeBlock.builder().
                add( "new $T($S, $T.class, $T.class)", parameterizedName( PropertyIdentityDeserializationInfo.class, type ),
                        identityInfo.getPropertyName(), identityInfo.getGenerator(), identityInfo.getScope() )
                .build();
    }

    private TypeSpec buildIdentifierDeserializationInfo( JClassType type, BeanIdentityInfo identityInfo,
                                                         JDeserializerType deserializerType ) {
        return TypeSpec.anonymousClassBuilder( "$S, $T.class, $T.class",
                identityInfo.getPropertyName(), identityInfo.getGenerator(), identityInfo.getScope() )
                .superclass( parameterizedName( AbstractIdentityDeserializationInfo.class, type, identityInfo.getType().get() ) )
                .addMethod( MethodSpec.methodBuilder( "newDeserializer" )
                        .addModifiers( Modifier.PROTECTED )
                        .addAnnotation( Override.class )
                        .returns( ParameterizedTypeName.get( ClassName.get( JsonDeserializer.class ), DEFAULT_WILDCARD ) )
                        .addStatement( "return $L", deserializerType.getInstance() )
                        .build()
                ).build();
    }

    private MethodSpec buildInitTypeInfoMethod( BeanTypeInfo beanTypeInfo ) throws UnableToCompleteException {
        return MethodSpec.methodBuilder( "initTypeInfo" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( parameterizedName( TypeDeserializationInfo.class, beanInfo.getType() ) )
                .addStatement( "return $L", generateTypeInfo( beanTypeInfo ) )
                .build();
    }

    private MethodSpec buildInitMapSubtypeClassToDeserializerMethod( ImmutableList<JClassType> subtypes )
            throws UnableToCompleteException {

        Class[] mapTypes = new Class[]{Class.class, SubtypeDeserializer.class};
        TypeName resultType = ParameterizedTypeName.get( Map.class, mapTypes );

        MethodSpec.Builder builder = MethodSpec.methodBuilder( "initMapSubtypeClassToDeserializer" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( resultType )
                .addStatement( "$T map = new $T($L)",
                        resultType, ParameterizedTypeName.get( IdentityHashMap.class, mapTypes ), subtypes.size() );

        for ( JClassType subtype : subtypes ) {

            JDeserializerType deserializerType;
            try {
                deserializerType = getJsonDeserializerFromType( subtype, true );
            } catch ( UnsupportedTypeException e ) {
                logger.log( Type.WARN, "Subtype '" + subtype.getQualifiedSourceName() + "' is not supported. We ignore it." );
                continue;
            }

            Class subtypeClass;
            TypeName deserializerClass;
            if ( configuration.getDeserializer( subtype ).isPresent() || null != subtype.isEnum() ) {
                subtypeClass = DefaultSubtypeDeserializer.class;
                deserializerClass = ParameterizedTypeName.get( ClassName.get( JsonDeserializer.class ), DEFAULT_WILDCARD );
            } else {
                subtypeClass = BeanSubtypeDeserializer.class;
                deserializerClass = ParameterizedTypeName.get( ClassName.get( AbstractBeanJsonDeserializer.class ), DEFAULT_WILDCARD );
            }

            TypeSpec subtypeType = TypeSpec.anonymousClassBuilder( "" )
                    .superclass( subtypeClass )
                    .addMethod( MethodSpec.methodBuilder( "newDeserializer" )
                            .addModifiers( Modifier.PROTECTED )
                            .addAnnotation( Override.class )
                            .returns( deserializerClass )
                            .addStatement( "return $L", deserializerType.getInstance() )
                            .build()
                    ).build();

            builder.addStatement( "map.put($T.class, $L)", rawName( subtype ), subtypeType );
        }

        builder.addStatement( "return map" );
        return builder.build();
    }

    private MethodSpec buildIsDefaultIgnoreUnknownMethod() {
        return MethodSpec.methodBuilder( "isDefaultIgnoreUnknown" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( boolean.class )
                .addStatement( "return true" )
                .build();
    }

}
