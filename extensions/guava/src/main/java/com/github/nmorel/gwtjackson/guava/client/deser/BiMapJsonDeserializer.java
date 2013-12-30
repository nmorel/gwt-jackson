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
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Default {@link JsonDeserializer} implementation for {@link BiMap}. The deserialization process returns a {@link HashBiMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link BiMap}
 * @param <V> Type of the values inside the {@link BiMap}
 *
 * @author Nicolas Morel
 */
public final class BiMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<BiMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link BiMap}
     * @param <V> Type of the values inside the {@link BiMap}
     *
     * @return a new instance of {@link BiMapJsonDeserializer}
     */
    public static <K, V> BiMapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                  JsonDeserializer<V> valueDeserializer ) {
        return new BiMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private BiMapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected BiMap<K, V> newMap() {
        return HashBiMap.create();
    }
}
