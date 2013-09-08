package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.Vector;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link Vector}.
 *
 * @param <T> Type of the elements inside the {@link Vector}
 * @author Nicolas Morel
 */
public class VectorJsonMapper<T> extends BaseListJsonMapper<Vector<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link Vector}. */
    public VectorJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected Vector<T> newCollection()
    {
        return new Vector<T>();
    }
}
