package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.List}.
 *
 * @param <L> {@link java.util.List} type
 * @param <T> Type of the elements inside the {@link java.util.List}
 *
 * @author Nicolas Morel
 */
public abstract class BaseListJsonDeserializer<L extends List<T>, T> extends BaseCollectionJsonDeserializer<L, T> {

    /**
     * @param deserializer {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} used to map the objects inside the {@link java
     * .util.List}.
     */
    public BaseListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }
}
