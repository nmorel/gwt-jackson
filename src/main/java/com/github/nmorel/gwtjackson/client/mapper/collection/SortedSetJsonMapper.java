package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.SortedSet;
import java.util.TreeSet;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link SortedSet}. The decoding process returns a
 * {@link TreeSet}.
 *
 * @param <T> Type of the elements inside the {@link SortedSet}
 * @author Nicolas Morel
 */
public final class SortedSetJsonMapper<T> extends BaseSortedSetJsonMapper<SortedSet<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link SortedSet}. */
    public SortedSetJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected SortedSet<T> newCollection()
    {
        return new TreeSet<T>();
    }
}
