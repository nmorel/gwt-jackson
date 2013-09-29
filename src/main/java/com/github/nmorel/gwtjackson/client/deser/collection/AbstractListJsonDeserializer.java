package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.AbstractList;
import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link AbstractList}. The deserialization process returns an {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link AbstractList}
 *
 * @author Nicolas Morel
 */
public class AbstractListJsonDeserializer<T> extends BaseListJsonDeserializer<AbstractList<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractList}.
     * @param <T> Type of the elements inside the {@link AbstractList}
     *
     * @return a new instance of {@link AbstractListJsonDeserializer}
     */
    public static <T> AbstractListJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new AbstractListJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractList}.
     */
    private AbstractListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected AbstractList<T> newCollection() {
        return new ArrayList<T>();
    }
}
