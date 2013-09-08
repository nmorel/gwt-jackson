package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.HashSet;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link HashSet}.
 *
 * @param <T> Type of the elements inside the {@link HashSet}
 * @author Nicolas Morel
 */
public class HashSetJsonMapper<T> extends BaseSetJsonMapper<HashSet<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link HashSet}. */
    public HashSetJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected HashSet<T> newCollection()
    {
        return new HashSet<T>();
    }
}
