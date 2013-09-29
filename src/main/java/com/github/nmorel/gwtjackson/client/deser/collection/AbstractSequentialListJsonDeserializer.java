package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.AbstractSequentialList;
import java.util.LinkedList;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link AbstractSequentialList}. The deserialization process returns a {@link
 * LinkedList}.
 *
 * @param <T> Type of the elements inside the {@link AbstractSequentialList}
 *
 * @author Nicolas Morel
 */
public class AbstractSequentialListJsonDeserializer<T> extends BaseListJsonDeserializer<AbstractSequentialList<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractSequentialList}.
     * @param <T> Type of the elements inside the {@link AbstractSequentialList}
     *
     * @return a new instance of {@link AbstractSequentialListJsonDeserializer}
     */
    public static <T> AbstractSequentialListJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new AbstractSequentialListJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractSequentialList}.
     */
    private AbstractSequentialListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected AbstractSequentialList<T> newCollection() {
        return new LinkedList<T>();
    }
}
