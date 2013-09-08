package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.TreeSet;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link TreeSet}.
 *
 * @param <T> Type of the elements inside the {@link TreeSet}
 * @author Nicolas Morel
 */
public class TreeSetJsonMapper<T> extends BaseSortedSetJsonMapper<TreeSet<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link TreeSet}. */
    public TreeSetJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected TreeSet<T> newCollection()
    {
        return new TreeSet<T>();
    }
}
