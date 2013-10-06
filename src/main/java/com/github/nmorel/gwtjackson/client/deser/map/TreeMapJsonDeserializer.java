package com.github.nmorel.gwtjackson.client.deser.map;

import java.util.TreeMap;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.BaseMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link TreeMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link TreeMap}
 * @param <V> Type of the values inside the {@link TreeMap}
 *
 * @author Nicolas Morel
 */
public final class TreeMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<TreeMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link TreeMap}
     * @param <V> Type of the values inside the {@link TreeMap}
     *
     * @return a new instance of {@link TreeMapJsonDeserializer}
     */
    public static <K, V> TreeMapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                    JsonDeserializer<V> valueDeserializer ) {
        return new TreeMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private TreeMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected TreeMap<K, V> newMap() {
        return new TreeMap<K, V>();
    }
}
