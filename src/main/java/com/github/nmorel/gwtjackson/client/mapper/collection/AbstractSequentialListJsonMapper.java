package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.AbstractSequentialList;
import java.util.LinkedList;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link AbstractSequentialList}. The decoding process
 * returns a {@link LinkedList}.
 *
 * @param <T> Type of the elements inside the {@link AbstractSequentialList}
 * @author Nicolas Morel
 */
public class AbstractSequentialListJsonMapper<T> extends BaseListJsonMapper<AbstractSequentialList<T>, T>
{

    /**
     * @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link
     * AbstractSequentialList}.
     */
    public AbstractSequentialListJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected AbstractSequentialList<T> newCollection()
    {
        return new LinkedList<T>();
    }
}
