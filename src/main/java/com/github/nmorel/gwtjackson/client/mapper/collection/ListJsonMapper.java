package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link List}. The decoding process returns an {@link ArrayList}.
 *
 * @author Nicolas Morel
 */
public class ListJsonMapper<T> extends AbstractCollectionJsonMapper<List<T>, T>
{

    /** @param mapper {@link JsonMapper} used to map the objects inside the list. */
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
