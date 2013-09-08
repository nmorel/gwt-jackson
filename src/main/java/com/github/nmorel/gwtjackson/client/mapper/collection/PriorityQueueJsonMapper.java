package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.PriorityQueue;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link PriorityQueue}.
 *
 * @param <T> Type of the elements inside the {@link PriorityQueue}
 * @author Nicolas Morel
 */
public class PriorityQueueJsonMapper<T> extends BaseQueueJsonMapper<PriorityQueue<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link PriorityQueue}. */
    public PriorityQueueJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected PriorityQueue<T> newCollection()
    {
        return new PriorityQueue<T>();
    }
}
