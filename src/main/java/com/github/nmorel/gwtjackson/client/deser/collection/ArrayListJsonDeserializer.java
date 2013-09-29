package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link ArrayList}
 *
 * @author Nicolas Morel
 */
public class ArrayListJsonDeserializer<T> extends BaseListJsonDeserializer<ArrayList<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link ArrayList}.
     * @param <T> Type of the elements inside the {@link ArrayList}
     *
     * @return a new instance of {@link ArrayListJsonDeserializer}
     */
    public static <T> ArrayListJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new ArrayListJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link ArrayList}.
     */
    private ArrayListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected ArrayList<T> newCollection() {
        return new ArrayList<T>();
    }
}
