package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link JsonDeserializer} implementation for {@link List}.
 *
 * @param <L> {@link List} type
 * @param <T> Type of the elements inside the {@link List}
 *
 * @author Nicolas Morel
 */
public abstract class BaseListJsonDeserializer<L extends List<T>, T> extends BaseCollectionJsonDeserializer<L, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link List}.
     */
    public BaseListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }
}
