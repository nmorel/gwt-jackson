package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.LinkedList;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.LinkedList}.
 *
 * @param <T> Type of the elements inside the {@link java.util.LinkedList}
 *
 * @author Nicolas Morel
 */
public class LinkedListJsonDeserializer<T> extends BaseListJsonDeserializer<LinkedList<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link LinkedList}.
     * @param <T> Type of the elements inside the {@link LinkedList}
     *
     * @return a new instance of {@link LinkedListJsonDeserializer}
     */
    public static <T> LinkedListJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new LinkedListJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link LinkedList}.
     */
    private LinkedListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected LinkedList<T> newCollection() {
        return new LinkedList<T>();
    }
}
