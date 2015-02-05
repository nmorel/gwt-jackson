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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer.ArrayCreator;
import com.github.nmorel.gwtjackson.client.deser.array.dd.Array2dJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.dd.Array2dJsonDeserializer.Array2dCreator;
import com.github.nmorel.gwtjackson.client.deser.map.key.EnumKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.ArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.dd.Array2dJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.EnumKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.rebind.RebindConfiguration.MapperInstance;
import com.github.nmorel.gwtjackson.rebind.RebindConfiguration.MapperType;
import com.github.nmorel.gwtjackson.rebind.bean.BeanInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanProcessor;
import com.github.nmorel.gwtjackson.rebind.exception.UnsupportedTypeException;
import com.github.nmorel.gwtjackson.rebind.property.PropertiesContainer;
import com.github.nmorel.gwtjackson.rebind.property.PropertyProcessor;
import com.github.nmorel.gwtjackson.rebind.type.JDeserializerType;
import com.github.nmorel.gwtjackson.rebind.type.JMapperType;
import com.github.nmorel.gwtjackson.rebind.type.JSerializerType;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JEnumType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.user.rebind.AbstractSourceCreator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.parameterizedName;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.rawName;
import static com.github.nmorel.gwtjackson.rebind.writer.JTypeName.typeName;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractCreator extends AbstractSourceCreator {

    protected static final String TYPE_PARAMETER_DESERIALIZER_FIELD_NAME = "deserializer%d";

    protected static final String TYPE_PARAMETER_SERIALIZER_FIELD_NAME = "serializer%d";

    protected final TreeLogger logger;

    protected final GeneratorContext context;

    protected final RebindConfiguration configuration;

    protected final JacksonTypeOracle typeOracle;

    protected AbstractCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration, JacksonTypeOracle
            typeOracle ) {
        this.logger = logger;
        this.context = context;
        this.configuration = configuration;
        this.typeOracle = typeOracle;
    }

    /**
     * Creates the {@link PrintWriter} to write the class.
     *
     * @param packageName the package
     * @param className the name of the class
     *
     * @return the {@link PrintWriter} or null if the class already exists.
     */
    protected final PrintWriter getPrintWriter( String packageName, String className ) {
        return context.tryCreate( logger, packageName, className );
    }

    /**
     * Writes the given type to the {@link PrintWriter}.
     *
     * @param packageName the package for the type
     * @param type the type
     * @param printWriter the writer
     *
     * @throws UnableToCompleteException if an exception is thrown by the writer
     */
    protected final void write( String packageName, TypeSpec type, PrintWriter printWriter ) throws UnableToCompleteException {
        try {
            JavaFile.builder( packageName, type )
                    .build()
                    .writeTo( printWriter );
            context.commit( logger, printWriter );
        } catch ( IOException e ) {
            logger.log( TreeLogger.Type.ERROR, "Error writing the file " + packageName + "." + type.name, e );
            throw new UnableToCompleteException();
        }
    }

    /**
     * Returns the mapper information for the given type. The result is cached.
     *
     * @param beanType the type
     *
     * @return the mapper information
     * @throws UnableToCompleteException if an exception occured while processing the type
     */
    protected final BeanJsonMapperInfo getMapperInfo( JClassType beanType ) throws UnableToCompleteException {
        BeanJsonMapperInfo mapperInfo = typeOracle.getBeanJsonMapperInfo( beanType );
        if ( null != mapperInfo ) {
            return mapperInfo;
        }

        boolean samePackage = true;
        String packageName = beanType.getPackage().getName();
        // We can't create classes in the java package so we prefix it.
        if ( packageName.startsWith( "java." ) ) {
            packageName = "gwtjackson." + packageName;
            samePackage = false;
        }

        // Retrieve the informations on the beans and its properties.
        BeanInfo beanInfo = BeanProcessor.processBean( logger, typeOracle, configuration, beanType );
        PropertiesContainer properties = PropertyProcessor
                .findAllProperties( configuration, logger, typeOracle, beanInfo, samePackage );
        beanInfo = BeanProcessor.processProperties( configuration, logger, typeOracle, beanInfo, properties );

        // We concatenate the name of all the enclosing classes.
        StringBuilder builder = new StringBuilder( beanType.getSimpleSourceName() );
        JClassType enclosingType = beanType.getEnclosingType();
        while ( null != enclosingType ) {
            builder.insert( 0, enclosingType.getSimpleSourceName() + "_" );
            enclosingType = enclosingType.getEnclosingType();
        }

        // If the type is specific to the mapper, we concatenate the name and hash of the mapper to it.
        boolean isSpecificToMapper = configuration.isSpecificToMapper( beanType );
        if ( isSpecificToMapper ) {
            JClassType rootMapperClass = configuration.getRootMapperClass();
            builder.insert( 0, '_' ).insert( 0, configuration.getRootMapperHash() ).insert( 0, '_' ).insert( 0, rootMapperClass
                    .getSimpleSourceName() );
        }

        String simpleSerializerClassName = builder.toString() + "BeanJsonSerializerImpl";
        String simpleDeserializerClassName = builder.toString() + "BeanJsonDeserializerImpl";

        mapperInfo = new BeanJsonMapperInfo( beanType, packageName, samePackage, simpleSerializerClassName,
                simpleDeserializerClassName, beanInfo, properties
                .getProperties() );

        typeOracle.addBeanJsonMapperInfo( beanType, mapperInfo );

        return mapperInfo;
    }

    protected abstract Optional<BeanJsonMapperInfo> getMapperInfo();

    /**
     * Build a {@link JSerializerType} that instantiate a {@link JsonSerializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonSerializer} will be created.
     *
     * @param type type
     *
     * @return the {@link JSerializerType}. Examples:
     * <ul>
     * <li>ctx.getIntegerSerializer()</li>
     * <li>new org.PersonBeanJsonSerializer()</li>
     * </ul>
     */
    protected final JSerializerType getJsonSerializerFromType( JType type ) throws UnableToCompleteException, UnsupportedTypeException {
        return getJsonSerializerFromType( type, false );
    }

    /**
     * Build a {@link JSerializerType} that instantiate a {@link JsonSerializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonSerializer} will be created.
     *
     * @param type type
     * @param subtype true if the serializer is for a subtype
     *
     * @return the {@link JSerializerType}. Examples:
     * <ul>
     * <li>ctx.getIntegerSerializer()</li>
     * <li>new org.PersonBeanJsonSerializer()</li>
     * </ul>
     */
    protected final JSerializerType getJsonSerializerFromType( JType type, boolean subtype )
            throws UnableToCompleteException, UnsupportedTypeException {

        JSerializerType.Builder builder = new JSerializerType.Builder().type( type );
        if ( null != type.isWildcard() ) {
            // For wildcard type, we use the base type to find the serializer.
            type = type.isWildcard().getBaseType();
        }

        if ( null != type.isRawType() ) {
            // For raw type, we use the base type to find the serializer.
            type = type.isRawType().getBaseType();
        }

        JTypeParameter typeParameter = type.isTypeParameter();
        if ( null != typeParameter ) {
            // It's a type parameter like T in 'MyClass<T>'
            if ( !subtype || typeParameter.getDeclaringClass() == getMapperInfo().get().getType() ) {
                // The serializer is created for the main type so we use the serializer field declared for this type.
                return builder.instance( CodeBlock.builder()
                        .add( String.format( TYPE_PARAMETER_SERIALIZER_FIELD_NAME, typeParameter.getOrdinal() ) )
                        .build() )
                        .build();
            } else {
                // There is no declared serializer so we use the base type to find a serializer.
                type = typeParameter.getBaseType();
            }
        }

        Optional<MapperInstance> configuredSerializer = configuration.getSerializer( type );
        if ( configuredSerializer.isPresent() ) {
            // The type is configured in AbstractConfiguration.
            if ( null != type.isParameterized() || null != type.isGenericType() ) {
                JClassType[] typeArgs;
                if ( null != type.isGenericType() ) {
                    typeArgs = type.isGenericType().asParameterizedByWildcards().getTypeArgs();
                } else {
                    typeArgs = type.isParameterized().getTypeArgs();
                }

                ImmutableList.Builder<JSerializerType> parametersSerializerBuilder = ImmutableList.builder();
                for ( int i = 0; i < typeArgs.length; i++ ) {
                    JSerializerType parameterSerializerType;
                    if ( MapperType.KEY_SERIALIZER == configuredSerializer.get().getParameters()[i] ) {
                        parameterSerializerType = getKeySerializerFromType( typeArgs[i] );
                    } else {
                        parameterSerializerType = getJsonSerializerFromType( typeArgs[i], subtype );
                    }
                    parametersSerializerBuilder.add( parameterSerializerType );
                }
                ImmutableList<JSerializerType> parametersSerializer = parametersSerializerBuilder.build();
                builder.parameters( parametersSerializer );
                builder.instance( methodCallCode( configuredSerializer.get(), parametersSerializer ) );

            } else {
                // The serializer has no parameters.
                builder.instance( methodCallCode( configuredSerializer.get() ) );
            }
            return builder.build();
        }

        if ( typeOracle.isJavaScriptObject( type ) ) {
            // It's a JSO and the user didn't give a custom serializer. We use the default one.
            configuredSerializer = configuration.getSerializer( typeOracle.getJavaScriptObject() );
            return builder.instance( methodCallCode( configuredSerializer.get() ) ).build();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return builder.instance( CodeBlock.builder()
                    .add( "$T.<$T<$T>>getInstance()", EnumJsonSerializer.class, EnumJsonSerializer.class, typeName( enumType ) )
                    .build() )
                    .build();
        }

        if ( Enum.class.getName().equals( type.getQualifiedSourceName() ) ) {
            return builder.instance( CodeBlock.builder().add( "$T.getInstance()", EnumJsonSerializer.class ).build() ).build();
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            Class arraySerializer;
            if ( arrayType.getRank() == 1 ) {
                // One dimension array
                arraySerializer = ArrayJsonSerializer.class;
            } else if ( arrayType.getRank() == 2 ) {
                // Two dimension array
                arraySerializer = Array2dJsonSerializer.class;
            } else {
                // More dimensions are not supported
                String message = "Arrays with 3 or more dimensions are not supported";
                logger.log( TreeLogger.Type.WARN, message );
                throw new UnsupportedTypeException( message );
            }
            JSerializerType parameterSerializerType = getJsonSerializerFromType( arrayType.getLeafType(), subtype );
            builder.parameters( ImmutableList.of( parameterSerializerType ) );
            builder.instance( CodeBlock.builder()
                    .add( "$T.newInstance($L)", arraySerializer, parameterSerializerType.getInstance() )
                    .build() );
            return builder.build();
        }

        if ( null != type.isAnnotation() ) {
            String message = "Annotations are not supported";
            logger.log( TreeLogger.Type.WARN, message );
            throw new UnsupportedTypeException( message );
        }

        JClassType classType = type.isClassOrInterface();
        if ( null != classType ) {
            // The type is a class or interface and has no default serializer. We generate one.
            JClassType baseClassType = classType;
            JParameterizedType parameterizedType = classType.isParameterized();
            if ( null != parameterizedType ) {
                // It's a bean with generics, we create a serializer based on generic type.
                baseClassType = parameterizedType.getBaseType();
            }

            BeanJsonSerializerCreator beanJsonSerializerCreator = new BeanJsonSerializerCreator(
                    logger.branch( Type.DEBUG, "Creating serializer for " + baseClassType.getQualifiedSourceName() ),
                    context, configuration, typeOracle, baseClassType );
            BeanJsonMapperInfo mapperInfo = beanJsonSerializerCreator.create();

            // Generics and parameterized types serializers have no default constructor. They need serializers for each parameter.
            ImmutableList<? extends JType> typeParameters = getTypeParameters( classType, subtype );
            ImmutableList.Builder<JSerializerType> parametersSerializerBuilder = ImmutableList.builder();
            for ( JType argType : typeParameters ) {
                parametersSerializerBuilder.add( getJsonSerializerFromType( argType, subtype ) );
            }
            ImmutableList<JSerializerType> parametersSerializer = parametersSerializerBuilder.build();

            builder.parameters( parametersSerializer );
            builder.beanMapper( true );
            builder.instance( constructorCallCode(
                    ClassName.get( mapperInfo.getPackageName(), mapperInfo.getSimpleSerializerClassName() ), parametersSerializer ) );
            return builder.build();
        }

        String message = "Type '" + type.getQualifiedSourceName() + "' is not supported";
        logger.log( TreeLogger.Type.WARN, message );
        throw new UnsupportedTypeException( message );
    }

    /**
     * Build the {@link JSerializerType} that instantiate a {@link KeySerializer} for the given type.
     *
     * @param type type
     *
     * @return the {@link JSerializerType}.
     */
    protected final JSerializerType getKeySerializerFromType( JType type ) throws UnsupportedTypeException {
        JSerializerType.Builder builder = new JSerializerType.Builder().type( type );
        if ( null != type.isWildcard() ) {
            // For wildcard type, we use the base type to find the serializer.
            type = type.isWildcard().getBaseType();
        }

        if ( null != type.isRawType() ) {
            // For raw type, we use the base type to find the serializer.
            type = type.isRawType().getBaseType();
        }

        Optional<MapperInstance> keySerializer = configuration.getKeySerializer( type );
        if ( keySerializer.isPresent() ) {
            builder.instance( methodCallCode( keySerializer.get() ) );
            return builder.build();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            builder.instance( CodeBlock.builder()
                    .add( "$T.<$T<$T>>getInstance()", EnumKeySerializer.class, EnumKeySerializer.class, typeName( enumType ) )
                    .build() );
            return builder.build();
        }

        if ( Enum.class.getName().equals( type.getQualifiedSourceName() ) ) {
            return builder.instance( CodeBlock.builder().add( "$T.getInstance()", EnumKeySerializer.class ).build() ).build();
        }

        String message = "Type '" + type.getQualifiedSourceName() + "' is not supported as map's key";
        logger.log( TreeLogger.Type.WARN, message );
        throw new UnsupportedTypeException( message );
    }

    /**
     * Build a {@link JDeserializerType} that instantiate a {@link JsonSerializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonSerializer} will be created.
     *
     * @param type type
     *
     * @return the {@link JDeserializerType}. Examples:
     * <ul>
     * <li>ctx.getIntegerDeserializer()</li>
     * <li>new org .PersonBeanJsonDeserializer()</li>
     * </ul>
     */
    protected final JDeserializerType getJsonDeserializerFromType( JType type ) throws UnableToCompleteException, UnsupportedTypeException {
        return getJsonDeserializerFromType( type, false );
    }

    /**
     * Build a {@link JDeserializerType} that instantiate a {@link JsonSerializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonSerializer} will be created.
     *
     * @param type type
     * @param subtype true if the deserializer is for a subtype
     *
     * @return the {@link JDeserializerType}. Examples:
     * <ul>
     * <li>ctx.getIntegerDeserializer()</li>
     * <li>new org .PersonBeanJsonDeserializer()</li>
     * </ul>
     */
    protected final JDeserializerType getJsonDeserializerFromType( JType type, boolean subtype ) throws UnableToCompleteException,
            UnsupportedTypeException {
        JDeserializerType.Builder builder = new JDeserializerType.Builder().type( type );
        if ( null != type.isWildcard() ) {
            // For wildcard type, we use the base type to find the deserializer.
            type = type.isWildcard().getBaseType();
        }

        if ( null != type.isRawType() ) {
            // For raw type, we use the base type to find the deserializer.
            type = type.isRawType().getBaseType();
        }

        JTypeParameter typeParameter = type.isTypeParameter();
        if ( null != typeParameter ) {
            // It's a type parameter like T in 'MyClass<T>'
            if ( !subtype || typeParameter.getDeclaringClass() == getMapperInfo().get().getType() ) {
                // The deserializer is created for the main type so we use the deserializer field declared for this type.
                return builder.instance( CodeBlock.builder()
                        .add( String.format( TYPE_PARAMETER_DESERIALIZER_FIELD_NAME, typeParameter.getOrdinal() ) )
                        .build() )
                        .build();
            } else {
                // There is no declared deserializer so we use the base type to find a deserializer.
                type = typeParameter.getBaseType();
            }
        }

        Optional<MapperInstance> configuredDeserializer = configuration.getDeserializer( type );
        if ( configuredDeserializer.isPresent() ) {
            // The type is configured in AbstractConfiguration.
            if ( null != type.isParameterized() || null != type.isGenericType() ) {
                JClassType[] typeArgs;
                if ( null != type.isGenericType() ) {
                    typeArgs = type.isGenericType().asParameterizedByWildcards().getTypeArgs();
                } else {
                    typeArgs = type.isParameterized().getTypeArgs();
                }

                ImmutableList.Builder<JDeserializerType> parametersDeserializerBuilder = ImmutableList.builder();
                for ( int i = 0; i < typeArgs.length; i++ ) {
                    JDeserializerType parameterDeserializerType;
                    if ( MapperType.KEY_DESERIALIZER == configuredDeserializer.get().getParameters()[i] ) {
                        parameterDeserializerType = getKeyDeserializerFromType( typeArgs[i] );
                    } else {
                        parameterDeserializerType = getJsonDeserializerFromType( typeArgs[i], subtype );
                    }
                    parametersDeserializerBuilder.add( parameterDeserializerType );
                }
                ImmutableList<JDeserializerType> parametersDeserializer = parametersDeserializerBuilder.build();
                builder.parameters( parametersDeserializer );
                builder.instance( methodCallCode( configuredDeserializer.get(), parametersDeserializer ) );

            } else {
                // The deserializer has no parameters.
                builder.instance( methodCallCode( configuredDeserializer.get() ) );
            }
            return builder.build();
        }

        if ( typeOracle.isJavaScriptObject( type ) ) {
            // It's a JSO and the user didn't give a custom deserializer. We use the default one.
            configuredDeserializer = configuration.getDeserializer( typeOracle.getJavaScriptObject() );
            return builder.instance( methodCallCode( configuredDeserializer.get() ) ).build();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return builder.instance( CodeBlock.builder()
                    .add( "$T.newInstance($T.class)", EnumJsonDeserializer.class, typeName( enumType ) )
                    .build() )
                    .build();
        }

        if ( Enum.class.getName().equals( type.getQualifiedSourceName() ) ) {
            String message = "Type java.lang.Enum is not supported by deserialization";
            logger.log( TreeLogger.Type.WARN, message );
            throw new UnsupportedTypeException( message );
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            TypeSpec arrayCreator;
            Class arrayDeserializer;
            JType leafType = arrayType.getLeafType();

            if ( arrayType.getRank() == 1 ) {
                // One dimension array
                arrayCreator = TypeSpec.anonymousClassBuilder( "" )
                        .addSuperinterface( parameterizedName( ArrayCreator.class, leafType ) )
                        .addMethod( MethodSpec.methodBuilder( "create" )
                                .addAnnotation( Override.class )
                                .addModifiers( Modifier.PUBLIC )
                                .addParameter( int.class, "length" )
                                .addStatement( "return new $T[$N]", rawName( leafType ), "length" )
                                .returns( typeName( arrayType ) )
                                .build() )
                        .build();
                arrayDeserializer = ArrayJsonDeserializer.class;

            } else if ( arrayType.getRank() == 2 ) {
                // Two dimensions array
                arrayCreator = TypeSpec.anonymousClassBuilder( "" )
                        .addSuperinterface( parameterizedName( Array2dCreator.class, leafType ) )
                        .addMethod( MethodSpec.methodBuilder( "create" )
                                .addAnnotation( Override.class )
                                .addModifiers( Modifier.PUBLIC )
                                .addParameter( int.class, "first" )
                                .addParameter( int.class, "second" )
                                .addStatement( "return new $T[$N][$N]", rawName( leafType ), "first", "second" )
                                .returns( typeName( arrayType ) )
                                .build() )
                        .build();
                arrayDeserializer = Array2dJsonDeserializer.class;

            } else {
                // More dimensions are not supported
                String message = "Arrays with 3 or more dimensions are not supported";
                logger.log( TreeLogger.Type.WARN, message );
                throw new UnsupportedTypeException( message );
            }

            JDeserializerType parameterDeserializerType = getJsonDeserializerFromType( leafType, subtype );
            builder.parameters( ImmutableList.of( parameterDeserializerType ) );
            builder.instance( CodeBlock.builder().add( "$T.newInstance($L, $L)", arrayDeserializer, parameterDeserializerType
                    .getInstance(), arrayCreator ).build() );
            return builder.build();
        }

        if ( null != type.isAnnotation() ) {
            String message = "Annotations are not supported";
            logger.log( TreeLogger.Type.WARN, message );
            throw new UnsupportedTypeException( message );
        }

        JClassType classType = type.isClassOrInterface();
        if ( null != classType ) {
            // The type is a class or interface and has no default deserializer. We generate one.
            JClassType baseClassType = classType;
            JParameterizedType parameterizedType = classType.isParameterized();
            if ( null != parameterizedType ) {
                // It's a bean with generics, we create a deserializer based on generic type.
                baseClassType = parameterizedType.getBaseType();
            }

            BeanJsonDeserializerCreator beanJsonDeserializerCreator = new BeanJsonDeserializerCreator(
                    logger.branch( Type.DEBUG, "Creating deserializer for " + baseClassType.getQualifiedSourceName() ),
                    context, configuration, typeOracle, baseClassType );
            BeanJsonMapperInfo mapperInfo = beanJsonDeserializerCreator.create();

            // Generics and parameterized types deserializers have no default constructor. They need deserializers for each parameter.
            ImmutableList<? extends JType> typeParameters = getTypeParameters( classType, subtype );
            ImmutableList.Builder<JDeserializerType> parametersDeserializerBuilder = ImmutableList.builder();
            for ( JType argType : typeParameters ) {
                parametersDeserializerBuilder.add( getJsonDeserializerFromType( argType, subtype ) );
            }
            ImmutableList<JDeserializerType> parametersDeserializer = parametersDeserializerBuilder.build();

            builder.parameters( parametersDeserializer );
            builder.beanMapper( true );
            builder.instance( constructorCallCode(
                    ClassName.get( mapperInfo.getPackageName(), mapperInfo.getSimpleDeserializerClassName() ), parametersDeserializer ) );
            return builder.build();
        }

        String message = "Type '" + type.getQualifiedSourceName() + "' is not supported";
        logger.log( TreeLogger.Type.WARN, message );
        throw new UnsupportedTypeException( message );
    }

    /**
     * Build the {@link JDeserializerType} that instantiate a {@link KeyDeserializer} for the given type.
     *
     * @param type type
     *
     * @return the {@link JDeserializerType}.
     */
    protected final JDeserializerType getKeyDeserializerFromType( JType type ) throws UnsupportedTypeException {
        JDeserializerType.Builder builder = new JDeserializerType.Builder().type( type );
        if ( null != type.isWildcard() ) {
            // For wildcard type, we use the base type to find the deserializer.
            type = type.isWildcard().getBaseType();
        }

        if ( null != type.isRawType() ) {
            // For raw type, we use the base type to find the deserializer.
            type = type.isRawType().getBaseType();
        }

        Optional<MapperInstance> keyDeserializer = configuration.getKeyDeserializer( type );
        if ( keyDeserializer.isPresent() ) {
            builder.instance( methodCallCode( keyDeserializer.get() ) );
            return builder.build();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            builder.instance( CodeBlock.builder()
                    .add( "$T.newInstance($T.class)", EnumKeyDeserializer.class, typeName( enumType ) )
                    .build() );
            return builder.build();
        }

        String message = "Type '" + type.getQualifiedSourceName() + "' is not supported as map's key";
        logger.log( TreeLogger.Type.WARN, message );
        throw new UnsupportedTypeException( message );
    }

    private ImmutableList<? extends JType> getTypeParameters( JClassType classType, boolean subtype ) {
        JParameterizedType parameterizedType = classType.isParameterized();
        if ( null != parameterizedType ) {
            return ImmutableList.copyOf( parameterizedType.getTypeArgs() );
        }

        JGenericType genericType = classType.isGenericType();
        if ( null != genericType ) {
            if ( subtype ) {
                // if it's a subtype we look for parent in hierarchy equals to mapped class
                JClassType mappedClassType = getMapperInfo().get().getType();
                JClassType parentClassType = null;
                for ( JClassType parent : genericType.getFlattenedSupertypeHierarchy() ) {
                    if ( parent.getQualifiedSourceName().equals( mappedClassType.getQualifiedSourceName() ) ) {
                        parentClassType = parent;
                        break;
                    }
                }

                ImmutableList.Builder<JType> builder = ImmutableList.builder();
                for ( JTypeParameter typeParameter : genericType.getTypeParameters() ) {
                    JType arg = null;
                    if ( null != parentClassType && null != parentClassType.isParameterized() ) {
                        int i = 0;
                        for ( JClassType parentTypeParameter : parentClassType.isParameterized().getTypeArgs() ) {
                            if ( null != parentTypeParameter.isTypeParameter() && parentTypeParameter.isTypeParameter().getName()
                                    .equals( typeParameter.getName() ) ) {
                                if ( null != mappedClassType.isGenericType() ) {
                                    arg = mappedClassType.isGenericType().getTypeParameters()[i];
                                } else {
                                    arg = mappedClassType.isParameterized().getTypeArgs()[i];
                                }
                                break;
                            }
                            i++;
                        }
                    }
                    if ( null == arg ) {
                        arg = typeParameter.getBaseType();
                    }
                    builder.add( arg );
                }
                return builder.build();
            } else {
                ImmutableList.Builder<JType> builder = ImmutableList.builder();
                for ( JTypeParameter typeParameter : genericType.getTypeParameters() ) {
                    builder.add( typeParameter.getBaseType() );
                }
                return builder.build();
            }
        }

        return ImmutableList.of();
    }

    /**
     * Build the code to call the constructor of a class
     *
     * @param className the class to call
     * @param parameters the parameters of the constructor
     *
     * @return the code calling the constructor
     */
    private CodeBlock constructorCallCode( ClassName className, ImmutableList<? extends JMapperType> parameters ) {
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.add( "new $T", className );
        return methodCallParametersCode( builder, parameters );
    }

    /**
     * Build the code to create a mapper.
     *
     * @param instance the class to call
     *
     * @return the code to create the mapper
     */
    private CodeBlock methodCallCode( MapperInstance instance ) {
        return methodCallCode( instance, ImmutableList.<JMapperType>of() );
    }

    /**
     * Build the code to create a mapper.
     *
     * @param instance the class to call
     * @param parameters the parameters of the method
     *
     * @return the code to create the mapper
     */
    private CodeBlock methodCallCode( MapperInstance instance, ImmutableList<? extends JMapperType> parameters ) {
        CodeBlock.Builder builder = CodeBlock.builder();
        if ( null == instance.getInstanceCreationMethod().isConstructor() ) {
            builder.add( "$T.$L", rawName( instance.getMapperType() ), instance.getInstanceCreationMethod().getName() );
        } else {
            builder.add( "new $T", typeName( instance.getMapperType() ) );
        }
        return methodCallParametersCode( builder, parameters );
    }

    /**
     * Build the code for the parameters of a method call.
     *
     * @param builder the code builder
     * @param parameters the parameters
     *
     * @return the code
     */
    private CodeBlock methodCallParametersCode( CodeBlock.Builder builder, ImmutableList<? extends JMapperType> parameters ) {
        if ( parameters.isEmpty() ) {
            return builder.add( "()" ).build();
        }

        builder.add( "(" );

        Iterator<? extends JMapperType> iterator = parameters.iterator();
        builder.add( iterator.next().getInstance() );

        while ( iterator.hasNext() ) {
            builder.add( ", " );
            builder.add( iterator.next().getInstance() );
        }

        builder.add( ")" );
        return builder.build();
    }

}
