package com.github.nmorel.gwtjackson.rebind;

import java.io.PrintWriter;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Nicolas Morel
 */
public class ObjectMapperCreator extends AbstractCreator {

    private static final String OBJECT_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.ObjectMapper";

    private static final String OBJECT_READER_CLASS = "com.github.nmorel.gwtjackson.client.ObjectReader";

    private static final String OBJECT_WRITER_CLASS = "com.github.nmorel.gwtjackson.client.ObjectWriter";

    private static final String ABSTRACT_OBJECT_MAPPER_CLASS = "com.github.nmorel.gwtjackson.client.AbstractObjectMapper";

    private static final String ABSTRACT_OBJECT_READER_CLASS = "com.github.nmorel.gwtjackson.client.AbstractObjectReader";

    private static final String ABSTRACT_OBJECT_WRITER_CLASS = "com.github.nmorel.gwtjackson.client.AbstractObjectWriter";

    public ObjectMapperCreator( TreeLogger logger, GeneratorContext context ) {
        super( logger, context );
    }

    /**
     * Creates the implementation of the interface denoted by typeName and extending {@link ObjectMapper}
     *
     * @param interfaceName name of the interface
     *
     * @return the fully qualified name of the created class
     * @throws UnableToCompleteException
     */
    public String create( String interfaceName ) throws UnableToCompleteException {
        JClassType interfaceClass = typeOracle.getType( interfaceName );

        // we concatenate the name of all the enclosing class
        StringBuilder builder = new StringBuilder( interfaceClass.getSimpleSourceName() + "Impl" );
        JClassType enclosingType = interfaceClass.getEnclosingType();
        while ( null != enclosingType ) {
            builder.insert( 0, enclosingType.getSimpleSourceName() + "_" );
            enclosingType = enclosingType.getEnclosingType();
        }

        String mapperClassSimpleName = builder.toString();
        String packageName = interfaceClass.getPackage().getName();
        String qualifiedMapperClassName = packageName + "." + mapperClassSimpleName;

        PrintWriter printWriter = getPrintWriter( packageName, mapperClassSimpleName );
        // the class already exists, no need to continue
        if ( printWriter == null ) {
            return qualifiedMapperClassName;
        }

        // Extract the type of the object to map
        JClassType mappedTypeClass = getMappedType( interfaceClass );

        boolean reader = typeOracle.isObjectReader( interfaceClass );
        boolean writer = typeOracle.isObjectWriter( interfaceClass );
        String abstractClass;
        if ( reader ) {
            if ( writer ) {
                abstractClass = ABSTRACT_OBJECT_MAPPER_CLASS;
            } else {
                abstractClass = ABSTRACT_OBJECT_READER_CLASS;
            }
        } else {
            abstractClass = ABSTRACT_OBJECT_WRITER_CLASS;
        }
        SourceWriter source = getSourceWriter( printWriter, packageName, mapperClassSimpleName, abstractClass + "<" +
            mappedTypeClass.getParameterizedQualifiedSourceName() + ">", interfaceName );

        writeClassBody( source, mappedTypeClass, reader, writer );

        return qualifiedMapperClassName;
    }

    private JClassType getMappedType( JClassType interfaceClass ) throws UnableToCompleteException {
        JClassType intf = interfaceClass.isInterface();
        if ( intf == null ) {
            logger.log( TreeLogger.Type.ERROR, "Expected " + interfaceClass + " to be an interface." );
            throw new UnableToCompleteException();
        }

        JClassType[] intfs = intf.getImplementedInterfaces();
        for ( JClassType t : intfs ) {
            if ( t.getQualifiedSourceName().equals( OBJECT_MAPPER_CLASS ) ) {
                return extractParameterizedType( OBJECT_MAPPER_CLASS, t.isParameterized() );
            } else if ( t.getQualifiedSourceName().equals( OBJECT_READER_CLASS ) ) {
                return extractParameterizedType( OBJECT_READER_CLASS, t.isParameterized() );
            } else if ( t.getQualifiedSourceName().equals( OBJECT_WRITER_CLASS ) ) {
                return extractParameterizedType( OBJECT_WRITER_CLASS, t.isParameterized() );
            }
        }
        logger.log( TreeLogger.Type.ERROR, "Expected  " + interfaceClass + " to extend one of the interface " + OBJECT_MAPPER_CLASS + ", " +
            OBJECT_READER_CLASS + " or " + OBJECT_WRITER_CLASS );
        throw new UnableToCompleteException();
    }

    private JClassType extractParameterizedType( String clazz, JParameterizedType genericType ) throws UnableToCompleteException {
        if ( genericType == null ) {
            logger.log( TreeLogger.Type.ERROR, "Expected the " + clazz + " declaration to specify a " +
                "parameterized type." );
            throw new UnableToCompleteException();
        }
        JClassType[] typeParameters = genericType.getTypeArgs();
        if ( typeParameters == null || typeParameters.length != 1 ) {
            logger.log( TreeLogger.Type.ERROR, "Expected the " + clazz + " declaration to specify 1 " +
                "parameterized type." );
            throw new UnableToCompleteException();
        }
        return typeParameters[0];
    }

    /**
     * Write the body of the class.
     *
     * @param source Printer
     * @param mappedTypeClass Type of the class to map
     * @param reader true if it's a reader
     * @param writer true if it's a writer
     *
     * @throws UnableToCompleteException
     */
    private void writeClassBody( SourceWriter source, JClassType mappedTypeClass, boolean reader,
                                 boolean writer ) throws UnableToCompleteException {
        source.println();
        source.indent();

        if ( reader ) {
            source.println( "@Override" );
            source.println( "protected %s<%s> newDeserializer(%s ctx) {", JSON_DESERIALIZER_CLASS, mappedTypeClass
                .getParameterizedQualifiedSourceName(), JSON_DESERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", getDeserializerFromType( mappedTypeClass ) );
            source.outdent();
            source.println( "}" );

            source.println();
        }

        if ( writer ) {
            source.println( "@Override" );
            source.println( "protected %s<%s> newSerializer(%s ctx) {", JSON_SERIALIZER_CLASS, mappedTypeClass
                .getParameterizedQualifiedSourceName(), JSON_SERIALIZATION_CONTEXT_CLASS );
            source.indent();
            source.println( "return %s;", getSerializerFromType( mappedTypeClass ) );
            source.outdent();
            source.println( "}" );

            source.println();
        }

        source.commit( logger );
    }
}
