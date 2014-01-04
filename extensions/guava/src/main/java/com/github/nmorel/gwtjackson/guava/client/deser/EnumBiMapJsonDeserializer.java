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
import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.BaseMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.EnumKeyDeserializer;
import com.google.common.collect.EnumBiMap;

/**
 * Default {@link JsonDeserializer} implementation for {@link EnumBiMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the enum keys inside the {@link EnumBiMap}
 * @param <V> Type of the values inside the {@link EnumBiMap}
 *
 * @author Nicolas Morel
 */
public final class EnumBiMapJsonDeserializer<K extends Enum<K>, V extends Enum<V>> extends BaseMapJsonDeserializer<EnumBiMap<K, V>, K, V> {

    /**
     * @param keyDeserializer {@link EnumKeyDeserializer} used to deserialize the enum keys.
     * @param valueDeserializer {@link EnumJsonDeserializer} used to deserialize the enum values.
     * @param <K> Type of the enum keys inside the {@link EnumBiMap}
     * @param <V> Type of the enum values inside the {@link EnumBiMap}
     *
     * @return a new instance of {@link EnumBiMapJsonDeserializer}
     */
    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMapJsonDeserializer<K, V> newInstance( EnumKeyDeserializer<K>
                                                                                                              keyDeserializer,
                                                                                                      EnumJsonDeserializer<V>
                                                                                                              valueDeserializer ) {
        return new EnumBiMapJsonDeserializer<K, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * Class of the enum key
     */
    private final Class<K> enumKeyClass;

    /**
     * Class of the enum value
     */
    private final Class<V> enumValueClass;

    /**
     * @param keyDeserializer {@link EnumKeyDeserializer} used to deserialize the enum keys.
     * @param valueDeserializer {@link EnumJsonDeserializer} used to deserialize the enum values.
     */
    private EnumBiMapJsonDeserializer( EnumKeyDeserializer<K> keyDeserializer, EnumJsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
        this.enumKeyClass = keyDeserializer.getEnumClass();
        this.enumValueClass = valueDeserializer.getEnumClass();
    }

    @Override
    protected EnumBiMap<K, V> newMap() {
        return EnumBiMap.create( enumKeyClass, enumValueClass );
    }
}
