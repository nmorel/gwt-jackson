package com.github.nmorel.gwtjackson.client.deser.map;

import java.util.IdentityHashMap;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.BaseMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link IdentityHashMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link IdentityHashMap}
 * @param <V> Type of the values inside the {@link IdentityHashMap}
 *
 * @author Nicolas Morel
 */
public final class IdentityHashMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<IdentityHashMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link IdentityHashMap}
     * @param <V> Type of the values inside the {@link IdentityHashMap}
     *
     * @return a new instance of {@link IdentityHashMapJsonDeserializer}
     */
    public static <K, V> IdentityHashMapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                            JsonDeserializer<V> valueDeserializer ) {
        return new IdentityHashMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private IdentityHashMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected IdentityHashMap<K, V> newMap() {
        return new IdentityHashMap<K, V>();
    }
}
