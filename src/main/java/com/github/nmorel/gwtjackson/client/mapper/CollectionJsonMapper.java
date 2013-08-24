package com.github.nmorel.gwtjackson.client.mapper;

import java.util.Collection;
import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Collection}. The decoding process returns an {@link ArrayList}.
 *
 * @author Nicolas Morel
 */
public class CollectionJsonMapper<C extends Collection<T>, T> extends IterableJsonMapper<C, T>
{

    /** @param mapper {@link JsonMapper} used to map the objects inside the collection. */
    public CollectionJsonMapper( JsonMapper<T> mapper )
    {
        super( mapper );
    }
}
