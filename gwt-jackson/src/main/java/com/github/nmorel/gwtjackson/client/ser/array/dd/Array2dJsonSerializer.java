/*
 * Copyright 2014 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.ser.array.dd;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for 2D array.
 *
 * @param <T> Type of the elements inside the array
 *
 * @author Nicolas Morel
 */
public class Array2dJsonSerializer<T> extends JsonSerializer<T[][]> {

    /**
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the array.
     * @param <T> Type of the elements inside the array
     *
     * @return a new instance of {@link Array2dJsonSerializer}
     */
    public static <T> Array2dJsonSerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return new Array2dJsonSerializer<T>( serializer );
    }

    private final JsonSerializer<T> serializer;

    /**
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the array.
     */
    protected Array2dJsonSerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    @Override
    protected boolean isEmpty( T[][] value ) {
        return null == value || value.length == 0;
    }

    @Override
    public void doSerialize( JsonWriter writer, T[][] values, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        if ( !ctx.isWriteEmptyJsonArrays() && values.length == 0 ) {
            writer.cancelName();
            return;
        }

        writer.beginArray();
        for ( T[] array : values ) {
            writer.beginArray();
            for ( T value : array ) {
                serializer.serialize( writer, value, ctx, params );
            }
            writer.endArray();
        }
        writer.endArray();
    }
}
