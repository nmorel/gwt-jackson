package com.github.nmorel.gwtjackson.client.deser.map;

import java.util.HashMap;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.BaseMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link HashMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link HashMap}
 * @param <V> Type of the values inside the {@link HashMap}
 *
 * @author Nicolas Morel
 */
public final class HashMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<HashMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link HashMap}
     * @param <V> Type of the values inside the {@link HashMap}
     *
     * @return a new instance of {@link HashMapJsonDeserializer}
     */
    public static <K, V> HashMapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                    JsonDeserializer<V> valueDeserializer ) {
        return new HashMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private HashMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected HashMap<K, V> newMap() {
        return new HashMap<K, V>();
    }
}
