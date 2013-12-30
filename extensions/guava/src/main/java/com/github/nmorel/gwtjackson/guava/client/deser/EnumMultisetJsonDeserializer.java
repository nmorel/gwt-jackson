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
import com.google.common.collect.EnumMultiset;

/**
 * Default {@link JsonDeserializer} implementation for {@link EnumMultiset}.
 *
 * @param <E> Type of the enum inside the {@link EnumMultiset}
 *
 * @author Nicolas Morel
 */
public final class EnumMultisetJsonDeserializer<E extends Enum<E>> extends BaseMultisetJsonDeserializer<EnumMultiset<E>, E> {

    /**
     * @param deserializer {@link EnumJsonDeserializer} used to deserialize the enums inside the {@link EnumMultiset}.
     * @param <E> Type of the enum inside the {@link EnumMultiset}
     *
     * @return a new instance of {@link EnumMultisetJsonDeserializer}
     */
    public static <E extends Enum<E>> EnumMultisetJsonDeserializer<E> newInstance( EnumJsonDeserializer<E> deserializer ) {
        return new EnumMultisetJsonDeserializer<E>( deserializer );
    }

    /**
     * Class of the enum key
     */
    private final Class<E> enumClass;

    /**
     * @param deserializer {@link EnumJsonDeserializer} used to deserialize the enums inside the {@link EnumMultiset}.
     */
    private EnumMultisetJsonDeserializer( EnumJsonDeserializer<E> deserializer ) {
        super( deserializer );
        this.enumClass = deserializer.getEnumClass();
    }

    @Override
    protected EnumMultiset<E> newCollection() {
        return EnumMultiset.create( enumClass );
    }

    @Override
    protected boolean isNullValueAllowed() {
        return false;
    }
}
