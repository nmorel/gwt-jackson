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

package com.github.nmorel.gwtjackson.guava.client.deser;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.common.base.Optional;

/**
 * Default {@link JsonDeserializer} implementation for {@link Optional}.
 *
 * @param <T> Type of the element inside the {@link Optional}
 *
 * @author Nicolas Morel
 */
public final class OptionalJsonDeserializer<T> extends JsonDeserializer<Optional<T>> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the object inside the {@link Optional}.
     * @param <T> Type of the element inside the {@link Optional}
     *
     * @return a new instance of {@link OptionalJsonDeserializer}
     */
    public static <T> OptionalJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new OptionalJsonDeserializer<T>( deserializer );
    }

    private final JsonDeserializer<T> deserializer;

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the object inside the {@link Optional}.
     */
    private OptionalJsonDeserializer( JsonDeserializer<T> deserializer ) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer can't be null" );
        }
        this.deserializer = deserializer;
    }

    @Override
    protected Optional<T> deserializeNullValue( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        reader.skipValue();
        return Optional.absent();
    }

    @Override
    public Optional<T> doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return Optional.of( deserializer.deserialize( reader, ctx ) );
    }

    @Override
    public void setBackReference( String referenceName, Object reference, Optional<T> value, JsonDeserializationContext ctx ) {
        if ( null != value && value.isPresent() ) {
            deserializer.setBackReference( referenceName, reference, value.get(), ctx );
        }
    }
}
