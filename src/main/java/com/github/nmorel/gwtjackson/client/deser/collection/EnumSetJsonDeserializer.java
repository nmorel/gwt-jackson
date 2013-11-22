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

package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.EnumSet;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link EnumSet}.
 *
 * @param <E> Type of the enumeration inside the {@link EnumSet}
 *
 * @author Nicolas Morel
 */
public class EnumSetJsonDeserializer<E extends Enum<E>> extends BaseSetJsonDeserializer<EnumSet<E>, E> {

    /**
     * @param enumClass class of the enumeration
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link EnumSet}.
     * @param <E> Type of the enumeration inside the {@link EnumSet}
     *
     * @return a new instance of {@link EnumSetJsonDeserializer}
     */
    public static <E extends Enum<E>> EnumSetJsonDeserializer<E> newInstance( Class<E> enumClass, JsonDeserializer<E> deserializer ) {
        return new EnumSetJsonDeserializer<E>( enumClass, deserializer );
    }

    private final Class<E> enumClass;

    /**
     * @param enumClass class of the enumeration
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link EnumSet}.
     */
    private EnumSetJsonDeserializer( Class<E> enumClass, JsonDeserializer<E> deserializer ) {
        super( deserializer );
        if ( null == enumClass ) {
            throw new IllegalArgumentException( "enumClass can't be null" );
        }
        this.enumClass = enumClass;
    }

    @Override
    protected EnumSet<E> newCollection() {
        return EnumSet.noneOf( enumClass );
    }

    @Override
    protected boolean isNullValueAllowed() {
        return false;
    }
}
