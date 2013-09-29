package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.LinkedHashSet;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link LinkedHashSet}.
 *
 * @param <T> Type of the elements inside the {@link LinkedHashSet}
 *
 * @author Nicolas Morel
 */
public class LinkedHashSetJsonDeserializer<T> extends BaseSetJsonDeserializer<LinkedHashSet<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link LinkedHashSet}.
     * @param <T> Type of the elements inside the {@link LinkedHashSet}
     *
     * @return a new instance of {@link LinkedHashSetJsonDeserializer}
     */
    public static <T> LinkedHashSetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new LinkedHashSetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link LinkedHashSet}.
     */
    private LinkedHashSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected LinkedHashSet<T> newCollection() {
        return new LinkedHashSet<T>();
    }
}
