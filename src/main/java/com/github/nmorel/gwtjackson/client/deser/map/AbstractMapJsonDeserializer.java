package com.github.nmorel.gwtjackson.client.deser.map;

import java.util.AbstractMap;
import java.util.LinkedHashMap;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.BaseMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link AbstractMap}. The deserialization process returns a {@link LinkedHashMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link AbstractMap}
 * @param <V> Type of the values inside the {@link AbstractMap}
 *
 * @author Nicolas Morel
 */
public final class AbstractMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<AbstractMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link AbstractMap}
     * @param <V> Type of the values inside the {@link AbstractMap}
     *
     * @return a new instance of {@link AbstractMapJsonDeserializer}
     */
    public static <K, V> AbstractMapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                        JsonDeserializer<V> valueDeserializer ) {
        return new AbstractMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private AbstractMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected AbstractMap<K, V> newMap() {
        return new LinkedHashMap<K, V>();
    }
}
