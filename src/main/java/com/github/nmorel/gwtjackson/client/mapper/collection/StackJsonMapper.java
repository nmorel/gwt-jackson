package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.Stack;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link Stack}.
 *
 * @param <T> Type of the elements inside the {@link Stack}
 * @author Nicolas Morel
 */
public class StackJsonMapper<T> extends BaseListJsonMapper<Stack<T>, T>
{

    /** @param mapper {@link com.github.nmorel.gwtjackson.client.JsonMapper} used to map the objects inside the {@link Stack}. */
    public StackJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected Stack<T> newCollection()
    {
        return new Stack<T>();
    }
}
