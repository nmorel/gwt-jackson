package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Queue;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link JsonDeserializer} implementation for {@link Queue}.
 *
 * @param <Q> {@link Queue} type
 * @param <T> Type of the elements inside the {@link Queue}
 *
 * @author Nicolas Morel
 */
public abstract class BaseQueueJsonDeserializer<Q extends Queue<T>, T> extends BaseCollectionJsonDeserializer<Q, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link Queue}.
     */
    public BaseQueueJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected boolean isNullValueAllowed() {
        return false;
    }
}
