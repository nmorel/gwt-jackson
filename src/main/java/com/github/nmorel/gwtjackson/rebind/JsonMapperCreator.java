package com.github.nmorel.gwtjackson.rebind;

import java.io.PrintWriter;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.AbstractSourceCreator;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/** @author Nicolas Morel */
public class JsonMapperCreator extends AbstractSourceCreator
{
    private final TreeLogger logger;
    private final GeneratorContext context;
    private final JClassType typeClass;
    private final TypeOracle typeOracle;
    private final String mapperClassSimpleName;
    private final JClassType mappedTypeClass;

    public JsonMapperCreator( TreeLogger logger, GeneratorContext context, JClassType typeClass,
                              TypeOracle typeOracle ) throws UnableToCompleteException
    {
        this.logger = logger;
        this.context = context;
        this.typeClass = typeClass;
        this.typeOracle = typeOracle;

        StringBuilder builder = new StringBuilder( typeClass.getSimpleSourceName() + "Impl" );
        JClassType enclosingType = typeClass.getEnclosingType();
        while ( null != enclosingType )
        {
            builder.insert( 0, enclosingType.getSimpleSourceName() + "_" );
            enclosingType = enclosingType.getEnclosingType();
        }

        mapperClassSimpleName = builder.toString();

        mappedTypeClass = getMappedType();
    }

    public String create() throws UnableToCompleteException
    {
        String qualifiedMapperClassName = typeClass.getPackage().getName() + "." + mapperClassSimpleName;

        SourceWriter source = getSourceWriter( typeClass );
        // Si le wrapper existe déjà, getSourceWriter renvoie null
        // Il n'est donc pas nécessaire de créer cette classe
        if ( source == null )
        {
            return qualifiedMapperClassName;
        }

        source.println();
        source.indent();

        source.println( "@Override" );
        source.println( "public %s decode(%s reader) throws java.io.IOException {", mappedTypeClass
            .getQualifiedSourceName(), JsonReader.class.getName() );
        source.indent();
        generateDecodeBody( source );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "public void encode(%s writer, %s value) throws java.io.IOException {", JsonWriter.class.getName(), mappedTypeClass
            .getQualifiedSourceName() );
        source.indent();
        generateEncodeBody( source );
        source.outdent();
        source.println( "}" );

        source.println();

        source.commit( logger );

        return qualifiedMapperClassName;
    }

    private void generateEncodeBody( SourceWriter source )
    {
        source.println( "" );
    }

    private void generateDecodeBody( SourceWriter source )
    {
        source.println(  "%s result = new %s();", mappedTypeClass.getQualifiedSourceName(), mappedTypeClass.getQualifiedSourceName() );
        source.println( "return result;" );
    }

    private SourceWriter getSourceWriter( JClassType classType )
    {
        String packageName = classType.getPackage().getName();

        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory( packageName, mapperClassSimpleName );
        composer.setSuperclass( AbstractJsonMapper.class.getName() + "<" + mappedTypeClass.getQualifiedSourceName() + ">" );
        composer.addImplementedInterface( classType.getQualifiedSourceName() );

        PrintWriter printWriter = context.tryCreate( logger, packageName, mapperClassSimpleName );
        if ( printWriter == null )
        {
            return null;
        }
        else
        {
            return composer.createSourceWriter( context, printWriter );
        }
    }

    private JClassType getMappedType() throws UnableToCompleteException
    {
        JClassType intf = typeClass.isInterface();
        if ( intf == null )
        {
            logger.log( TreeLogger.Type.ERROR, "Expected " + typeClass + " to be an interface." );
            throw new UnableToCompleteException();
        }

        JClassType[] intfs = intf.getImplementedInterfaces();
        for ( JClassType t : intfs )
        {
            if ( t.getQualifiedSourceName().equals( JsonMapper.class.getName() ) )
            {
                JParameterizedType genericType = t.isParameterized();
                if ( genericType == null )
                {
                    logger.log( TreeLogger.Type.ERROR, "Expected the " + JsonMapper.class.getName() + " declaration to specify a " +
                        "parameterized type." );
                    throw new UnableToCompleteException();
                }
                JClassType[] typeParameters = genericType.getTypeArgs();
                if ( typeParameters == null || typeParameters.length != 1 )
                {
                    logger.log( TreeLogger.Type.ERROR, "Expected the " + JsonMapper.class.getName() + " declaration to specify 1 " +
                        "parameterized type." );
                    throw new UnableToCompleteException();
                }
                return typeParameters[0].isClass();
            }
        }
        logger.log( TreeLogger.Type.ERROR, "Expected  " + typeClass + " to extend the " + JsonMapper.class.getName() + " interface." );
        throw new UnableToCompleteException();
    }
}
