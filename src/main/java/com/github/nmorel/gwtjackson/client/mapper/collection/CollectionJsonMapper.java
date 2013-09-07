package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.ArrayList;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Collection}. The decoding process returns an {@link ArrayList}.
 *
 * @author Nicolas Morel
 */
public class CollectionJsonMapper<T> extends AbstractCollectionJsonMapper<Collection<T>, T>
{

    /** @param mapper {@link JsonMapper} used to map the objects inside the collection. */
    public CollectionJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected Collection<T> newCollection()
    {
        return new ArrayList<T>();
    }
}
