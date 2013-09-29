package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.SortedSet;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link JsonDeserializer} implementation for {@link SortedSet}.
 *
 * @param <S> {@link SortedSet} type
 * @param <T> Type of the elements inside the {@link SortedSet}
 *
 * @author Nicolas Morel
 */
public abstract class BaseSortedSetJsonDeserializer<S extends SortedSet<T>, T> extends BaseSetJsonDeserializer<S, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link SortedSet}.
     */
    public BaseSortedSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected boolean isNullValueAllowed() {
        return false;
    }
}
