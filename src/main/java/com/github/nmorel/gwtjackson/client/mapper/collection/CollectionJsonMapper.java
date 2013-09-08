package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.ArrayList;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Collection}. The decoding process returns an {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link Collection}
 * @author Nicolas Morel
 */
public class CollectionJsonMapper<T> extends BaseCollectionJsonMapper<Collection<T>, T>
{

    /** @param mapper {@link JsonMapper} used to map the objects inside the {@link Collection}. */
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
