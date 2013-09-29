package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.SortedSet;
import java.util.TreeSet;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.SortedSet}. The decoding
 * process returns a
 * {@link java.util.TreeSet}.
 *
 * @param <T> Type of the elements inside the {@link java.util.SortedSet}
 *
 * @author Nicolas Morel
 */
public final class SortedSetJsonDeserializer<T> extends BaseSortedSetJsonDeserializer<SortedSet<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link SortedSet}.
     * @param <T> Type of the elements inside the {@link SortedSet}
     *
     * @return a new instance of {@link SortedSetJsonDeserializer}
     */
    public static <T> SortedSetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new SortedSetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link SortedSet}.
     */
    private SortedSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected SortedSet<T> newCollection() {
        return new TreeSet<T>();
    }
}
