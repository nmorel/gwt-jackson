package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.HashSet;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Set}. The decoding process returns an {@link HashSet}.
 *
 * @param <T> Type of the elements inside the {@link Set}
 * @author Nicolas Morel
 */
public final class SetJsonMapper<T> extends BaseSetJsonMapper<Set<T>, T>
{

    /** @param mapper {@link JsonMapper} used to map the objects inside the {@link Set}. */
    public SetJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected Set<T> newCollection()
    {
        return new HashSet<T>();
    }
}
