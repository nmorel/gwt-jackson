package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.HashSet;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link HashSet}.
 *
 * @param <T> Type of the elements inside the {@link HashSet}
 *
 * @author Nicolas Morel
 */
public class HashSetJsonDeserializer<T> extends BaseSetJsonDeserializer<HashSet<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link HashSet}.
     * @param <T> Type of the elements inside the {@link HashSet}
     *
     * @return a new instance of {@link HashSetJsonDeserializer}
     */
    public static <T> HashSetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new HashSetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link HashSet}.
     */
    private HashSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected HashSet<T> newCollection() {
        return new HashSet<T>();
    }
}
