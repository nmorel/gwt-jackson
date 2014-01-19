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

package com.github.nmorel.gwtjackson.client.ser.array;

import javax.annotation.Nonnull;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for array.
 *
 * @param <T> Type of the elements inside the array
 *
 * @author Nicolas Morel
 */
public class ArrayJsonSerializer<T> extends JsonSerializer<T[]> {

    /**
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the array.
     * @param <T> Type of the elements inside the array
     *
     * @return a new instance of {@link ArrayJsonSerializer}
     */
    public static <T> ArrayJsonSerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return new ArrayJsonSerializer<T>( serializer );
    }

    private final JsonSerializer<T> serializer;

    /**
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the array.
     */
    protected ArrayJsonSerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull T[] values, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        if ( !ctx.isWriteEmptyJsonArrays() && values.length == 0 ) {
            writer.cancelName();
            return;
        }

        if ( ctx.isWriteSingleElemArraysUnwrapped() && values.length == 1 ) {
            serializer.serialize( writer, values[0], ctx, params );
        } else {
            writer.beginArray();
            for ( T value : values ) {
                serializer.serialize( writer, value, ctx, params );
            }
            writer.endArray();
        }
    }
}
