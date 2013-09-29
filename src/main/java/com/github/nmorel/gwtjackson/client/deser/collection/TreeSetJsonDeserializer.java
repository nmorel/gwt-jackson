package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.TreeSet;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link TreeSet}.
 *
 * @param <T> Type of the elements inside the {@link TreeSet}
 *
 * @author Nicolas Morel
 */
public class TreeSetJsonDeserializer<T> extends BaseSortedSetJsonDeserializer<TreeSet<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link TreeSet}.
     * @param <T> Type of the elements inside the {@link TreeSet}
     *
     * @return a new instance of {@link TreeSetJsonDeserializer}
     */
    public static <T> TreeSetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new TreeSetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link TreeSet}.
     */
    private TreeSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected TreeSet<T> newCollection() {
        return new TreeSet<T>();
    }
}
