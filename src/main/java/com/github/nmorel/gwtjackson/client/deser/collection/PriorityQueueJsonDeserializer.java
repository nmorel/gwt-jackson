package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.PriorityQueue;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link PriorityQueue}.
 *
 * @param <T> Type of the elements inside the {@link PriorityQueue}
 *
 * @author Nicolas Morel
 */
public class PriorityQueueJsonDeserializer<T> extends BaseQueueJsonDeserializer<PriorityQueue<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link PriorityQueue}.
     * @param <T> Type of the elements inside the {@link PriorityQueue}
     *
     * @return a new instance of {@link PriorityQueueJsonDeserializer}
     */
    public static <T> PriorityQueueJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new PriorityQueueJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link PriorityQueue}.
     */
    private PriorityQueueJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected PriorityQueue<T> newCollection() {
        return new PriorityQueue<T>();
    }
}
