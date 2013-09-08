package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link List}. The decoding process returns an {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link List}
 * @author Nicolas Morel
 */
public final class ListJsonMapper<T> extends BaseListJsonMapper<List<T>, T>
{

    /** @param mapper {@link JsonMapper} used to map the objects inside the {@link List}. */
    public ListJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }

    @Override
    protected List<T> newCollection()
    {
        return new ArrayList<T>();
    }
}
