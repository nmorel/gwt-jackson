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

package com.github.nmorel.gwtjackson.guava.client.ser;

import javax.annotation.Nonnull;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.common.base.Optional;

/**
 * Default {@link JsonSerializer} implementation for {@link Optional}.
 *
 * @param <T> Type of the element inside the {@link Optional}
 *
 * @author Nicolas Morel
 */
public final class OptionalJsonSerializer<T> extends JsonSerializer<Optional<T>> {

    /**
     * @param serializer {@link JsonSerializer} used to serialize the object inside the {@link Optional}.
     * @param <T> Type of the element inside the {@link Optional}
     *
     * @return a new instance of {@link OptionalJsonSerializer}
     */
    public static <T> OptionalJsonSerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return new OptionalJsonSerializer<T>( serializer );
    }

    private final JsonSerializer<T> serializer;

    /**
     * @param serializer {@link JsonSerializer} used to serialize the object inside the {@link Optional}.
     */
    private OptionalJsonSerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    @Override
    protected void doSerialize( JsonWriter writer, @Nonnull Optional<T> value, JsonSerializationContext ctx,
                                JsonSerializerParameters params ) {
        if ( value.isPresent() ) {
            serializer.serialize( writer, value.get(), ctx, params );
        } else if ( writer.getSerializeNulls() ) {
            writer.nullValue();
        } else {
            writer.setSerializeNulls( true );
            writer.nullValue();
            writer.setSerializeNulls( false );
        }
    }
}
