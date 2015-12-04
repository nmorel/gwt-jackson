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
import com.google.common.collect.TreeMultimap;

/**
 * Default {@link JsonDeserializer} implementation for {@link TreeMultimap}.
 * <p>Cannot be overriden. Use {@link BaseMultimapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link TreeMultimap}
 * @param <V> Type of the values inside the {@link TreeMultimap}
 *
 * @author Nicolas Morel
 */
public final class TreeMultimapJsonDeserializer<K extends Comparable<K>, V extends Comparable<V>> extends
        BaseMultimapJsonDeserializer<TreeMultimap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     *
     * @return a new instance of {@link TreeMultimapJsonDeserializer}
     */
    public static TreeMultimapJsonDeserializer newInstance( KeyDeserializer keyDeserializer, JsonDeserializer valueDeserializer ) {
        return new TreeMultimapJsonDeserializer( keyDeserializer, valueDeserializer );
    }

    /**
     * @param keyDeserializer {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private TreeMultimapJsonDeserializer( KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
    }

    @Override
    protected TreeMultimap<K, V> newMultimap() {
        return TreeMultimap.create();
    }
}
