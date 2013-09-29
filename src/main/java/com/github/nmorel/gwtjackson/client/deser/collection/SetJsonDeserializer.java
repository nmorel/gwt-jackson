package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.LinkedHashSet;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} implementation for {@link java.util.Set}. The decoding process
 * returns a {@link java.util.LinkedHashSet}.
 *
 * @param <T> Type of the elements inside the {@link java.util.Set}
 *
 * @author Nicolas Morel
 */
public final class SetJsonDeserializer<T> extends BaseSetJsonDeserializer<Set<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Set}.
     * @param <T> Type of the elements inside the {@link Set}
     *
     * @return a new instance of {@link SetJsonDeserializer}
     */
    public static <T> SetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new SetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Set}.
     */
    private SetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected Set<T> newCollection() {
        return new LinkedHashSet<T>();
    }
}
