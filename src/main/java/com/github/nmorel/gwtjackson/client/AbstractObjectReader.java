package com.github.nmorel.gwtjackson.client;

/**
 * Base implementation of {@link ObjectReader}. Extends {@link AbstractObjectMapper} to avoid code duplication, trying to write with this
 * reader will result in an {@link UnsupportedOperationException}.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractObjectReader<T> extends AbstractObjectMapper<T> implements ObjectReader<T> {

    public AbstractObjectReader( String rootName ) {
        super( rootName );
    }

    @Override
    protected final JsonSerializer<T> newSerializer( JsonSerializationContext ctx ) {
        throw new UnsupportedOperationException( "ObjectReader doesn't support serialization" );
    }
}
