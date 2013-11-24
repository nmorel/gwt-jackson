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

package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;

/**
 * Default {@link KeyDeserializer} implementation for {@link Enum}.
 *
 * @param <E> Type of the enum
 *
 * @author Nicolas Morel
 */
public final class EnumKeyDeserializer<E extends Enum<E>> extends KeyDeserializer<E> {

    /**
     * @param enumClass class of the enumeration
     * @param <E> Type of the enum
     *
     * @return a new instance of {@link EnumKeyDeserializer}
     */
    public static <E extends Enum<E>> EnumKeyDeserializer<E> newInstance( Class<E> enumClass ) {
        return new EnumKeyDeserializer<E>( enumClass );
    }

    private final Class<E> enumClass;

    /**
     * @param enumClass class of the enumeration
     */
    private EnumKeyDeserializer( Class<E> enumClass ) {
        if ( null == enumClass ) {
            throw new IllegalArgumentException( "enumClass cannot be null" );
        }
        this.enumClass = enumClass;
    }

    @Override
    protected E doDeserialize( String key, JsonDeserializationContext ctx ) {
        return Enum.valueOf( enumClass, key );
    }
}
