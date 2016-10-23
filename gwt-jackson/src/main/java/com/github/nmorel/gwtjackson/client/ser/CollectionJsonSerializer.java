/*
 * Copyright 2015 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.ser;

import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonSerializer} implementation for {@link Collection}.
 *
 * @param <T> Type of the elements inside the {@link Collection}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class CollectionJsonSerializer<C extends Collection<T>, T> extends JsonSerializer<C> {

    /**
     * <p>newInstance</p>
     *
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the {@link Collection}.
     * @return a new instance of {@link CollectionJsonSerializer}
     * @param <C> a C object.
     */
    public static <C extends Collection<?>> CollectionJsonSerializer<C, ?> newInstance( JsonSerializer<?> serializer ) {
        return new CollectionJsonSerializer( serializer );
    }

    protected final JsonSerializer<T> serializer;

    /**
     * <p>Constructor for CollectionJsonSerializer.</p>
     *
     * @param serializer {@link JsonSerializer} used to serialize the objects inside the {@link Collection}.
     */
    protected CollectionJsonSerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    /** {@inheritDoc} */
    @Override
    protected boolean isEmpty( C value ) {
        return null == value || value.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public void doSerialize( JsonWriter writer, C values, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        if ( values.isEmpty() ) {
            if ( ctx.isWriteEmptyJsonArrays() ) {
                writer.beginArray();
                writer.endArray();
            } else {
                writer.cancelName();
            }
            return;
        }

        if ( ctx.isWriteSingleElemArraysUnwrapped() && values.size() == 1 ) {
            // there is only one element, we write it directly
            serializer.serialize( writer, values.iterator().next(), ctx, params );
        } else {
            writer.beginArray();
            for ( T value : values ) {
                serializer.serialize( writer, value, ctx, params );
            }
            writer.endArray();
        }
    }
}
