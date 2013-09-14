package com.github.nmorel.gwtjackson.rebind;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
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

/** @author Nicolas Morel */
public abstract class AbstractJsonMapperCreator extends AbstractSourceCreator
{
    public static final List<String> BASE_TYPES = Arrays
        .asList( "java.math.BigDecimal", "java.math.BigInteger", "java.lang.Boolean", "java.lang.Byte", "java.lang.Character",
            "java.util.Date", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short",
            "java.sql.Date", "java.sql.Time", "java.sql.Timestamp", "java.lang.String" );
    public static final String BEAN_INSTANCE_NAME = "$$instance$$";
    public static final String IS_SET_FORMAT = "is_%s_set";
    public static final String BUILDER_MAPPER_FORMAT = "mapper_%s";
    public static final String JSON_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.JsonMapper";
    public static final String ABSTRACT_JSON_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.AbstractJsonMapper";
    public static final String ABSTRACT_BEAN_JSON_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper";
    public static final String INSTANCE_BUILDER_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" + "" +
        ".InstanceBuilder";
    public static final String DECODER_PROPERTY_BEAN_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" + "" +
        ".DecoderProperty";
    public static final String ENCODER_PROPERTY_BEAN_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" + "" +
        ".EncoderProperty";
    public static final String BACK_REFERENCE_PROPERTY_BEAN_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper" +
        ".BackReferenceProperty";
    public static final String JSON_READER_CLASS = "com.github.nmorel.gwtjackson.client.stream.JsonReader";
    public static final String JSON_WRITER_CLASS = "com.github.nmorel.gwtjackson.client.stream.JsonWriter";
    public static final String JSON_MAPPING_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonMappingContext";
    public static final String JSON_DECODING_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonDecodingContext";
    public static final String JSON_ENCODING_CONTEXT_CLASS = "com.github.nmorel.gwtjackson.client.JsonEncodingContext";
    public static final String JSON_DECODING_EXCEPTION_CLASS = "com.github.nmorel.gwtjackson.client.exception.JsonDecodingException";
    public static final String JSON_ENCODING_EXCEPTION_CLASS = "com.github.nmorel.gwtjackson.client.exception.JsonEncodingException";
    public static final String ARRAY_CREATOR_CLASS = "com.github.nmorel.gwtjackson.client.mapper.array.ArrayJsonMapper.ArrayCreator";
    public static final String ABSTRACT_SUPERCLASS_JSON_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.mapper" + "" +
        ".AbstractSuperclassJsonMapper";
    public static final String SUBTYPE_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.mapper.AbstractSuperclassJsonMapper" + "" +
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
    protected final Map<JType, String> typeToMapper;

    protected AbstractJsonMapperCreator( TreeLogger logger, GeneratorContext context )
    {
        this( logger, context, new JacksonTypeOracle( logger, context.getTypeOracle() ), new HashMap<JType, String>() );
    }

    /** @param typeToMapper Map that stores all the JsonMapper we found by type. */
    protected AbstractJsonMapperCreator( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle, Map<JType,
        String> typeToMapper )
    {
        this.logger = logger;
        this.context = context;
        this.typeOracle = typeOracle;
        this.typeToMapper = typeToMapper;
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

    protected String getMapperFromType( JType type ) throws UnableToCompleteException
    {
        String mapper = typeToMapper.get( type );
        if ( null == mapper )
        {
            mapper = createMapperFromType( type );
            typeToMapper.put( type, mapper );
        }
        return mapper;
    }

    private String createMapperFromType( JType type ) throws UnableToCompleteException
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
                return String.format( method, getMapperFromType( arrayType.getComponentType() ), arrayCreator );
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
                mappers[i] = getMapperFromType( args[i] );
            }

            return String.format( result, mappers );
        }

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

            // it's a bean, we create the mapper
            BeanJsonMapperCreator beanJsonMapperCreator = new BeanJsonMapperCreator( logger
                .branch( TreeLogger.Type.INFO, "Creating mapper for " + classType
                    .getQualifiedSourceName() ), context, typeOracle, typeToMapper );
            String name = beanJsonMapperCreator.create( classType );
            return "new " + name + "()";
        }

        logger.log( TreeLogger.Type.ERROR, "Type '" + type.getQualifiedSourceName() + "' is not supported" );
        throw new UnableToCompleteException();
    }
}
