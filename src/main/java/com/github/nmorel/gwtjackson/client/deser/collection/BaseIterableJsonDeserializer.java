package com.github.nmorel.gwtjackson.client.deser.collection;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link JsonDeserializer} implementation for {@link Iterable}.
 *
 * @param <I> {@link Iterable} type
 * @param <T> Type of the elements inside the {@link Iterable}
 *
 * @author Nicolas Morel
 */
public abstract class BaseIterableJsonDeserializer<I extends Iterable<T>, T> extends JsonDeserializer<I> {

    protected final JsonDeserializer<T> deserializer;

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link Iterable}.
     */
    public BaseIterableJsonDeserializer( JsonDeserializer<T> deserializer ) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer can't be null" );
        }
        this.deserializer = deserializer;
    }

    @Override
    public void setBackReference( String referenceName, Object reference, I value, JsonDeserializationContext ctx ) {
        if ( null != value ) {
            for ( T val : value ) {
                deserializer.setBackReference( referenceName, reference, val, ctx );
            }
        }
    }
}
