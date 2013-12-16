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

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractIdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.bean.PropertyIdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractIdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.ObjectIdSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.PropertyIdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.rebind.PropertyInfo.AdditionalMethod;
import com.github.nmorel.gwtjackson.rebind.RebindConfiguration.MapperInstance;
import com.github.nmorel.gwtjackson.rebind.RebindConfiguration.MapperType;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JEnumType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.user.rebind.AbstractSourceCreator;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;

import static com.github.nmorel.gwtjackson.rebind.CreatorUtils.QUOTED_FUNCTION;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractCreator extends AbstractSourceCreator {

    public static final String JSON_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.JsonDeserializer";

    public static final String JSON_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.JsonSerializer";

    public static final String JSON_READER_CLASS = "com.github.nmorel.gwtjackson.client.stream.JsonReader";

    public static final String JSON_DESERIALIZATION_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonDeserializationContext";

    public static final String JSON_SERIALIZATION_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonSerializationContext";

    public static final String ARRAY_CREATOR_CLASS = "com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer.ArrayCreator";

    protected static final String IDENTITY_DESERIALIZATION_INFO_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean" + "" + "" +
        ".IdentityDeserializationInfo";

    protected static final String IDENTITY_SERIALIZATION_INFO_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean" + "" + "" +
        ".IdentitySerializationInfo";

    protected static final String TYPE_PARAMETER_PREFIX = "p_";

    protected static final String TYPE_PARAMETER_DESERIALIZER_FIELD_NAME = "deserializer%d";

    protected static final String TYPE_PARAMETER_SERIALIZER_FIELD_NAME = "serializer%d";

    protected static final String ABSTRACT_BEAN_JSON_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean" + "" +
        ".AbstractBeanJsonDeserializer";

    protected static final String ABSTRACT_BEAN_JSON_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean" + "" +
        ".AbstractBeanJsonSerializer";

    protected static final String SUPERCLASS_DESERIALIZATION_INFO_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean" + "" +
        ".SuperclassDeserializationInfo";

    protected static final String SUBTYPE_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.deser.bean.SubtypeDeserializer";

    protected static final String SUPERCLASS_SERIALIZATION_INFO_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean" + "" +
        ".SuperclassSerializationInfo";

    protected static final String SUBTYPE_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.ser.bean.SubtypeSerializer";

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

    protected String getQualifiedClassName( JType type ) {
        if ( null == type.isPrimitive() ) {
            return type.getParameterizedQualifiedSourceName();
        } else {
            return type.isPrimitive().getQualifiedBoxedSourceName();
        }
    }

    /**
     * Build the string that instantiate a {@link JsonSerializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonSerializer} will
     * be created.
     *
     * @param type type
     *
     * @return the code instantiating the {@link JsonSerializer}. Examples:
     *         <ul>
     *         <li>ctx.getIntegerSerializer()</li>
     *         <li>new org.PersonBeanJsonSerializer()</li>
     *         </ul>
     */
    protected String getJsonSerializerFromType( JType type ) throws UnableToCompleteException {
        return getJsonSerializerFromType( type, null );
    }

    /**
     * Build the string that instantiate a {@link JsonSerializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonSerializer} will
     * be created.
     *
     * @param type type
     * @param propertyInfo additionnal info to gives to the serializer
     *
     * @return the code instantiating the {@link JsonSerializer}. Examples:
     *         <ul>
     *         <li>ctx.getIntegerSerializer()</li>
     *         <li>new org.PersonBeanJsonSerializer()</li>
     *         </ul>
     */
    protected String getJsonSerializerFromType( JType type, PropertyInfo propertyInfo ) throws UnableToCompleteException {
        if ( null != propertyInfo && propertyInfo.isRawValue() ) {
            return String.format( "ctx.<%s>getRawValueJsonSerializer()", type.getParameterizedQualifiedSourceName() );
        }

        JTypeParameter typeParameter = type.isTypeParameter();
        if ( null != typeParameter ) {
            return String.format( TYPE_PARAMETER_SERIALIZER_FIELD_NAME, typeParameter.getOrdinal() );
        }

        Optional<MapperInstance> configuredSerializer = configuration.getSerializer( type );
        if ( configuredSerializer.isPresent() ) {
            if ( null != type.isParameterized() ) {
                String[] params = new String[type.isParameterized().getTypeArgs().length];
                for ( int i = 0; i < params.length; i++ ) {
                    if ( MapperType.KEY_SERIALIZER == configuredSerializer.get().getParameters()[i] ) {
                        params[i] = getKeySerializerFromType( type.isParameterized().getTypeArgs()[i] );
                    } else {
                        params[i] = getJsonSerializerFromType( type.isParameterized().getTypeArgs()[i], propertyInfo );
                    }
                }
                return String.format( configuredSerializer.get().getInstanceCreation(), params );
            } else {
                return configuredSerializer.get().getInstanceCreation();
            }
        }

        if ( typeOracle.isObject( type ) ) {
            logger.log( Type.ERROR, "java.lang.Object is not a supported type" );
            throw new UnableToCompleteException();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return String.format( "ctx.<%s>getEnumJsonSerializer()", enumType.getQualifiedSourceName() );
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            return String.format( "ctx.newArrayJsonSerializer(%s)", getJsonSerializerFromType( arrayType
                .getComponentType(), propertyInfo ) );
        }

        JClassType classType = type.isClassOrInterface();
        JParameterizedType parameterizedType = type.isParameterized();
        if ( null != classType ) {
            // it's a bean
            JClassType baseClassType = classType;
            if ( null != parameterizedType ) {
                // it's a bean with generics, we create a serializer based on generic type
                baseClassType = typeOracle.findGenericType( parameterizedType );
            }
            BeanJsonSerializerCreator beanJsonSerializerCreator = new BeanJsonSerializerCreator( logger
                .branch( Type.DEBUG, "Creating serializer for " + baseClassType
                    .getQualifiedSourceName() ), context, configuration, typeOracle );
            BeanJsonMapperInfo info = beanJsonSerializerCreator.create( baseClassType );

            StringBuilder joinedTypeParameters = new StringBuilder();
            StringBuilder joinedTypeParameterSerializers = new StringBuilder();
            if ( null != parameterizedType ) {
                joinedTypeParameters.append( '<' );
                for ( int i = 0; i < parameterizedType.getTypeArgs().length; i++ ) {
                    if ( i > 0 ) {
                        joinedTypeParameters.append( ", " );
                        joinedTypeParameterSerializers.append( ", " );
                    }
                    JClassType argType = parameterizedType.getTypeArgs()[i];
                    joinedTypeParameters.append( argType.getParameterizedQualifiedSourceName() );
                    joinedTypeParameterSerializers.append( getJsonSerializerFromType( argType ) );
                }
                joinedTypeParameters.append( '>' );
            }

            String parameters = generateBeanJsonSerializerParameters( classType, info, propertyInfo );
            String sep = "";
            if ( joinedTypeParameterSerializers.length() > 0 && !parameters.isEmpty() ) {
                sep = ", ";
            }
            return String.format( "new %s%s(%s%s%s)", info.getQualifiedSerializerClassName(), joinedTypeParameters
                .toString(), joinedTypeParameterSerializers, sep, parameters );
        }

        logger.log( TreeLogger.Type.ERROR, "Type '" + type.getQualifiedSourceName() + "' is not supported" );
        throw new UnableToCompleteException();
    }

    private String generateBeanJsonSerializerParameters( JClassType type, BeanJsonMapperInfo info,
                                                         PropertyInfo propertyInfo ) throws UnableToCompleteException {
        if ( null == propertyInfo || (!propertyInfo.getIdentityInfo().isPresent() && !propertyInfo.getTypeInfo().isPresent()) ) {
            return "";
        }

        StringSourceWriter sourceWriter = new StringSourceWriter();

        if ( propertyInfo.getIdentityInfo().isPresent() ) {
            generateIdentifierSerializationInfo( sourceWriter, type, propertyInfo.getIdentityInfo().get() );
        } else {
            sourceWriter.print( "null" );
        }

        sourceWriter.print( ", " );

        if ( propertyInfo.getTypeInfo().isPresent() ) {
            generateSuperclassInfo( sourceWriter, info.getBeanInfo(), propertyInfo.getTypeInfo(), true );
        } else {
            sourceWriter.print( "null" );
        }

        return sourceWriter.toString();
    }

    protected void generateIdentifierSerializationInfo( SourceWriter source, JClassType type, BeanIdentityInfo identityInfo ) throws
        UnableToCompleteException {

        if ( identityInfo.isIdABeanProperty() ) {
            source.println( "new %s<%s>(%s, \"%s\")", PropertyIdentitySerializationInfo.class.getName(), type
                .getParameterizedQualifiedSourceName(), identityInfo.isAlwaysAsId(), identityInfo.getPropertyName() );
        } else {
            String qualifiedType = getQualifiedClassName( identityInfo.getType() );
            String identityPropertyClass = String.format( "%s<%s, %s>", AbstractIdentitySerializationInfo.class.getName(), type
                .getParameterizedQualifiedSourceName(), qualifiedType );

            source.println( "new %s(%s, \"%s\") {", identityPropertyClass, identityInfo.isAlwaysAsId(), identityInfo.getPropertyName() );
            source.indent();

            source.println( "@Override" );
            source
                .println( "protected %s<%s> newSerializer(%s ctx) {", JSON_SERIALIZER_CLASS, qualifiedType,
                    JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", getJsonSerializerFromType( identityInfo.getType() ) );
            source.outdent();
            source.println( "}" );
            source.println();

            Optional<AdditionalMethod> additionalMethod = Optional.absent();

            source.println( "@Override" );
            source.println( "public %s<%s> getObjectId(%s bean, %s ctx) {", ObjectIdSerializer.class.getName(), qualifiedType, type
                .getParameterizedQualifiedSourceName(), JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();

            String generatorType = String.format( "%s<%s>", ObjectIdGenerator.class.getName(), qualifiedType );
            source.println( "%s generator = new %s().forScope(%s.class);", generatorType, identityInfo.getGenerator()
                .getCanonicalName(), identityInfo.getScope().getName() );
            source.println( "%s scopedGen = ctx.findObjectIdGenerator(generator);", generatorType );
            source.println( "if(null == scopedGen) {" );
            source.indent();
            source.println( "scopedGen = generator.newForSerialization(ctx);" );
            source.println( "ctx.addGenerator(scopedGen);" );
            source.outdent();
            source.println( "}" );
            source.println( "return new %s<%s>(scopedGen.generateId(bean), getSerializer(ctx));", ObjectIdSerializer.class
                .getName(), qualifiedType );

            source.outdent();
            source.println( "}" );

            if ( additionalMethod.isPresent() ) {
                source.println();
                additionalMethod.get().write( source );
            }

            source.outdent();
            source.print( "}" );
        }
    }

    /**
     * Build the string that instantiate a {@link KeySerializer} for the given type.
     *
     * @param type type
     *
     * @return the code instantiating the {@link KeySerializer}.
     */
    protected String getKeySerializerFromType( JType type ) throws UnableToCompleteException {
        Optional<String> keySerializer = configuration.getKeySerializer( type );
        if ( keySerializer.isPresent() ) {
            return keySerializer.get();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return String.format( "ctx.<%s>getEnumKeySerializer()", enumType.getQualifiedSourceName() );
        }

        logger.log( TreeLogger.Type.ERROR, "Type '" + type.getQualifiedSourceName() + "' is not supported as map's key" );
        throw new UnableToCompleteException();
    }

    /**
     * Build the string that instantiate a {@link JsonDeserializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonDeserializer} will
     * be created.
     *
     * @param type type
     *
     * @return the code instantiating the deserializer. Examples:
     *         <ul>
     *         <li>ctx.getIntegerDeserializer()</li>
     *         <li>new org .PersonBeanJsonDeserializer()</li>
     *         </ul>
     */
    protected String getJsonDeserializerFromType( JType type ) throws UnableToCompleteException {
        return getJsonDeserializerFromType( type, null );
    }

    /**
     * Build the string that instantiate a {@link JsonDeserializer} for the given type. If the type is a bean,
     * the implementation of {@link AbstractBeanJsonDeserializer} will
     * be created.
     *
     * @param type type
     * @param propertyInfo additionnal info to gives to the deserializer
     *
     * @return the code instantiating the deserializer. Examples:
     *         <ul>
     *         <li>ctx.getIntegerDeserializer()</li>
     *         <li>new org.PersonBeanJsonDeserializer()</li>
     *         </ul>
     */
    protected String getJsonDeserializerFromType( JType type, PropertyInfo propertyInfo ) throws UnableToCompleteException {
        JTypeParameter typeParameter = type.isTypeParameter();
        if ( null != typeParameter ) {
            return String.format( TYPE_PARAMETER_DESERIALIZER_FIELD_NAME, typeParameter.getOrdinal() );
        }

        Optional<MapperInstance> configuredDeserializer = configuration.getDeserializer( type );
        if ( configuredDeserializer.isPresent() ) {
            if ( null != type.isParameterized() ) {
                String[] params = new String[type.isParameterized().getTypeArgs().length];
                for ( int i = 0; i < params.length; i++ ) {
                    if ( MapperType.KEY_DESERIALIZER == configuredDeserializer.get().getParameters()[i] ) {
                        params[i] = getKeyDeserializerFromType( type.isParameterized().getTypeArgs()[i] );
                    } else {
                        params[i] = getJsonDeserializerFromType( type.isParameterized().getTypeArgs()[i], propertyInfo );
                    }
                }
                return String.format( configuredDeserializer.get().getInstanceCreation(), params );
            } else {
                return configuredDeserializer.get().getInstanceCreation();
            }
        }

        if ( typeOracle.isObject( type ) ) {
            logger.log( Type.ERROR, "java.lang.Object is not a supported type" );
            throw new UnableToCompleteException();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return "ctx.newEnumJsonDeserializer(" + enumType.getQualifiedSourceName() + ".class)";
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            String method = "ctx.newArrayJsonDeserializer(%s, %s)";
            String arrayCreator = "new " + ARRAY_CREATOR_CLASS + "<" + arrayType.getComponentType()
                .getParameterizedQualifiedSourceName() + ">(){\n" +
                "  @Override\n" +
                "  public " + arrayType.getParameterizedQualifiedSourceName() + " create( int length ) {\n" +
                "    return new " + arrayType.getComponentType().getParameterizedQualifiedSourceName() + "[length];\n" +
                "  }\n" +
                "}";
            return String.format( method, getJsonDeserializerFromType( arrayType.getComponentType(), propertyInfo ), arrayCreator );
        }

        JParameterizedType parameterizedType = type.isParameterized();
        if ( null != parameterizedType ) {
            if ( typeOracle.isEnumSet( parameterizedType ) ) {

                // EnumSet needs the enum class as parameter
                return String.format( "ctx.newEnumSetJsonDeserializer(%s.class, %s)", parameterizedType.getTypeArgs()[0]
                    .getQualifiedSourceName(), getJsonDeserializerFromType( parameterizedType.getTypeArgs()[0], propertyInfo ) );

            }

            if ( typeOracle.isEnumMap( parameterizedType ) ) {
                // EnumMap needs the enum class as parameter
                return String.format( "ctx.newEnumMapJsonDeserializer(%s.class, %s, %s)", parameterizedType.getTypeArgs()[0]
                    .getQualifiedSourceName(), getKeyDeserializerFromType( parameterizedType
                    .getTypeArgs()[0] ), getJsonDeserializerFromType( parameterizedType.getTypeArgs()[1], propertyInfo ) );
            }

            // other parameterized types are considered to be beans
        }

        JClassType classType = type.isClassOrInterface();
        if ( null != classType ) {
            // it's a bean
            JClassType baseClassType = classType;
            if ( null != parameterizedType ) {
                // it's a bean with generics, we create a deserializer based on generic type
                baseClassType = typeOracle.findGenericType( parameterizedType );
            }
            BeanJsonDeserializerCreator beanJsonDeserializerCreator = new BeanJsonDeserializerCreator( logger
                .branch( Type.DEBUG, "Creating deserializer for " + baseClassType
                    .getQualifiedSourceName() ), context, configuration, typeOracle );
            BeanJsonMapperInfo info = beanJsonDeserializerCreator.create( baseClassType );

            StringBuilder joinedTypeParameters = new StringBuilder();
            StringBuilder joinedTypeParameterDeserializers = new StringBuilder();
            if ( null != parameterizedType ) {
                joinedTypeParameters.append( '<' );
                for ( int i = 0; i < parameterizedType.getTypeArgs().length; i++ ) {
                    if ( i > 0 ) {
                        joinedTypeParameters.append( ", " );
                        joinedTypeParameterDeserializers.append( ", " );
                    }
                    JClassType argType = parameterizedType.getTypeArgs()[i];
                    joinedTypeParameters.append( argType.getParameterizedQualifiedSourceName() );
                    joinedTypeParameterDeserializers.append( getJsonDeserializerFromType( argType ) );
                }
                joinedTypeParameters.append( '>' );
            }

            String parameters = generateBeanJsonDeserializerParameters( info, propertyInfo );
            String sep = "";
            if ( joinedTypeParameterDeserializers.length() > 0 && !parameters.isEmpty() ) {
                sep = ", ";
            }

            return String.format( "new %s%s(%s%s%s)", info
                .getQualifiedDeserializerClassName(), joinedTypeParameters, joinedTypeParameterDeserializers, sep,
                generateBeanJsonDeserializerParameters( info, propertyInfo ) );
        }

        logger.log( TreeLogger.Type.ERROR, "Type '" + type.getQualifiedSourceName() + "' is not supported" );
        throw new UnableToCompleteException();
    }

    private String generateBeanJsonDeserializerParameters( BeanJsonMapperInfo info, PropertyInfo propertyInfo ) throws
        UnableToCompleteException {
        if ( null == propertyInfo || (!propertyInfo.getIdentityInfo().isPresent() && !propertyInfo.getTypeInfo().isPresent()) ) {
            return "";
        }

        StringSourceWriter sourceWriter = new StringSourceWriter();

        if ( propertyInfo.getIdentityInfo().isPresent() ) {
            generateIdentifierDeserializationInfo( sourceWriter, info.getBeanInfo(), propertyInfo.getIdentityInfo().get() );
        } else {
            sourceWriter.print( "null" );
        }

        sourceWriter.print( ", " );

        if ( propertyInfo.getTypeInfo().isPresent() ) {
            generateSuperclassInfo( sourceWriter, info.getBeanInfo(), propertyInfo.getTypeInfo(), false );
        } else {
            sourceWriter.print( "null" );
        }

        return sourceWriter.toString();
    }

    protected void generateIdentifierDeserializationInfo( SourceWriter source, BeanInfo beanInfo,
                                                          BeanIdentityInfo identityInfo ) throws UnableToCompleteException {
        if ( identityInfo.isIdABeanProperty() ) {

            source.println( "new %s<%s>(\"%s\", %s.class, %s.class)", PropertyIdentityDeserializationInfo.class.getName(), beanInfo
                .getType().getParameterizedQualifiedSourceName(), identityInfo.getPropertyName(), identityInfo.getGenerator()
                .getCanonicalName(), identityInfo.getScope().getCanonicalName() );

        } else {

            String qualifiedType = getQualifiedClassName( identityInfo.getType() );

            String identityPropertyClass = String.format( "%s<%s, %s>", AbstractIdentityDeserializationInfo.class.getName(), beanInfo
                .getType().getParameterizedQualifiedSourceName(), qualifiedType );

            source.println( "new %s(\"%s\", %s.class, %s.class) {", identityPropertyClass, identityInfo.getPropertyName(), identityInfo
                .getGenerator().getCanonicalName(), identityInfo.getScope().getCanonicalName() );
            source.indent();

            source.println( "@Override" );
            source
                .println( "protected %s<%s> newDeserializer(%s ctx) {", JSON_DESERIALIZER_CLASS, qualifiedType,
                    JSON_DESERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", getJsonDeserializerFromType( identityInfo.getType() ) );
            source.outdent();
            source.println( "}" );
            source.println();

            source.outdent();
            source.print( "}" );
        }
    }

    /**
     * Build the string that instantiate a {@link KeyDeserializer} for the given type.
     *
     * @param type type
     *
     * @return the code instantiating the {@link KeyDeserializer}.
     */
    protected String getKeyDeserializerFromType( JType type ) throws UnableToCompleteException {
        Optional<String> keyDeserializer = configuration.getKeyDeserializer( type );
        if ( keyDeserializer.isPresent() ) {
            return keyDeserializer.get();
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return String.format( "ctx.newEnumKeyDeserializer(%s.class)", enumType.getQualifiedSourceName() );
        }

        logger.log( TreeLogger.Type.ERROR, "Type '" + type.getQualifiedSourceName() + "' is not supported as map's key" );
        throw new UnableToCompleteException();
    }

    protected void generateSuperclassInfo( SourceWriter source, BeanInfo beanInfo, Optional<BeanTypeInfo> typeInfo,
                                           boolean serialization ) throws UnableToCompleteException {
        source.print( "new %s(", serialization ? SUPERCLASS_SERIALIZATION_INFO_CLASS : SUPERCLASS_DESERIALIZATION_INFO_CLASS );
        // gives the information about how to read and write the type info
        if ( typeInfo.isPresent() ) {
            String typeInfoProperty = null;
            if ( null != typeInfo.get().getPropertyName() ) {
                typeInfoProperty = QUOTED_FUNCTION.apply( typeInfo.get().getPropertyName() );
            }
            source.print( "com.fasterxml.jackson.annotation.JsonTypeInfo.As.%s, %s", typeInfo.get().getInclude(), typeInfoProperty );
        }
        source.println( ")" );
        source.indent();

        generateSubtypeMappers( source, beanInfo, typeInfo, serialization );

        source.outdent();
    }

    private void generateSubtypeMappers( SourceWriter source, BeanInfo beanInfo, Optional<BeanTypeInfo> typeInfo,
                                         boolean serialization ) throws UnableToCompleteException {
        if ( beanInfo.getCreatorMethod().isPresent() ) {
            generateSubtypeMapper( source, typeInfo, beanInfo.getType(), serialization );
        }
        for ( JClassType subtype : beanInfo.getType().getSubtypes() ) {
            generateSubtypeMapper( source, typeInfo, subtype, serialization );
        }
    }

    private void generateSubtypeMapper( SourceWriter source, Optional<BeanTypeInfo> typeInfo, JClassType subtype,
                                        boolean serialization ) throws UnableToCompleteException {
        String typeMetadata;
        if ( !typeInfo.isPresent() ) {
            typeMetadata = null;
        } else {
            typeMetadata = QUOTED_FUNCTION.apply( typeInfo.get().getMapTypeToMetadata().get( subtype ) );
        }

        String suffix;
        String subtypeClass;
        String mapper;
        String mapperClass;
        String contextClass;
        if ( serialization ) {
            suffix = "Serializer";
            subtypeClass = SUBTYPE_SERIALIZER_CLASS;
            mapper = getJsonSerializerFromType( subtype );
            mapperClass = ABSTRACT_BEAN_JSON_SERIALIZER_CLASS;
            contextClass = JSON_SERIALIZATION_CONTEXT_CLASS;
        } else {
            suffix = "Deserializer";
            subtypeClass = SUBTYPE_DESERIALIZER_CLASS;
            mapper = getJsonDeserializerFromType( subtype );
            mapperClass = ABSTRACT_BEAN_JSON_DESERIALIZER_CLASS;
            contextClass = JSON_DESERIALIZATION_CONTEXT_CLASS;
        }

        source.println( ".addSubtype%s( new %s<%s>() {", suffix, subtypeClass, subtype.getQualifiedSourceName() );
        source.indent();
        source.indent();

        source.println( "@Override" );
        source.println( "public %s<%s> new%s(%s ctx) {", mapperClass, subtype.getQualifiedSourceName(), suffix, contextClass );
        source.indent();
        source.println( "return %s;", mapper );
        source.outdent();
        source.println( "}" );

        source.outdent();
        source.println( "}, %s.class, %s )", subtype.getQualifiedSourceName(), typeMetadata );
        source.println();

        source.outdent();
    }

}
