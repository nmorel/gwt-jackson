package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link List}.
 *
 * @param <L> {@link List} type
 * @param <T> Type of the elements inside the {@link List}
 * @author Nicolas Morel
 */
public abstract class BaseListJsonMapper<L extends List<T>, T> extends BaseCollectionJsonMapper<L, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link List}. */
    public BaseListJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }
}
