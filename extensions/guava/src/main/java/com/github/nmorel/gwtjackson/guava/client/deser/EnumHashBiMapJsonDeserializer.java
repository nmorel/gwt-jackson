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
import com.github.nmorel.gwtjackson.client.deser.map.key.EnumKeyDeserializer;
import com.google.common.collect.EnumHashBiMap;

/**
 * Default {@link JsonDeserializer} implementation for {@link EnumHashBiMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <E> Type of the enum keys inside the {@link EnumHashBiMap}
 * @param <V> Type of the values inside the {@link EnumHashBiMap}
 *
 * @author Nicolas Morel
 */
public final class EnumHashBiMapJsonDeserializer<E extends Enum<E>, V> extends BaseMapJsonDeserializer<EnumHashBiMap<E, V>, E, V> {

    /**
     * @param keyDeserializer {@link EnumKeyDeserializer} used to deserialize the enum keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <E> Type of the enum keys inside the {@link EnumHashBiMap}
     * @param <V> Type of the values inside the {@link EnumHashBiMap}
     *
     * @return a new instance of {@link EnumHashBiMapJsonDeserializer}
     */
    public static <E extends Enum<E>, V> EnumHashBiMapJsonDeserializer<E, V> newInstance( EnumKeyDeserializer<E> keyDeserializer,
                                                                                          JsonDeserializer<V> valueDeserializer ) {
        return new EnumHashBiMapJsonDeserializer<E, V>( keyDeserializer, valueDeserializer );
    }

    /**
     * Class of the enum key
     */
    private final Class<E> enumClass;

    /**
     * @param keyDeserializer {@link EnumKeyDeserializer} used to deserialize the enum keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private EnumHashBiMapJsonDeserializer( EnumKeyDeserializer<E> keyDeserializer, JsonDeserializer<V> valueDeserializer ) {
        super( keyDeserializer, valueDeserializer );
        this.enumClass = keyDeserializer.getEnumClass();
    }

    @Override
    protected EnumHashBiMap<E, V> newMap() {
        return EnumHashBiMap.create( enumClass );
    }
}
