package com.github.nmorel.gwtjackson.rebind;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.deser.bean.AbstractBeanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.ObjectIdSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JEnumType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.user.rebind.AbstractSourceCreator;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractCreator extends AbstractSourceCreator {

    public static final List<String> BASE_TYPES = Arrays
        .asList( "java.math.BigDecimal", "java.math.BigInteger", "java.lang.Boolean", "java.lang.Byte", "java.lang.Character",
            "java.util.Date", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short",
            "java.sql.Date", "java.sql.Time", "java.sql.Timestamp", "java.lang.String", "java.util.UUID" );

    public static final String BEAN_INSTANCE_NAME = "$$instance$$";

    public static final String IS_SET_FORMAT = "is_%s_set";

    public static final String BUILDER_DESERIALIZER_FORMAT = "mapper_%s";

    public static final String JSON_DESERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.JsonDeserializer";

    public static final String JSON_SERIALIZER_CLASS = "com.github.nmorel.gwtjackson.client.JsonSerializer";

    public static final String JSON_READER_CLASS = "com.github.nmorel.gwtjackson.client.stream.JsonReader";

    public static final String JSON_WRITER_CLASS = "com.github.nmorel.gwtjackson.client.stream.JsonWriter";

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

    /**
     * Returns the String represention of the java type for a primitive for example int/Integer, float/Float, char/Character.
     *
     * @param type primitive type
     *
     * @return the string representation
     */
    protected static String getJavaObjectTypeFor( JPrimitiveType type ) {
        if ( type == JPrimitiveType.INT ) {
            return "Integer";
        } else if ( type == JPrimitiveType.CHAR ) {
            return "Character";
        } else {
            String s = type.getSimpleSourceName();
            return s.substring( 0, 1 ).toUpperCase() + s.substring( 1 );
        }
    }

    protected final TreeLogger logger;

    protected final GeneratorContext context;

    protected final JacksonTypeOracle typeOracle;

    protected AbstractCreator( TreeLogger logger, GeneratorContext context ) {
        this( logger, context, new JacksonTypeOracle( logger, context.getTypeOracle() ) );
    }

    protected AbstractCreator( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle ) {
        this.logger = logger;
        this.context = context;
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

        JPrimitiveType primitiveType = type.isPrimitive();
        if ( null != primitiveType ) {
            String boxedName = getJavaObjectTypeFor( primitiveType );
            return "ctx.get" + boxedName + "JsonSerializer()";
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return String.format( "ctx.<%s>getEnumJsonSerializer()", enumType.getQualifiedSourceName() );
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            if ( null != arrayType.getComponentType().isPrimitive() ) {
                String boxedName = getJavaObjectTypeFor( arrayType.getComponentType().isPrimitive() );
                return "ctx.getPrimitive" + boxedName + "ArrayJsonSerializer()";
            } else {
                return String.format( "ctx.newArrayJsonSerializer(%s)", getJsonSerializerFromType( arrayType
                    .getComponentType(), propertyInfo ) );
            }
        }

        JParameterizedType parameterizedType = type.isParameterized();
        if ( null != parameterizedType ) {

            if ( typeOracle.isIterable( parameterizedType ) ) {

                // Iterable and Collection serializer
                String serializer = getJsonSerializerFromType( parameterizedType.getTypeArgs()[0], propertyInfo );
                return String.format( "ctx.new%sJsonSerializer(%s)", parameterizedType.getSimpleSourceName(), serializer );

            } else if ( typeOracle.isMap( parameterizedType ) ) {

                // Map serializer
                String keySerializer = getKeySerializerFromType( parameterizedType.getTypeArgs()[0] );
                String valueSerializer = getJsonSerializerFromType( parameterizedType.getTypeArgs()[1], propertyInfo );
                return String.format( "ctx.new%sJsonSerializer(%s, %s)", parameterizedType
                    .getSimpleSourceName(), keySerializer, valueSerializer );

            }

            // other parameterized types are considered to be beans
        }

        JClassType classType = type.isClassOrInterface();
        if ( null != classType ) {
            String qualifiedSourceName = classType.getQualifiedSourceName();
            if ( BASE_TYPES.contains( qualifiedSourceName ) ) {
                if ( qualifiedSourceName.startsWith( "java.sql" ) ) {
                    return "ctx.getSql" + classType.getSimpleSourceName() + "JsonSerializer()";
                } else {
                    return "ctx.get" + classType.getSimpleSourceName() + "JsonSerializer()";
                }
            }

            // it's a bean
            JClassType baseClassType = classType;
            if ( null != parameterizedType ) {
                // it's a bean with generics, we create a deserializer based on generic type
                baseClassType = typeOracle.findGenericType( parameterizedType );
            }
            BeanJsonSerializerCreator beanJsonSerializerCreator = new BeanJsonSerializerCreator( logger
                .branch( TreeLogger.Type.INFO, "Creating serializer for " + baseClassType.getQualifiedSourceName() ), context, typeOracle );
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
        if ( null == propertyInfo || (null == propertyInfo.getIdentityInfo()) ) {
            return "";
        }

        StringSourceWriter sourceWriter = new StringSourceWriter();

        if ( null == propertyInfo.getIdentityInfo() ) {
            sourceWriter.print( "null" );
        } else {
            findIdPropertyInfo( info.getProperties(), propertyInfo.getIdentityInfo() );
            generateIdentifierSerializationInfo( sourceWriter, type, propertyInfo.getIdentityInfo() );
        }

        sourceWriter.print( ", " );

        // TODO subtype info on property
        sourceWriter.print( "null" );

        return sourceWriter.toString();
    }

    protected void generateIdentifierSerializationInfo( SourceWriter source, JClassType type, BeanIdentityInfo identityInfo ) throws
        UnableToCompleteException {
        String qualifiedType = getQualifiedClassName( identityInfo.getType() );

        String identityPropertyClass = String.format( "%s<%s, %s>", IDENTITY_SERIALIZATION_INFO_CLASS, type
            .getParameterizedQualifiedSourceName(), qualifiedType );

        source.println( "new %s(%s, \"%s\") {", identityPropertyClass, identityInfo.isAlwaysAsId(), identityInfo.getPropertyName() );
        source.indent();

        source.println();
        source.println( "@Override" );
        source
            .println( "protected %s<%s> newSerializer(%s ctx) {", JSON_SERIALIZER_CLASS, qualifiedType, JSON_SERIALIZATION_CONTEXT_CLASS );
        source.indent();
        source.println( "return %s;", getJsonSerializerFromType( identityInfo.getType() ) );
        source.outdent();
        source.println( "}" );
        source.println();

        source.println( "@Override" );
        source.println( "public %s<%s> getObjectId(%s bean, %s ctx) {", ObjectIdSerializer.class.getName(), qualifiedType, type
            .getParameterizedQualifiedSourceName(), JSON_SERIALIZATION_CONTEXT_CLASS );
        source.indent();
        if ( null == identityInfo.getProperty() ) {
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
        } else {
            source.println( "return new %s<%s>(%s, getSerializer(ctx));", ObjectIdSerializer.class.getName(), qualifiedType, identityInfo
                .getProperty().getGetterAccessor() );
        }
        source.outdent();
        source.println( "}" );
        source.println();

        source.outdent();
        source.println( "}" );
    }

    /**
     * Build the string that instantiate a {@link KeySerializer} for the given type.
     *
     * @param type type
     *
     * @return the code instantiating the {@link KeySerializer}.
     */
    protected String getKeySerializerFromType( JType type ) throws UnableToCompleteException {
        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return String.format( "ctx.<%s>getEnumKeySerializer()", enumType.getQualifiedSourceName() );
        }

        JClassType classType = type.isClass();
        if ( null != classType && BASE_TYPES.contains( classType.getQualifiedSourceName() ) ) {
            if ( classType.getQualifiedSourceName().startsWith( "java.sql" ) ) {
                return String.format( "ctx.getSql%sKeySerializer()", classType.getSimpleSourceName() );
            } else {
                return String.format( "ctx.get%sKeySerializer()", classType.getSimpleSourceName() );
            }
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
     *         <li>new org .PersonBeanJsonDeserializer()</li>
     *         </ul>
     */
    protected String getJsonDeserializerFromType( JType type, PropertyInfo propertyInfo ) throws UnableToCompleteException {
        JTypeParameter typeParameter = type.isTypeParameter();
        if ( null != typeParameter ) {
            return String.format( TYPE_PARAMETER_DESERIALIZER_FIELD_NAME, typeParameter.getOrdinal() );
        }

        JPrimitiveType primitiveType = type.isPrimitive();
        if ( null != primitiveType ) {
            String boxedName = getJavaObjectTypeFor( primitiveType );
            return "ctx.get" + boxedName + "JsonDeserializer()";
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return "ctx.newEnumJsonDeserializer(" + enumType.getQualifiedSourceName() + ".class)";
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType ) {
            if ( null != arrayType.getComponentType().isPrimitive() ) {
                String boxedName = getJavaObjectTypeFor( arrayType.getComponentType().isPrimitive() );
                return "ctx.getPrimitive" + boxedName + "ArrayJsonDeserializer()";
            } else {
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
        }

        JParameterizedType parameterizedType = type.isParameterized();
        if ( null != parameterizedType ) {

            if ( typeOracle.isIterable( parameterizedType ) ) {

                // Iterable and Collection deserializer
                String deserializer = getJsonDeserializerFromType( parameterizedType.getTypeArgs()[0], propertyInfo );

                if ( typeOracle.isEnumSet( parameterizedType ) ) {
                    // EnumSet needs the enum class as parameter
                    return String.format( "ctx.newEnumSetJsonDeserializer(%s.class, %s)", parameterizedType.getTypeArgs()[0]
                        .getQualifiedSourceName(), deserializer );
                } else {
                    return String.format( "ctx.new%sJsonDeserializer(%s)", parameterizedType.getSimpleSourceName(), deserializer );
                }

            } else if ( typeOracle.isMap( parameterizedType ) ) {

                // Map deserializer
                String keyDeserializer = getKeyDeserializerFromType( parameterizedType.getTypeArgs()[0] );
                String valueDeserializer = getJsonDeserializerFromType( parameterizedType.getTypeArgs()[1], propertyInfo );

                if ( typeOracle.isEnumMap( parameterizedType ) ) {
                    // EnumMap needs the enum class as parameter
                    return String.format( "ctx.newEnumMapJsonDeserializer(%s.class, %s, %s)", parameterizedType.getTypeArgs()[0]
                        .getQualifiedSourceName(), keyDeserializer, valueDeserializer );
                } else {
                    return String.format( "ctx.new%sJsonDeserializer(%s, %s)", parameterizedType
                        .getSimpleSourceName(), keyDeserializer, valueDeserializer );
                }

            }

            // other parameterized types are considered to be beans
        }

        JClassType classType = type.isClassOrInterface();
        if ( null != classType ) {
            String qualifiedSourceName = classType.getQualifiedSourceName();
            if ( BASE_TYPES.contains( qualifiedSourceName ) ) {
                if ( qualifiedSourceName.startsWith( "java.sql" ) ) {
                    return "ctx.getSql" + classType.getSimpleSourceName() + "JsonDeserializer()";
                } else {
                    return "ctx.get" + classType.getSimpleSourceName() + "JsonDeserializer()";
                }
            }

            // it's a bean
            JClassType baseClassType = classType;
            if ( null != parameterizedType ) {
                // it's a bean with generics, we create a deserializer based on generic type
                baseClassType = typeOracle.findGenericType( parameterizedType );
            }
            BeanJsonDeserializerCreator beanJsonDeserializerCreator = new BeanJsonDeserializerCreator( logger
                .branch( TreeLogger.Type.INFO, "Creating deserializer for " + baseClassType
                    .getQualifiedSourceName() ), context, typeOracle );
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
        if ( null == propertyInfo || (null == propertyInfo.getIdentityInfo()) ) {
            return "";
        }

        StringSourceWriter sourceWriter = new StringSourceWriter();

        if ( null == propertyInfo.getIdentityInfo() ) {
            sourceWriter.print( "null" );
        } else {
            findIdPropertyInfo( info.getProperties(), propertyInfo.getIdentityInfo() );
            generateIdentifierDeserializationInfo( sourceWriter, propertyInfo.getIdentityInfo() );
        }

        sourceWriter.print( ", " );

        // TODO subtype info on property
        sourceWriter.print( "null" );

        return sourceWriter.toString();
    }

    protected void generateIdentifierDeserializationInfo( SourceWriter source, BeanIdentityInfo identityInfo ) throws
        UnableToCompleteException {
        String qualifiedType = getQualifiedClassName( identityInfo.getType() );

        String identityPropertyClass = String.format( "%s<%s>", IDENTITY_DESERIALIZATION_INFO_CLASS, qualifiedType );

        source.println( "new %s(\"%s\", %s.class, %s.class) {", identityPropertyClass, identityInfo.getPropertyName(), identityInfo
            .getGenerator().getCanonicalName(), identityInfo.getScope().getCanonicalName() );
        source.indent();

        source.println();
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
        source.println( "}" );
    }

    protected void findIdPropertyInfo( Map<String, PropertyInfo> properties, BeanIdentityInfo identityInfo ) throws
        UnableToCompleteException {
        if ( null != identityInfo && identityInfo.isIdABeanProperty() ) {
            PropertyInfo property = properties.get( identityInfo.getPropertyName() );
            if ( null == property ) {
                logger.log( Type.ERROR, "Cannot find the property with the name '" + identityInfo
                    .getPropertyName() + "' used for identity" );
                throw new UnableToCompleteException();
            }
            identityInfo.setProperty( property );
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
        JEnumType enumType = type.isEnum();
        if ( null != enumType ) {
            return String.format( "ctx.newEnumKeyDeserializer(%s.class)", enumType.getQualifiedSourceName() );
        }

        JClassType classType = type.isClass();
        if ( null != classType && BASE_TYPES.contains( classType.getQualifiedSourceName() ) ) {
            if ( classType.getQualifiedSourceName().startsWith( "java.sql" ) ) {
                return String.format( "ctx.getSql%sKeyDeserializer()", classType.getSimpleSourceName() );
            } else {
                return String.format( "ctx.get%sKeyDeserializer()", classType.getSimpleSourceName() );
            }
        }

        logger.log( TreeLogger.Type.ERROR, "Type '" + type.getQualifiedSourceName() + "' is not supported as map's key" );
        throw new UnableToCompleteException();
    }
}
