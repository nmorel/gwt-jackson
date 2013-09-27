package com.github.nmorel.gwtjackson.rebind;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.utils.ObjectIdEncoder;
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
import com.google.gwt.user.rebind.AbstractSourceCreator;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;

/** @author Nicolas Morel */
public abstract class AbstractJsonMapperCreator extends AbstractSourceCreator
{
    public static final List<String> BASE_TYPES = Arrays
        .asList( "java.math.BigDecimal", "java.math.BigInteger", "java.lang.Boolean", "java.lang.Byte", "java.lang.Character",
            "java.util.Date", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short",
            "java.sql.Date", "java.sql.Time", "java.sql.Timestamp", "java.lang.String", "java.util.UUID" );
    public static final String BEAN_INSTANCE_NAME = "$$instance$$";
    public static final String IS_SET_FORMAT = "is_%s_set";
    public static final String BUILDER_MAPPER_FORMAT = "mapper_%s";
    public static final String JSON_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.JsonMapper";
    public static final String ABSTRACT_JSON_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.AbstractJsonMapper";
    public static final String ABSTRACT_BEAN_JSON_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper";
    public static final String INSTANCE_BUILDER_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" + "" +
        ".InstanceBuilder";
    public static final String INSTANCE_BUILDER_CALLBACK_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" + "" +
        ".InstanceBuilderCallback";
    public static final String DECODER_PROPERTY_BEAN_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" + "" +
        ".DecoderProperty";
    public static final String ENCODER_PROPERTY_BEAN_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" + "" +
        ".EncoderProperty";
    public static final String BACK_REFERENCE_PROPERTY_BEAN_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" +
        ".BackReferenceProperty";
    public static final String IDENTITY_PROPERTY_BEAN_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" + "" +
        ".IdProperty";
    public static final String JSON_READER_CLASS = "com.github.nmorel.gwtjackson.client.stream.JsonReader";
    public static final String JSON_WRITER_CLASS = "com.github.nmorel.gwtjackson.client.stream.JsonWriter";
    public static final String JSON_MAPPING_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonMappingContext";
    public static final String JSON_DECODING_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonDecodingContext";
    public static final String JSON_ENCODING_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonEncodingContext";
    public static final String JSON_DECODING_EXCEPTION_CLASS = "com.github.nmorel.gwtjackson.client.exception.JsonDecodingException";
    public static final String JSON_ENCODING_EXCEPTION_CLASS = "com.github.nmorel.gwtjackson.client.exception.JsonEncodingException";
    public static final String ARRAY_CREATOR_CLASS = "com.github.nmorel.gwtjackson.client.mapper.array.ArrayJsonMapper.ArrayCreator";
    public static final String SUPERCLASS_INFO_CLASS = "com.github.nmorel.gwtjackson.client.mapper.SuperclassInfo";
    public static final String SUBTYPE_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.mapper.SuperclassInfo" + "" +
        ".SubtypeMapper";

    /**
     * Returns the String represention of the java type for a primitive for example int/Integer, float/Float, char/Character.
     *
     * @param type primitive type
     * @return the string representation
     */
    protected static String getJavaObjectTypeFor( JPrimitiveType type )
    {
        if ( type == JPrimitiveType.INT )
        {
            return "Integer";
        }
        else if ( type == JPrimitiveType.CHAR )
        {
            return "Character";
        }
        else
        {
            String s = type.getSimpleSourceName();
            return s.substring( 0, 1 ).toUpperCase() + s.substring( 1 );
        }
    }

    protected final TreeLogger logger;
    protected final GeneratorContext context;
    protected final JacksonTypeOracle typeOracle;

    protected AbstractJsonMapperCreator( TreeLogger logger, GeneratorContext context )
    {
        this( logger, context, new JacksonTypeOracle( logger, context.getTypeOracle() ) );
    }

    protected AbstractJsonMapperCreator( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle )
    {
        this.logger = logger;
        this.context = context;
        this.typeOracle = typeOracle;
    }

    protected PrintWriter getPrintWriter( String packageName, String className )
    {
        return context.tryCreate( logger, packageName, className );
    }

    protected SourceWriter getSourceWriter( PrintWriter printWriter, String packageName, String className, String superClass,
                                            String... interfaces )
    {
        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory( packageName, className );
        if ( null != superClass )
        {
            composer.setSuperclass( superClass );
        }
        for ( String interfaceName : interfaces )
        {
            composer.addImplementedInterface( interfaceName );
        }
        return composer.createSourceWriter( context, printWriter );
    }

    /**
     * Build the string that instantiate a mapper for the given type. If the type is a bean, the implementation of {@link com.github
     * .nmorel.gwtjackson.client.AbstractJsonMapper} will be created.
     *
     * @param type type
     * @return the code instantiating the mapper. Examples: <ul><li>ctx.getIntegerMapper()</li><li>new org.PersonBeanJsonMapper()</li></ul>
     */
    protected String getMapperFromType( JType type ) throws UnableToCompleteException
    {
        return getMapperFromType( type, null );
    }

    /**
     * Build the string that instantiate a mapper for the given type. If the type is a bean, the implementation of {@link com.github
     * .nmorel.gwtjackson.client.AbstractJsonMapper} will be created.
     *
     * @param type type
     * @param propertyInfo additionnal info to gives to the mapper
     * @return the code instantiating the mapper. Examples: <ul><li>ctx.getIntegerMapper()</li><li>new org.PersonBeanJsonMapper()</li></ul>
     */
    protected String getMapperFromType( JType type, PropertyInfo propertyInfo ) throws UnableToCompleteException
    {
        JPrimitiveType primitiveType = type.isPrimitive();
        if ( null != primitiveType )
        {
            String boxedName = getJavaObjectTypeFor( primitiveType );
            return "ctx.get" + boxedName + "JsonMapper()";
        }

        JEnumType enumType = type.isEnum();
        if ( null != enumType )
        {
            return "ctx.createEnumJsonMapper(" + enumType.getQualifiedSourceName() + ".class)";
        }

        JArrayType arrayType = type.isArray();
        if ( null != arrayType )
        {
            if ( null != arrayType.getComponentType().isPrimitive() )
            {
                String boxedName = getJavaObjectTypeFor( arrayType.getComponentType().isPrimitive() );
                return "ctx.getPrimitive" + boxedName + "ArrayJsonMapper()";
            }
            else
            {
                String method = "ctx.createArrayJsonMapper(%s, %s)";
                String arrayCreator = "new " + ARRAY_CREATOR_CLASS + "<" + arrayType.getComponentType()
                    .getParameterizedQualifiedSourceName() + ">(){\n" +
                    "  @Override\n" +
                    "  public " + arrayType.getParameterizedQualifiedSourceName() + " create( int length ) {\n" +
                    "    return new " + arrayType.getComponentType().getParameterizedQualifiedSourceName() + "[length];\n" +
                    "  }\n" +
                    "}";
                return String.format( method, getMapperFromType( arrayType.getComponentType(), propertyInfo ), arrayCreator );
            }
        }

        JParameterizedType parameterizedType = type.isParameterized();
        if ( null != parameterizedType )
        {
            String result;

            if ( typeOracle.isEnumSet( parameterizedType ) )
            {
                result = "ctx.createEnumSetJsonMapper(" + parameterizedType.getTypeArgs()[0].getQualifiedSourceName() + ".class, %s)";
            }
            else if ( typeOracle.isIterable( parameterizedType ) )
            {
                result = "ctx.create" + parameterizedType.getSimpleSourceName() + "JsonMapper(%s)";
            }
            else if ( typeOracle.isMap( parameterizedType ) )
            {
                // TODO add support for map
                logger.log( TreeLogger.Type.ERROR, "Map are not supported yet" );
                throw new UnableToCompleteException();
            }
            else
            {
                // TODO
                logger.log( TreeLogger.Type.ERROR, "Parameterized type '" + parameterizedType
                    .getQualifiedSourceName() + "' is not supported" );
                throw new UnableToCompleteException();
            }

            JClassType[] args = parameterizedType.getTypeArgs();
            String[] mappers = new String[args.length];
            for ( int i = 0; i < args.length; i++ )
            {
                mappers[i] = getMapperFromType( args[i], propertyInfo );
            }

            return String.format( result, mappers );
        }

        // TODO should we use isClassOrInterface ? need to add test for interface
        JClassType classType = type.isClass();
        if ( null != classType )
        {
            String qualifiedSourceName = classType.getQualifiedSourceName();
            if ( BASE_TYPES.contains( qualifiedSourceName ) )
            {
                if ( qualifiedSourceName.startsWith( "java.sql" ) )
                {
                    return "ctx.getSql" + classType.getSimpleSourceName() + "JsonMapper()";
                }
                else
                {
                    return "ctx.get" + classType.getSimpleSourceName() + "JsonMapper()";
                }
            }

            // it's a bean
            BeanJsonMapperInfo info = typeOracle.getBeanJsonMapperInfo( classType );
            if ( null == info )
            {
                BeanJsonMapperCreator beanJsonMapperCreator = new BeanJsonMapperCreator( logger
                    .branch( TreeLogger.Type.INFO, "Creating mapper for " + classType.getQualifiedSourceName() ), context, typeOracle );
                info = beanJsonMapperCreator.create( classType );
                typeOracle.addBeanJsonMapperInfo( classType, info );
            }
            return String.format( "new %s(%s)", info
                .getQualifiedMapperClassName(), generateBeanJsonMapperParameters( classType, info, propertyInfo ) );
        }

        logger.log( TreeLogger.Type.ERROR, "Type '" + type.getQualifiedSourceName() + "' is not supported" );
        throw new UnableToCompleteException();
    }

    private String generateBeanJsonMapperParameters( JClassType type, BeanJsonMapperInfo info,
                                                     PropertyInfo propertyInfo ) throws UnableToCompleteException
    {
        if ( null == propertyInfo || (null == propertyInfo.getIdentityInfo()) )
        {
            return "";
        }

        StringSourceWriter sourceWriter = new StringSourceWriter();

        if ( null == propertyInfo.getIdentityInfo() )
        {
            sourceWriter.print( "null" );
        }
        else
        {
            findIdPropertyInfo( info.getProperties(), propertyInfo.getIdentityInfo() );
            generateIdProperty( sourceWriter, type, propertyInfo.getIdentityInfo() );
        }

        sourceWriter.print( ", " );

        // TODO subtype info on property
        sourceWriter.print( "null" );

        return sourceWriter.toString();
    }

    protected String getQualifiedBoxedName( JType type )
    {
        if ( null != type.isPrimitive() )
        {
            return type.isPrimitive().getQualifiedBoxedSourceName();
        }
        else
        {
            return type.getParameterizedQualifiedSourceName();
        }
    }

    protected void generateIdProperty( SourceWriter source, JClassType type, BeanIdentityInfo identityInfo ) throws
        UnableToCompleteException
    {
        String qualifiedType = null != identityInfo.getType().isPrimitive() ? identityInfo.getType().isPrimitive()
            .getQualifiedBoxedSourceName() : identityInfo.getType().getParameterizedQualifiedSourceName();

        String identityPropertyClass = String.format( "%s<%s>", IDENTITY_PROPERTY_BEAN_CLASS, type.getParameterizedQualifiedSourceName() );

        source.println( "new %s() {", identityPropertyClass );
        source.indent();

        source.println();
        source.println( "private %s<%s> mapper;", JSON_MAPPER_CLASS, qualifiedType );
        source.println();
        source.println( "@Override" );
        source.println( "public %s<%s> getMapper(%s ctx) {", JSON_MAPPER_CLASS, qualifiedType, JSON_MAPPING_CONTEXT_CLASS );
        source.indent();
        source.println( "if(null == mapper) {" );
        source.indent();
        source.println( "mapper = %s;", getMapperFromType( identityInfo.getType() ) );
        source.outdent();
        source.println( "}" );
        source.println( "return mapper;" );
        source.outdent();
        source.println( "}" );
        source.println();

        source.println( "@Override" );
        source.println( "public boolean isAlwaysAsId() {" );
        source.indent();
        source.println( "return %s;", identityInfo.isAlwaysAsId() );
        source.outdent();
        source.println( "}" );
        source.println();

        source.println( "@Override" );
        source.println( "public String getPropertyName() {" );
        source.indent();
        source.println( "return \"%s\";", identityInfo.getPropertyName() );
        source.outdent();
        source.println( "}" );
        source.println();

        source.println( "@Override" );
        source.println( "public %s<%s> getObjectId( %s bean, %s ctx ) {", ObjectIdEncoder.class.getName(), qualifiedType, type
            .getParameterizedQualifiedSourceName(), JSON_ENCODING_CONTEXT_CLASS );
        source.indent();
        if ( null == identityInfo.getProperty() )
        {
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
            source.println( "return new %s<%s>(scopedGen.generateId(bean), getMapper(ctx));", ObjectIdEncoder.class
                .getName(), qualifiedType );
        }
        else
        {
            source.println( "return new %s<%s>(%s, getMapper(ctx));", ObjectIdEncoder.class.getName(), qualifiedType, identityInfo
                .getProperty().getGetterAccessor() );
        }
        source.outdent();
        source.println( "}" );
        source.println();

        source.println( "@Override" );
        source.println( "public %s getIdKey( %s reader, %s ctx ) {", IdKey.class
            .getCanonicalName(), JSON_READER_CLASS, JSON_DECODING_CONTEXT_CLASS );
        source.indent();
        source.println( "return newIdKey(getMapper(ctx).decode(reader, ctx));" );
        source.outdent();
        source.println( "}" );
        source.println();

        source.println( "@Override" );
        source.println( "public %s newIdKey( java.lang.Object id ) {", IdKey.class
            .getCanonicalName(), JSON_READER_CLASS, JSON_DECODING_CONTEXT_CLASS );
        source.indent();
        source.println( "return new %s(%s.class, %s.class, id);", IdKey.class.getCanonicalName(), identityInfo.getGenerator()
            .getCanonicalName(), identityInfo.getScope().getCanonicalName() );
        source.outdent();
        source.println( "}" );
        source.println();

        source.outdent();
        source.println( "}" );
    }

    protected void findIdPropertyInfo( Map<String, PropertyInfo> properties, BeanIdentityInfo identityInfo ) throws
        UnableToCompleteException
    {
        if ( null != identityInfo && identityInfo.isIdABeanProperty() )
        {
            PropertyInfo property = properties.get( identityInfo.getPropertyName() );
            if ( null == property )
            {
                logger.log( Type.ERROR, "Cannot find the property with the name '" + identityInfo
                    .getPropertyName() + "' used for identity" );
                throw new UnableToCompleteException();
            }
            identityInfo.setProperty( property );
        }
    }
}
