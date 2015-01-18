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

import java.io.PrintWriter;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer.ArrayCreator;
import com.github.nmorel.gwtjackson.client.deser.array.dd.Array2dJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.dd.Array2dJsonDeserializer.Array2dCreator;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractBeanJsonDeserializer;
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
import com.github.nmorel.gwtjackson.rebind.exception.UnsupportedTypeException;
import com.github.nmorel.gwtjackson.rebind.type.JDeserializerType;
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
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractCreator extends AbstractSourceCreator {

    protected static final String JSON_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.JsonDeserializer";

    protected static final String JSON_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.JsonSerializer";

    protected static final String JSON_DESERIALIZATION_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonDeserializationContext";

    protected static final String JSON_SERIALIZATION_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonSerializationContext";

    protected static final String TYPE_PARAMETER_DESERIALIZER_FIELD_NAME = "deserializer%d";

    protected static final String TYPE_PARAMETER_SERIALIZER_FIELD_NAME = "serializer%d";

    protected final TreeLogger logger;

    protected final GeneratorContext context;

    protected final RebindConfiguration configuration;

    protected final JacksonTypeOracle typeOracle;

    protected AbstractCreator( TreeLogger logger, GeneratorContext context, RebindConfiguration configuration,
                               JacksonTypeOracle typeOracle ) {
        this.logger = logger;
        this.context = context;
        this.configuration = configuration;
        this.typeOracle = typeOracle;
    }

    protected PrintWriter getPrintWriter( String packageName, String className ) {
        return context.tryCreate( logger, packageName, className );
    }

    protected SourceWriter getSourceWriter( PrintWriter printWriter, String packageName, String className, String superClass,
                                            String... interfaces ) {
        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory( packageName, className );
        if ( null != superClass ) {
            composer.setSuperclass( superClass );
        }
        for ( String interfaceName : interfaces ) {
            composer.addImplementedInterface( interfaceName );
        }
        return composer.createSourceWriter( context, printWriter );
    }

    protected abstract Optional<BeanJsonMapperInfo> getMapperInfo();

    /**
     * Build the string that instantiate a {@link JsonSerializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonSerializer} will
     * be created.
     *
     * @param type type
     *
     * @return the code instantiating the {@link JsonSerializer}. Examples:
     * <ul>
     * <li>ctx.getIntegerSerializer()</li>
     * <li>new org.PersonBeanJsonSerializer()</li>
     * </ul>
     */
    protected JSerializerType getJsonSerializerFromType( JType type ) throws UnableToCompleteException, UnsupportedTypeException {
        return getJsonSerializerFromType( type, false );
    }

    /**
     * Build the string that instantiate a {@link JsonSerializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonSerializer} will
     * be created.
     *
     * @param type type
     * @param subtype true if the serializer is for a subtype
     *
     * @return the code instantiating the {@link JsonSerializer}. Examples:
     * <ul>
     * <li>ctx.getIntegerSerializer()</li>
     * <li>new org.PersonBeanJsonSerializer()</li>
     * </ul>
     */
    protected JSerializerType getJsonSerializerFromType( JType type, boolean subtype ) throws UnableToCompleteException,
            UnsupportedTypeException {
        JSerializerType.Builder builder = new JSerializerType.Builder().type( type );
        if ( null != type.isWildcard() ) {
            // we use the base type to find the serializer to use
            type = type.isWildcard().getBaseType();
        }

        if ( null != type.isRawType() ) {
            type = type.isRawType().getBaseType();
        }

        JTypeParameter typeParameter = type.isTypeParameter();
        if ( null != typeParameter ) {
            if ( !subtype || typeParameter.getDeclaringClass() == getMapperInfo().get().getType() ) {
                return builder.instance( String.format( TYPE_PARAMETER_SERIALIZER_FIELD_NAME, typeParameter.getOrdinal() ) ).build();
            } else {
                type = typeParameter.getBaseType();
            }
        }

        Optional<MapperInstance> configuredSerializer = configuration.getSerializer( type );
        if ( configuredSerializer.isPresent() ) {
            if ( null != type.isParameterized() || null != type.isGenericType() ) {
                JClassType[] typeArgs;
                if ( null != type.isGenericType() ) {
                    typeArgs = type.isGenericType().asParameterizedByWildcards().getTypeArgs();
                } else {
                    typeArgs = type.isParameterized().getTypeArgs();
                }

                ImmutableList.Builder<JSerializerType> parametersSerializerBuilder = ImmutableList.builder();
                String[] params = new String[typeArgs.length];

                for ( int i = 0; i < params.length; i++ ) {
                    JSerializerType parameterSerializerType;
                    if ( MapperType.KEY_SERIALIZER == configuredSerializer.get().getParameters()[i] ) {
                        parameterSerializerType = getKeySerializerFromType( typeArgs[i] );
                    } else {
                        parameterSerializerType = getJsonSerializerFromType( typeArgs[i], subtype );
                    }
                    parametersSerializerBuilder.add( parameterSerializerType );
                    params[i] = parameterSerializerType.getInstance();
                }
                builder.parameters( parametersSerializerBuilder.build() );
                builder.instance( String.format( configuredSerializer.get().getInstanceCreation(), params ) );

            } else {
                builder.instance( configuredSerializer.get().getInstanceCreation() );
            }
            return builder.build();
        }

        if ( typeOracle.isJavaScriptObject( type ) ) {
            // it's a JSO and the user didn't give a custom serializer. We use the default one.
            configuredSerializer = configuration.getSerializer( typeOracle.getJavaScriptObject() );
            return builder.instance( configuredSerializer.get().getInstanceCreation() ).build();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return builder.instance( String.format( "%s.<%s<%s>>getInstance()", EnumJsonSerializer.class
                    .getCanonicalName(), EnumJsonSerializer.class.getCanonicalName(), enumType.getQualifiedSourceName() ) ).build();
        }

        if ( Enum.class.getName().equals( type.getQualifiedSourceName() ) ) {
            return builder.instance( String.format( "%s.getInstance()", EnumJsonSerializer.class.getCanonicalName() ) ).build();
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            String arraySerializer;
            if ( arrayType.getRank() == 1 ) {
                // one dimension array
                arraySerializer = ArrayJsonSerializer.class.getCanonicalName();
            } else if ( arrayType.getRank() == 2 ) {
                // two dimension array
                arraySerializer = Array2dJsonSerializer.class.getCanonicalName();
            } else {
                // more dimensions are not supported
                String message = "Arrays with 3 or more dimensions are not supported";
                logger.log( TreeLogger.Type.WARN, message );
                throw new UnsupportedTypeException( message );
            }
            JSerializerType parameterSerializerType = getJsonSerializerFromType( arrayType.getLeafType(), subtype );
            builder.parameters( ImmutableList.of( parameterSerializerType ) );
            return builder.instance( String.format( "%s.newInstance(%s)", arraySerializer, parameterSerializerType.getInstance() ) )
                    .build();
        }

        if ( null != type.isAnnotation() ) {
            String message = "Annotations are not supported";
            logger.log( TreeLogger.Type.WARN, message );
            throw new UnsupportedTypeException( message );
        }

        JClassType classType = type.isClassOrInterface();
        if ( null != classType ) {
            // it's a bean
            JClassType baseClassType = classType;
            JParameterizedType parameterizedType = classType.isParameterized();
            if ( null != parameterizedType ) {
                // it's a bean with generics, we create a serializer based on generic type
                baseClassType = parameterizedType.getBaseType();
            }

            BeanJsonSerializerCreator beanJsonSerializerCreator = new BeanJsonSerializerCreator( logger
                    .branch( Type.DEBUG, "Creating serializer for " + baseClassType
                            .getQualifiedSourceName() ), context, configuration, typeOracle );
            String qualifiedClassName = beanJsonSerializerCreator.create( baseClassType );

            ImmutableList<? extends JType> typeParameters = getTypeParameters( classType, subtype );
            StringBuilder joinedTypeParameterSerializers = new StringBuilder();
            if ( !typeParameters.isEmpty() ) {
                ImmutableList.Builder<JSerializerType> parametersSerializerBuilder = ImmutableList.builder();
                boolean first = true;
                for ( JType argType : typeParameters ) {
                    if ( first ) {
                        first = false;
                    } else {
                        joinedTypeParameterSerializers.append( ", " );
                    }

                    JSerializerType parameterSerializerType = getJsonSerializerFromType( argType, subtype );
                    parametersSerializerBuilder.add( parameterSerializerType );
                    joinedTypeParameterSerializers.append( parameterSerializerType.getInstance() );
                }
                builder.parameters( parametersSerializerBuilder.build() );
            }

            builder.beanMapper( true );
            builder.instance( String.format( "new %s(%s)", qualifiedClassName, joinedTypeParameterSerializers ) );
            return builder.build();
        }

        String message = "Type '" + type.getQualifiedSourceName() + "' is not supported";
        logger.log( TreeLogger.Type.WARN, message );
        throw new UnsupportedTypeException( message );
    }

    /**
     * Build the string that instantiate a {@link KeySerializer} for the given type.
     *
     * @param type type
     *
     * @return the code instantiating the {@link KeySerializer}.
     */
    protected JSerializerType getKeySerializerFromType( JType type ) throws UnsupportedTypeException {
        JSerializerType.Builder builder = new JSerializerType.Builder().type( type );
        if ( null != type.isWildcard() ) {
            // we use the base type to find the serializer to use
            type = type.isWildcard().getBaseType();
        }

        Optional<MapperInstance> keySerializer = configuration.getKeySerializer( type );
        if ( keySerializer.isPresent() ) {
            builder.instance( keySerializer.get().getInstanceCreation() );
            return builder.build();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            builder.instance( String.format( "%s.<%s<%s>>getInstance()", EnumKeySerializer.class.getCanonicalName(), EnumKeySerializer.class
                    .getCanonicalName(), enumType.getQualifiedSourceName() ) );
            return builder.build();
        }

        if ( Enum.class.getName().equals( type.getQualifiedSourceName() ) ) {
            return builder.instance( String.format( "%s.getInstance()", EnumKeySerializer.class.getCanonicalName() ) ).build();
        }

        String message = "Type '" + type.getQualifiedSourceName() + "' is not supported as map's key";
        logger.log( TreeLogger.Type.WARN, message );
        throw new UnsupportedTypeException( message );
    }

    /**
     * Build the string that instantiate a {@link JsonDeserializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonDeserializer} will
     * be created.
     *
     * @param type type
     *
     * @return the code instantiating the deserializer. Examples:
     * <ul>
     * <li>ctx.getIntegerDeserializer()</li>
     * <li>new org .PersonBeanJsonDeserializer()</li>
     * </ul>
     */
    protected JDeserializerType getJsonDeserializerFromType( JType type ) throws UnableToCompleteException, UnsupportedTypeException {
        return getJsonDeserializerFromType( type, false );
    }

    /**
     * Build the string that instantiate a {@link JsonDeserializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonDeserializer} will
     * be created.
     *
     * @param type type
     * @param subtype true if the deserializer is for a subtype
     *
     * @return the code instantiating the deserializer. Examples:
     * <ul>
     * <li>ctx.getIntegerDeserializer()</li>
     * <li>new org .PersonBeanJsonDeserializer()</li>
     * </ul>
     */
    protected JDeserializerType getJsonDeserializerFromType( JType type, boolean subtype ) throws UnableToCompleteException,
            UnsupportedTypeException {
        JDeserializerType.Builder builder = new JDeserializerType.Builder().type( type );
        if ( null != type.isWildcard() ) {
            // we use the base type to find the deserializer to use
            type = type.isWildcard().getBaseType();
        }

        if ( null != type.isRawType() ) {
            type = type.isRawType().getBaseType();
        }

        JTypeParameter typeParameter = type.isTypeParameter();
        if ( null != typeParameter ) {
            if ( !subtype || typeParameter.getDeclaringClass() == getMapperInfo().get().getType() ) {
                return builder.instance( String.format( TYPE_PARAMETER_DESERIALIZER_FIELD_NAME, typeParameter.getOrdinal() ) ).build();
            } else {
                type = typeParameter.getBaseType();
            }
        }

        Optional<MapperInstance> configuredDeserializer = configuration.getDeserializer( type );
        if ( configuredDeserializer.isPresent() ) {
            if ( null != type.isParameterized() || null != type.isGenericType() ) {
                JClassType[] typeArgs;
                if ( null != type.isGenericType() ) {
                    typeArgs = type.isGenericType().asParameterizedByWildcards().getTypeArgs();
                } else {
                    typeArgs = type.isParameterized().getTypeArgs();
                }

                ImmutableList.Builder<JDeserializerType> parametersDeserializerBuilder = ImmutableList.builder();
                String[] params = new String[typeArgs.length];

                for ( int i = 0; i < params.length; i++ ) {
                    JDeserializerType parameterDeserializerType;
                    if ( MapperType.KEY_DESERIALIZER == configuredDeserializer.get().getParameters()[i] ) {
                        parameterDeserializerType = getKeyDeserializerFromType( typeArgs[i] );
                    } else {
                        parameterDeserializerType = getJsonDeserializerFromType( typeArgs[i], subtype );
                    }

                    parametersDeserializerBuilder.add( parameterDeserializerType );
                    params[i] = parameterDeserializerType.getInstance();
                }
                builder.parameters( parametersDeserializerBuilder.build() );
                builder.instance( String.format( configuredDeserializer.get().getInstanceCreation(), params ) );

            } else {
                builder.instance( configuredDeserializer.get().getInstanceCreation() );
            }
            return builder.build();
        }

        if ( typeOracle.isJavaScriptObject( type ) ) {
            // it's a JSO and the user didn't give a custom deserializer. We use the default one.
            configuredDeserializer = configuration.getDeserializer( typeOracle.getJavaScriptObject() );
            return builder.instance( configuredDeserializer.get().getInstanceCreation() ).build();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return builder.instance( EnumJsonDeserializer.class.getCanonicalName() + ".newInstance(" + enumType
                    .getQualifiedSourceName() + ".class)" ).build();
        }

        if ( Enum.class.getName().equals( type.getQualifiedSourceName() ) ) {
            String message = "Type java.lang.Enum is not supported by deserialization";
            logger.log( TreeLogger.Type.WARN, message );
            throw new UnsupportedTypeException( message );
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            String method = "%s.newInstance(%s, %s)";
            String arrayCreator;
            String arrayDeserializer;
            JType leafType = arrayType.getLeafType();

            if ( arrayType.getRank() == 1 ) {
                // one dimension array
                arrayCreator = "new " + ArrayCreator.class.getCanonicalName() + "<" + leafType
                        .getParameterizedQualifiedSourceName() + ">(){\n" +
                        "  @Override\n" +
                        "  public " + arrayType.getParameterizedQualifiedSourceName() + " create( int length ) {\n" +
                        "    return new " + leafType.getQualifiedSourceName() + "[length];\n" +
                        "  }\n" +
                        "}";
                arrayDeserializer = ArrayJsonDeserializer.class.getCanonicalName();

            } else if ( arrayType.getRank() == 2 ) {
                // 2 dimensions array
                arrayCreator = "new " + Array2dCreator.class.getCanonicalName() + "<" + leafType
                        .getParameterizedQualifiedSourceName() + ">(){\n" +
                        "  @Override\n" +
                        "  public " + arrayType.getParameterizedQualifiedSourceName() + " create( int first, int second ) {\n" +
                        "    return new " + leafType.getQualifiedSourceName() + "[first][second];\n" +
                        "  }\n" +
                        "}";
                arrayDeserializer = Array2dJsonDeserializer.class.getCanonicalName();

            } else {
                // more dimensions are not supported
                String message = "Arrays with 3 or more dimensions are not supported";
                logger.log( TreeLogger.Type.WARN, message );
                throw new UnsupportedTypeException( message );
            }

            JDeserializerType parameterDeserializerType = getJsonDeserializerFromType( leafType, subtype );
            builder.parameters( ImmutableList.of( parameterDeserializerType ) );
            return builder.instance( String.format( method, arrayDeserializer, parameterDeserializerType.getInstance(), arrayCreator ) )
                    .build();
        }

        if ( null != type.isAnnotation() ) {
            String message = "Annotations are not supported";
            logger.log( TreeLogger.Type.WARN, message );
            throw new UnsupportedTypeException( message );
        }

        JClassType classType = type.isClassOrInterface();
        if ( null != classType ) {
            // it's a bean
            JClassType baseClassType = classType;
            JParameterizedType parameterizedType = classType.isParameterized();
            if ( null != parameterizedType ) {
                // it's a bean with generics, we create a deserializer based on generic type
                baseClassType = parameterizedType.getBaseType();
            }

            BeanJsonDeserializerCreator beanJsonDeserializerCreator = new BeanJsonDeserializerCreator( logger
                    .branch( Type.DEBUG, "Creating deserializer for " + baseClassType
                            .getQualifiedSourceName() ), context, configuration, typeOracle );
            String qualifiedClassName = beanJsonDeserializerCreator.create( baseClassType );

            ImmutableList<? extends JType> typeParameters = getTypeParameters( classType, subtype );
            StringBuilder joinedTypeParameterDeserializers = new StringBuilder();
            if ( !typeParameters.isEmpty() ) {
                ImmutableList.Builder<JDeserializerType> parametersDeserializerBuilder = ImmutableList.builder();
                boolean first = true;
                for ( JType argType : typeParameters ) {
                    if ( first ) {
                        first = false;
                    } else {
                        joinedTypeParameterDeserializers.append( ", " );
                    }

                    JDeserializerType parameterDeserializerType = getJsonDeserializerFromType( argType, subtype );
                    parametersDeserializerBuilder.add( parameterDeserializerType );
                    joinedTypeParameterDeserializers.append( parameterDeserializerType.getInstance() );
                }
                builder.parameters( parametersDeserializerBuilder.build() );
            }

            builder.beanMapper( true );
            builder.instance( String.format( "new %s(%s)", qualifiedClassName, joinedTypeParameterDeserializers ) );
            return builder.build();
        }

        String message = "Type '" + type.getQualifiedSourceName() + "' is not supported";
        logger.log( TreeLogger.Type.WARN, message );
        throw new UnsupportedTypeException( message );
    }

    /**
     * Build the string that instantiate a {@link KeyDeserializer} for the given type.
     *
     * @param type type
     *
     * @return the code instantiating the {@link KeyDeserializer}.
     */
    protected JDeserializerType getKeyDeserializerFromType( JType type ) throws UnsupportedTypeException {
        JDeserializerType.Builder builder = new JDeserializerType.Builder().type( type );
        if ( null != type.isWildcard() ) {
            // we use the base type to find the serializer to use
            type = type.isWildcard().getBaseType();
        }

        Optional<MapperInstance> keyDeserializer = configuration.getKeyDeserializer( type );
        if ( keyDeserializer.isPresent() ) {
            builder.instance( keyDeserializer.get().getInstanceCreation() );
            return builder.build();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            builder.instance( String.format( "%s.newInstance(%s.class)", EnumKeyDeserializer.class.getCanonicalName(), enumType
                    .getQualifiedSourceName() ) );
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

}
