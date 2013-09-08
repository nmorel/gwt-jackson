package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.AbstractList;
import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link AbstractList}. The decoding process returns an
 * {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link AbstractList}
 * @author Nicolas Morel
 */
public class AbstractListJsonMapper<T> extends BaseListJsonMapper<AbstractList<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link AbstractList}. */
    public AbstractListJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected AbstractList<T> newCollection()
    {
        return new ArrayList<T>();
    }
}
