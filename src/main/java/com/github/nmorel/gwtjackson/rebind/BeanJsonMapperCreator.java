package com.github.nmorel.gwtjackson.rebind;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.user.rebind.SourceWriter;

/** @author Nicolas Morel */
public class BeanJsonMapperCreator extends AbstractJsonMapperCreator
{

    public BeanJsonMapperCreator( TreeLogger logger, GeneratorContext context, JacksonTypeOracle typeOracle )
    {
        super( logger, context, typeOracle );
    }

    /**
     * Creates an implementation of {@link com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper} for the type given in
     * parameter
     *
     * @param beanName name of the bean
     * @return the fully qualified name of the created class
     * @throws com.google.gwt.core.ext.UnableToCompleteException
     */
    public String create( String beanName ) throws UnableToCompleteException
    {
        JClassType beanType = typeOracle.getType( beanName );
        return create( beanType );
    }

    /**
     * Creates an implementation of {@link com.github.nmorel.gwtjackson.client.mapper.AbstractBeanJsonMapper} for the type given in
     * parameter
     *
     * @param beanType type of the bean
     * @return the fully qualified name of the created class
     * @throws com.google.gwt.core.ext.UnableToCompleteException
     */
    public String create( JClassType beanType ) throws UnableToCompleteException
    {
        // we concatenate the name of all the enclosing class
        StringBuilder builder = new StringBuilder( beanType.getSimpleSourceName() + "BeanJsonMapperImpl" );
        JClassType enclosingType = beanType.getEnclosingType();
        while ( null != enclosingType )
        {
            builder.insert( 0, enclosingType.getSimpleSourceName() + "_" );
            enclosingType = enclosingType.getEnclosingType();
        }

        String mapperClassSimpleName = builder.toString();
        String packageName = beanType.getPackage().getName();
        String qualifiedMapperClassName = packageName + "." + mapperClassSimpleName;

        SourceWriter source = getSourceWriter( packageName, mapperClassSimpleName, ABSTRACT_BEAN_JSON_MAPPER_CLASS + "<" +
            beanType.getParameterizedQualifiedSourceName() + ">" );

        // the class already exists, no need to continue
        if ( source == null )
        {
            return qualifiedMapperClassName;
        }

        writeClassBody( source, beanType );

        return qualifiedMapperClassName;
    }

    private void writeClassBody( SourceWriter source, JClassType beanType ) throws UnableToCompleteException
    {
        source.println();
        source.indent();

        source.println( "@Override" );
        source.println( "protected %s newInstance() {", beanType.getParameterizedQualifiedSourceName() );
        source.indent();
        generateNewInstanceBody( source, beanType );
        source.outdent();
        source.println( "}" );

        source.println();

        source.println( "@Override" );
        source.println( "protected void initProperties(java.util.Map<java.lang.String, %s<%s>> properties) {", PROPERTY_BEAN_CLASS, beanType
            .getParameterizedQualifiedSourceName() );
        source.indent();
        generateInitProperties( source, beanType );
        source.outdent();
        source.println( "}" );

        source.println();

        source.outdent();
        source.commit( logger );
    }

    private void generateNewInstanceBody( SourceWriter source, JClassType beanType )
    {
        source.println( "return new %s();", beanType.getParameterizedQualifiedSourceName() );
    }

    private void generateInitProperties( SourceWriter source, JClassType beanType ) throws UnableToCompleteException
    {
        for ( JField field : beanType.getFields() )
        {
            String name = field.getName().substring( 0, 1 ).toUpperCase() + field.getName().substring( 1 );
            String getterName = "get" + name;
            String setterName = "set" + name;

            source.println( "properties.put(\"%s\", new " + PROPERTY_BEAN_CLASS + "<%s>() {", field.getName(), beanType
                .getParameterizedQualifiedSourceName() );
            source.indent();

            source.println( "@Override" );
            source.println( "public void decode(%s reader, %s bean, %s ctx) throws java.io.IOException {", JSON_READER_CLASS, beanType
                .getParameterizedQualifiedSourceName(), JSON_DECODING_CONTEXT_CLASS );
            source.indent();
            source.println( "bean.%s(%s.decode(reader, ctx));", setterName, createMapperFromType( field.getType() ) );
            source.outdent();
            source.println( "}" );

            source.println();

            source.println( "@Override" );
            source.println( "public void encode(%s writer, %s bean, %s ctx) throws java.io.IOException {", JSON_WRITER_CLASS, beanType
                .getParameterizedQualifiedSourceName(), JSON_ENCODING_CONTEXT_CLASS );
            source.indent();
            source.println( "%s.encode(writer, bean.%s(), ctx);", createMapperFromType( field.getType() ), getterName );
            source.outdent();
            source.println( "}" );

            source.outdent();
            source.println( "} );" );
        }
    }
}
