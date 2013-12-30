/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.guava.client.deser;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.BaseMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.google.common.collect.HashBiMap;

/**
 * Default {@link JsonDeserializer} implementation for {@link HashBiMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link HashBiMap}
 * @param <V> Type of the values inside the {@link HashBiMap}
 *
 * @author Nicolas Morel
 */
public final class HashBiMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<HashBiMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link HashBiMap}
     * @param <V> Type of the values inside the {@link HashBiMap}
     *
     * @return a new instance of {@link HashBiMapJsonDeserializer}
     */
    public static <K, V> HashBiMapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                      JsonDeserializer<V> valueDeserializer ) {
        return new HashBiMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private HashBiMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected HashBiMap<K, V> newMap() {
        return HashBiMap.create();
    }
}
