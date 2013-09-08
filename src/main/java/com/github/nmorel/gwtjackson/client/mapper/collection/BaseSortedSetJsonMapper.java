package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.SortedSet;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link SortedSet}.
 *
 * @param <S> {@link SortedSet} type
 * @param <T> Type of the elements inside the {@link SortedSet}
 * @author Nicolas Morel
 */
public abstract class BaseSortedSetJsonMapper<S extends SortedSet<T>, T> extends BaseSetJsonMapper<S, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link SortedSet}. */
    public BaseSortedSetJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }
}
