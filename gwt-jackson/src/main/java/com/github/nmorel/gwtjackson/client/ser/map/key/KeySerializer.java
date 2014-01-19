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
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;

/**
 * Base class for all the {@link Map} key serializer. It handles null values and exceptions. The rest is delegated to implementations.
 *
 * @author Nicolas Morel
 */
public abstract class KeySerializer<T> {

    /**
     * Serializes an object into a {@link String} to use as map's key.
     *
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     *
     * @return the key
     * @throws JsonSerializationException if an error occurs during the serialization
     */
    public String serialize( T value, JsonSerializationContext ctx ) throws JsonSerializationException {
        if ( null == value ) {
            return null;
        }
        return doSerialize( value, ctx );
    }

    /**
     * Serializes a non-null object into a {@link String} to use as map's key.
     *
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     *
     * @return the key
     */
    protected abstract String doSerialize( @Nonnull T value, JsonSerializationContext ctx );
}
