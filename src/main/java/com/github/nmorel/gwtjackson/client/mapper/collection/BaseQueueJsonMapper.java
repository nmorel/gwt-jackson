package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.Queue;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Base {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link Queue}.
 *
 * @param <Q> {@link Queue} type
 * @param <T> Type of the elements inside the {@link Queue}
 * @author Nicolas Morel
 */
public abstract class BaseQueueJsonMapper<Q extends Queue<T>, T> extends BaseCollectionJsonMapper<Q, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link Queue}. */
    public BaseQueueJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }
}
