package com.github.nmorel.gwtjackson.client;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractObjectWriter<T> extends AbstractObjectMapper<T> implements ObjectWriter<T> {

    @Override
    protected JsonDeserializer<T> newDeserializer( JsonDecodingContext ctx ) {
        throw new UnsupportedOperationException( "ObjectWriter doesn't support deserialization" );
    }
}
