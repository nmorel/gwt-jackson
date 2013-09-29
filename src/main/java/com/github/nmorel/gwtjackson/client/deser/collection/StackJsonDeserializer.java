package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Stack;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link Stack}.
 *
 * @param <T> Type of the elements inside the {@link Stack}
 *
 * @author Nicolas Morel
 */
public class StackJsonDeserializer<T> extends BaseListJsonDeserializer<Stack<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Stack}.
     * @param <T> Type of the elements inside the {@link Stack}
     *
     * @return a new instance of {@link StackJsonDeserializer}
     */
    public static <T> StackJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new StackJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Stack}.
     */
    private StackJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected Stack<T> newCollection() {
        return new Stack<T>();
    }
}
