package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link JsonDeserializer} implementation for {@link Set}.
 *
 * @param <S> {@link Set} type
 * @param <T> Type of the elements inside the {@link Set}
 *
 * @author Nicolas Morel
 */
public abstract class BaseSetJsonDeserializer<S extends Set<T>, T> extends BaseCollectionJsonDeserializer<S, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link Set}.
     */
    public BaseSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }
}
