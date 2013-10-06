package com.github.nmorel.gwtjackson.client.deser.map;

import java.util.LinkedHashMap;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.BaseMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link LinkedHashMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link LinkedHashMap}
 * @param <V> Type of the values inside the {@link LinkedHashMap}
 *
 * @author Nicolas Morel
 */
public final class LinkedHashMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<LinkedHashMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link LinkedHashMap}
     * @param <V> Type of the values inside the {@link LinkedHashMap}
     *
     * @return a new instance of {@link LinkedHashMapJsonDeserializer}
     */
    public static <K, V> LinkedHashMapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                          JsonDeserializer<V> valueDeserializer ) {
        return new LinkedHashMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private LinkedHashMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected LinkedHashMap<K, V> newMap() {
        return new LinkedHashMap<K, V>();
    }
}
