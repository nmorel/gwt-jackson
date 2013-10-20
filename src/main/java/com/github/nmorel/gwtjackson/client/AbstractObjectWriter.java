package com.github.nmorel.gwtjackson.client;

/**
 * Base implementation of {@link ObjectWriter}. Extends {@link AbstractObjectMapper} to avoid code duplication, trying to read with this
 * writer will result in an {@link UnsupportedOperationException}.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractObjectWriter<T> extends AbstractObjectMapper<T> implements ObjectWriter<T> {

    public AbstractObjectWriter( String rootName ) {
        super( rootName );
    }

    @Override
    protected final JsonDeserializer<T> newDeserializer( JsonDeserializationContext ctx ) {
        throw new UnsupportedOperationException( "ObjectWriter doesn't support deserialization" );
    }
}
