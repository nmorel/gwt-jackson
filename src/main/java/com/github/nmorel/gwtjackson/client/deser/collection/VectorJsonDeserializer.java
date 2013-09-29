package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Vector;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link Vector}.
 *
 * @param <T> Type of the elements inside the {@link Vector}
 *
 * @author Nicolas Morel
 */
public class VectorJsonDeserializer<T> extends BaseListJsonDeserializer<Vector<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Vector}.
     * @param <T> Type of the elements inside the {@link Vector}
     *
     * @return a new instance of {@link VectorJsonDeserializer}
     */
    public static <T> VectorJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new VectorJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Vector}.
     */
    private VectorJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected Vector<T> newCollection() {
        return new Vector<T>();
    }
}
