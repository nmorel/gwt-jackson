package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.Set}.
 *
 * @param <S> {@link java.util.Set} type
 * @param <T> Type of the elements inside the {@link java.util.Set}
 *
 * @author Nicolas Morel
 */
public abstract class BaseSetJsonDeserializer<S extends Set<T>, T> extends BaseCollectionJsonDeserializer<S, T> {

    /**
     * @param deserializer {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} used to map the objects inside the {@link
     * java.util.Set}.
     */
    public BaseSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }
}
