package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.AbstractQueue;
import java.util.PriorityQueue;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.AbstractQueue}. The decoding
 * process returns a
 * {@link java.util.PriorityQueue}.
 *
 * @param <T> Type of the elements inside the {@link java.util.AbstractQueue}
 *
 * @author Nicolas Morel
 */
public final class AbstractQueueJsonDeserializer<T> extends BaseQueueJsonDeserializer<AbstractQueue<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractQueue}.
     * @param <T> Type of the elements inside the {@link AbstractQueue}
     *
     * @return a new instance of {@link AbstractQueueJsonDeserializer}
     */
    public static <T> AbstractQueueJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new AbstractQueueJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractQueue}.
     */
    private AbstractQueueJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected AbstractQueue<T> newCollection() {
        return new PriorityQueue<T>();
    }
}
