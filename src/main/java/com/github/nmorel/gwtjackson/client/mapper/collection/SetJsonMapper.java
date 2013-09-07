package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.HashSet;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Set}. The decoding process returns an {@link HashSet}.
 *
 * @author Nicolas Morel
 */
public class SetJsonMapper<T> extends AbstractCollectionJsonMapper<Set<T>, T>
{

    /** @param mapper {@link JsonMapper} used to map the objects inside the set. */
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
