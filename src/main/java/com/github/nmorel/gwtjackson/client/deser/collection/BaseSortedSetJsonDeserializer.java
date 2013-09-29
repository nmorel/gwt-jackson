package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.SortedSet;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.SortedSet}.
 *
 * @param <S> {@link java.util.SortedSet} type
 * @param <T> Type of the elements inside the {@link java.util.SortedSet}
 *
 * @author Nicolas Morel
 */
public abstract class BaseSortedSetJsonDeserializer<S extends SortedSet<T>, T> extends BaseSetJsonDeserializer<S, T> {

    /**
     * @param deserializer {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} used to map the objects inside the {@link java
     * .util.SortedSet}.
     */
    public BaseSortedSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected boolean isNullValueAllowed() {
        return false;
    }
}
