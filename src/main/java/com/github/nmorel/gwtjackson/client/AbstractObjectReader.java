package com.github.nmorel.gwtjackson.client;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractObjectReader<T> extends AbstractObjectMapper<T> implements ObjectReader<T> {

    @Override
    protected JsonSerializer<T> newSerializer( JsonEncodingContext ctx ) {
        throw new UnsupportedOperationException( "ObjectReader doesn't support serialization" );
    }
}
