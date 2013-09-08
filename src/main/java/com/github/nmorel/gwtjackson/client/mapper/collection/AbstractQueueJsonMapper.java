package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.AbstractQueue;
import java.util.PriorityQueue;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link AbstractQueue}. The decoding process returns a
 * {@link PriorityQueue}.
 *
 * @param <T> Type of the elements inside the {@link AbstractQueue}
 * @author Nicolas Morel
 */
public final class AbstractQueueJsonMapper<T> extends BaseQueueJsonMapper<AbstractQueue<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link AbstractQueue}. */
    public AbstractQueueJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected AbstractQueue<T> newCollection()
    {
        return new PriorityQueue<T>();
    }
}
