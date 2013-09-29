package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.ArrayList;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link Collection}. The deserialization process returns an {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link Collection}
 *
 * @author Nicolas Morel
 */
public class CollectionJsonDeserializer<T> extends BaseCollectionJsonDeserializer<Collection<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Collection}.
     * @param <T> Type of the elements inside the {@link Collection}
     *
     * @return a new instance of {@link CollectionJsonDeserializer}
     */
    public static <T> CollectionJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new CollectionJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Collection}.
     */
    private CollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected Collection<T> newCollection() {
        return new ArrayList<T>();
    }
}
