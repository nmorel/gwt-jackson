package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.LinkedList;
import java.util.Queue;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link Queue}. The decoding process returns a {@link
 * LinkedList}.
 *
 * @param <T> Type of the elements inside the {@link Queue}
 * @author Nicolas Morel
 */
public final class QueueJsonMapper<T> extends BaseQueueJsonMapper<Queue<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link Queue}. */
    public QueueJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected Queue<T> newCollection()
    {
        return new LinkedList<T>();
    }
}
