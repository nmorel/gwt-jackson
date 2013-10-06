package com.github.nmorel.gwtjackson.client.deser.map;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link Map}. The deserialization process returns a {@link LinkedHashMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link Map}
 * @param <V> Type of the values inside the {@link Map}
 *
 * @author Nicolas Morel
 */
public final class MapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<Map<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link Map}
     * @param <V> Type of the values inside the {@link Map}
     *
     * @return a new instance of {@link MapJsonDeserializer}
     */
    public static <K, V> MapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                JsonDeserializer<V> valueDeserializer ) {
        return new MapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private MapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected Map<K, V> newMap() {
        return new LinkedHashMap<K, V>();
    }
}
