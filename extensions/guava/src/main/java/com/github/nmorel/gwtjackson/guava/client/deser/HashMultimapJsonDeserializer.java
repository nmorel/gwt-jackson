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
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.google.common.collect.HashMultimap;

/**
 * Default {@link JsonDeserializer} implementation for {@link HashMultimap}.
 * <p>Cannot be overriden. Use {@link BaseMultimapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link HashMultimap}
 * @param <V> Type of the values inside the {@link HashMultimap}
 *
 * @author Nicolas Morel
 */
public final class HashMultimapJsonDeserializer<K, V> extends BaseMultimapJsonDeserializer<HashMultimap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link HashMultimap}
     * @param <V> Type of the values inside the {@link HashMultimap}
     *
     * @return a new instance of {@link HashMultimapJsonDeserializer}
     */
    public static <K, V> HashMultimapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                         JsonDeserializer<V> valueDeserializer ) {
        return new HashMultimapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private HashMultimapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected HashMultimap<K, V> newMultimap() {
        return HashMultimap.create();
    }
}
