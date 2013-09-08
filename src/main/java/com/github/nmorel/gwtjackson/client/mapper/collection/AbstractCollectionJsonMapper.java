package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.AbstractCollection;
import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link AbstractCollection}. The decoding process
 * returns an {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link AbstractCollection}
 * @author Nicolas Morel
 */
public class AbstractCollectionJsonMapper<T> extends BaseCollectionJsonMapper<AbstractCollection<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link
     * AbstractCollection}. */
    public AbstractCollectionJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected AbstractCollection<T> newCollection()
    {
        return new ArrayList<T>();
    }
}
