package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.LinkedList;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link LinkedList}.
 *
 * @param <T> Type of the elements inside the {@link LinkedList}
 * @author Nicolas Morel
 */
public class LinkedListJsonMapper<T> extends BaseListJsonMapper<LinkedList<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link LinkedList}. */
    public LinkedListJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected LinkedList<T> newCollection()
    {
        return new LinkedList<T>();
    }
}
