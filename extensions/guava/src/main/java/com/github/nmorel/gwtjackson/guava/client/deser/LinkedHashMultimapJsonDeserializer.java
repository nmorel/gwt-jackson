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
import com.google.common.collect.LinkedHashMultimap;

/**
 * Default {@link JsonDeserializer} implementation for {@link LinkedHashMultimap}.
 * <p>Cannot be overriden. Use {@link BaseMultimapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link LinkedHashMultimap}
 * @param <V> Type of the values inside the {@link LinkedHashMultimap}
 *
 * @author Nicolas Morel
 */
public final class LinkedHashMultimapJsonDeserializer<K, V> extends BaseMultimapJsonDeserializer<LinkedHashMultimap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K> Type of the keys inside the {@link LinkedHashMultimap}
     * @param <V> Type of the values inside the {@link LinkedHashMultimap}
     *
     * @return a new instance of {@link LinkedHashMultimapJsonDeserializer}
     */
    public static <K, V> LinkedHashMultimapJsonDeserializer<K, V> newInstance( KeyDeserializer<K> keyDeserializer,
                                                                               JsonDeserializer<V> valueDeserializer ) {
        return new LinkedHashMultimapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private LinkedHashMultimapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected LinkedHashMultimap<K, V> newMultimap() {
        return LinkedHashMultimap.create();
    }
}
