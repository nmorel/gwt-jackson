package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link Set}.
 *
 * @param <S> {@link Set} type
 * @param <T> Type of the elements inside the {@link Set}
 * @author Nicolas Morel
 */
public abstract class BaseSetJsonMapper<S extends Set<T>, T> extends BaseCollectionJsonMapper<S, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link Set}. */
    public BaseSetJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }
}
