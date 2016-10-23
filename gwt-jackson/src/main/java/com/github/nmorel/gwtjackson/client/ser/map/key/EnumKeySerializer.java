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

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Default {@link KeySerializer} implementation for {@link Enum}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class EnumKeySerializer<E extends Enum<E>> extends KeySerializer<E> {

    private static final EnumKeySerializer<?> INSTANCE = new EnumKeySerializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link EnumKeySerializer}
     * @param <S> a S object.
     */
    @SuppressWarnings( "unchecked" )
    public static <S extends EnumKeySerializer<?>> S getInstance() {
        return (S) INSTANCE;
    }

    private EnumKeySerializer() { }

    /** {@inheritDoc} */
    @Override
    public boolean mustBeEscaped( JsonSerializationContext ctx ) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    protected String doSerialize( E value, JsonSerializationContext ctx ) {
        return value.name();
    }
}
