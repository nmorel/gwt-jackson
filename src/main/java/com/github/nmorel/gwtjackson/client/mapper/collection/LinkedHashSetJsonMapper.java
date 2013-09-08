package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.LinkedHashSet;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link LinkedHashSet}.
 *
 * @param <T> Type of the elements inside the {@link LinkedHashSet}
 * @author Nicolas Morel
 */
public class LinkedHashSetJsonMapper<T> extends BaseSetJsonMapper<LinkedHashSet<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link LinkedHashSet}. */
    public LinkedHashSetJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected LinkedHashSet<T> newCollection()
    {
        return new LinkedHashSet<T>();
    }
}
