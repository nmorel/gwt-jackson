package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.AbstractQueue;
import java.util.PriorityQueue;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link AbstractQueue}. The deserialization process returns a {@link PriorityQueue}.
 *
 * @param <T> Type of the elements inside the {@link AbstractQueue}
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
