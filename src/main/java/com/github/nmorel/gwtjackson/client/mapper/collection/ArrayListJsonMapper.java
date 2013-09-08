package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link ArrayList}
 * @author Nicolas Morel
 */
public class ArrayListJsonMapper<T> extends BaseListJsonMapper<ArrayList<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link ArrayList}. */
    public ArrayListJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected ArrayList<T> newCollection()
    {
        return new ArrayList<T>();
    }
}
