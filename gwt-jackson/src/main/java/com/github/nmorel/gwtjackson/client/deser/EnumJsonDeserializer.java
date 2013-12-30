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

package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonDeserializer} implementation for {@link Enum}.
 *
 * @param <E> Type of the enum
 *
 * @author Nicolas Morel
 */
public class EnumJsonDeserializer<E extends Enum<E>> extends JsonDeserializer<E> {

    /**
     * @param enumClass class of the enumeration
     * @param <E> Type of the enum
     *
     * @return a new instance of {@link EnumJsonDeserializer}
     */
    public static <E extends Enum<E>> EnumJsonDeserializer<E> newInstance( Class<E> enumClass ) {
        return new EnumJsonDeserializer<E>( enumClass );
    }

    private final Class<E> enumClass;

    /**
     * @param enumClass class of the enumeration
     */
    protected EnumJsonDeserializer( Class<E> enumClass ) {
        if ( null == enumClass ) {
            throw new IllegalArgumentException( "enumClass cannot be null" );
        }
        this.enumClass = enumClass;
    }

    @Override
    public E doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) throws IOException {
        return Enum.valueOf( enumClass, reader.nextString() );
    }

    public Class<E> getEnumClass() {
        return enumClass;
    }
}
