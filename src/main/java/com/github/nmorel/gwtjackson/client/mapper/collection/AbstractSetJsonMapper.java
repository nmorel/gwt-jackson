package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.AbstractSet;
import java.util.LinkedHashSet;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link java.util.Set}. The decoding process returns a
 * {@link LinkedHashSet}.
 *
 * @param <T> Type of the elements inside the {@link AbstractSet}
 * @author Nicolas Morel
 */
public final class AbstractSetJsonMapper<T> extends BaseSetJsonMapper<AbstractSet<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link AbstractSet}. */
    public AbstractSetJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected AbstractSet<T> newCollection()
    {
        return new LinkedHashSet<T>();
    }
}
