package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.List}. The decoding process
 * returns an {@link java.util.ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link java.util.List}
 *
 * @author Nicolas Morel
 */
public final class ListJsonDeserializer<T> extends BaseListJsonDeserializer<List<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link List}.
     * @param <T> Type of the elements inside the {@link List}
     *
     * @return a new instance of {@link ListJsonDeserializer}
     */
    public static <T> ListJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new ListJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link List}.
     */
    private ListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected List<T> newCollection() {
        return new ArrayList<T>();
    }
}
