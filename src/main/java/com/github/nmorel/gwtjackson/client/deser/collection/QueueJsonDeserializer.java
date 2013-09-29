package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.LinkedList;
import java.util.Queue;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link Queue}. The deserialization process returns a {@link LinkedList}.
 *
 * @param <T> Type of the elements inside the {@link Queue}
 *
 * @author Nicolas Morel
 */
public final class QueueJsonDeserializer<T> extends BaseQueueJsonDeserializer<Queue<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Queue}.
     * @param <T> Type of the elements inside the {@link Queue}
     *
     * @return a new instance of {@link QueueJsonDeserializer}
     */
    public static <T> QueueJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new QueueJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Queue}.
     */
    private QueueJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected Queue<T> newCollection() {
        return new LinkedList<T>();
    }
}
