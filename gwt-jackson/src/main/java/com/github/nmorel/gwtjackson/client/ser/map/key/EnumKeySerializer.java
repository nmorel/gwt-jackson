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

package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Default {@link KeySerializer} implementation for {@link Enum}.
 *
 * @author Nicolas Morel
 */
public final class EnumKeySerializer<E extends Enum<E>> extends KeySerializer<E> {

    private static final EnumKeySerializer<?> INSTANCE = new EnumKeySerializer();

    /**
     * @return an instance of {@link EnumKeySerializer}
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> EnumKeySerializer<E> getInstance() {
        return (EnumKeySerializer<E>) INSTANCE;
    }

    private EnumKeySerializer() { }

    @Override
    protected String doSerialize( @Nonnull E value, JsonSerializationContext ctx ) {
        return value.name();
    }
}
