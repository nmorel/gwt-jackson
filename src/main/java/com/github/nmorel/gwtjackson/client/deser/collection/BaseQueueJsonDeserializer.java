package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Queue;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.Queue}.
 *
 * @param <Q> {@link java.util.Queue} type
 * @param <T> Type of the elements inside the {@link java.util.Queue}
 *
 * @author Nicolas Morel
 */
public abstract class BaseQueueJsonDeserializer<Q extends Queue<T>, T> extends BaseCollectionJsonDeserializer<Q, T> {

    /**
     * @param deserializer {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} used to map the objects inside the {@link java
     * .util.Queue}.
     */
    public BaseQueueJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected boolean isNullValueAllowed() {
        return false;
    }
}
