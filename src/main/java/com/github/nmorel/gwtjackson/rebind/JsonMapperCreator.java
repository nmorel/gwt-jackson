package com.github.nmorel.gwtjackson.rebind;

import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.user.rebind.SourceWriter;

/** @author Nicolas Morel */
public class JsonMapperCreator extends AbstractJsonMapperCreator
{

    public JsonMapperCreator( TreeLogger logger, GeneratorContext context )
    {
        super( logger, context );
    }

    /**
     * Creates the implementation of the interface denoted by typeName and extending {@link JsonMapper}
     *
     * @param interfaceName name of the interface
     * @return the fully qualified name of the created class
     * @throws UnableToCompleteException
     */
    public String create( String interfaceName ) throws UnableToCompleteException
    {
        JClassType interfaceClass = typeOracle.getType( interfaceName );

        // we concatenate the name of all the enclosing class
        StringBuilder builder = new StringBuilder( interfaceClass.getSimpleSourceName() + "Impl" );
        JClassType enclosingType = interfaceClass.getEnclosingType();
        while ( null != enclosingType )
        {
            builder.insert( 0, enclosingType.getSimpleSourceName() + "_" );
            enclosingType = enclosingType.getEnclosingType();
        }

        String mapperClassSimpleName = builder.toString();
        String packageName = interfaceClass.getPackage().getName();
        String qualifiedMapperClassName = packageName + "." + mapperClassSimpleName;

        // Extract the type of the object to map
        JClassType mappedTypeClass = getMappedType( interfaceClass );

        SourceWriter source = getSourceWriter( packageName, mapperClassSimpleName, ABSTRACT_JSON_MAPPER_CLASS + "<" +
            mappedTypeClass.getParameterizedQualifiedSourceName() + ">", interfaceName );

        // the class already exists, no need to continue
        if ( source == null )
        {
            return qualifiedMapperClassName;
        }

        writeClassBody( source, mappedTypeClass );

        return qualifiedMapperClassName;
    }

    private JClassType getMappedType( JClassType interfaceClass ) throws UnableToCompleteException
    {
        JClassType intf = interfaceClass.isInterface();
        if ( intf == null )
        {
            logger.log( TreeLogger.Type.ERROR, "Expected " + interfaceClass + " to be an interface." );
            throw new UnableToCompleteException();
        }

        JClassType[] intfs = intf.getImplementedInterfaces();
        for ( JClassType t : intfs )
        {
            if ( t.getQualifiedSourceName().equals( JSON_MAPPER_CLASS ) )
            {
                JParameterizedType genericType = t.isParameterized();
                if ( genericType == null )
                {
                    logger.log( TreeLogger.Type.ERROR, "Expected the " + JSON_MAPPER_CLASS + " declaration to specify a " +
                        "parameterized type." );
                    throw new UnableToCompleteException();
                }
                JClassType[] typeParameters = genericType.getTypeArgs();
                if ( typeParameters == null || typeParameters.length != 1 )
                {
                    logger.log( TreeLogger.Type.ERROR, "Expected the " + JSON_MAPPER_CLASS + " declaration to specify 1 " +
                        "parameterized type." );
                    throw new UnableToCompleteException();
                }
                return typeParameters[0];
            }
        }
        logger.log( TreeLogger.Type.ERROR, "Expected  " + interfaceClass + " to extend the " + JSON_MAPPER_CLASS + " interface." );
        throw new UnableToCompleteException();
    }

    private void writeClassBody( SourceWriter source, JClassType mappedTypeClass ) throws UnableToCompleteException
    {
        source.println();
        source.indent();

        source.println( "@Override" );
        source.println( "protected %s doDecode(%s reader, %s ctx) throws %s {", mappedTypeClass
            .getParameterizedQualifiedSourceName(), JSON_READER_CLASS, JSON_DECODING_CONTEXT_CLASS, JSON_DECODING_EXCEPTION_CLASS );
        source.indent();
        generateDecodeBody( source, mappedTypeClass );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "protected void doEncode(%s writer, %s value, %s ctx) throws %s {", JSON_WRITER_CLASS, mappedTypeClass
            .getParameterizedQualifiedSourceName(), JSON_ENCODING_CONTEXT_CLASS, JSON_ENCODING_EXCEPTION_CLASS );
        source.indent();
        generateEncodeBody( source, mappedTypeClass );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "private %s<%s> getMapper(%s ctx) {", JSON_MAPPER_CLASS, mappedTypeClass
            .getParameterizedQualifiedSourceName(), JSON_MAPPING_CONTEXT_CLASS );
        source.indent();
        generateGetMapperBody( source, mappedTypeClass );
        source.outdent();
        source.println( "}" );

        source.println();

        source.commit( logger );
    }

    private void generateEncodeBody( SourceWriter source, JClassType mappedTypeClass )
    {
        source.println( "getMapper(ctx).encode( writer, value, ctx );" );
    }

    private void generateDecodeBody( SourceWriter source, JClassType mappedTypeClass )
    {
        source.println( "return getMapper(ctx).decode( reader, ctx );" );
    }

    private void generateGetMapperBody( SourceWriter source, JClassType mappedTypeClass ) throws UnableToCompleteException
    {
        source.println( "return %s;", createMapperFromType( mappedTypeClass ) );
    }
}
