package com.github.nmorel.gwtjackson.client.deser.map;

import java.util.SortedMap;
import java.util.TreeMap;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.BaseMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link SortedMap}. The deserialization process returns a {@link TreeMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link SortedMap}
 * @param <V> Type of the values inside the {@link SortedMap}
 *
 * @author Nicolas Morel
 */
public final class SortedMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<SortedMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link SortedMap}
     * @param <V> Type of the values inside the {@link SortedMap}
     *
     * @return a new instance of {@link SortedMapJsonDeserializer}
     */
    public static <K, V> SortedMapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                      JsonDeserializer<V> valueDeserializer ) {
        return new SortedMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private SortedMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected SortedMap<K, V> newMap() {
        return new TreeMap<K, V>();
    }
}
