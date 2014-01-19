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

import java.util.ArrayList;
import java.util.Collection;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link JsonDeserializer} implementation for {@link Iterable}. The deserialization process returns an {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link Iterable}
 *
 * @author Nicolas Morel
 */
public class IterableJsonDeserializer<T> extends BaseIterableJsonDeserializer<Iterable<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Iterable}.
     * @param <T> Type of the elements inside the {@link Iterable}
     *
     * @return a new instance of {@link IterableJsonDeserializer}
     */
    public static <T> IterableJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new IterableJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Iterable}.
     */
    private IterableJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    public Iterable<T> doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        if ( JsonToken.BEGIN_ARRAY == reader.peek() ) {

            Collection<T> result = new ArrayList<T>();

            reader.beginArray();
            while ( JsonToken.END_ARRAY != reader.peek() ) {
                result.add( deserializer.deserialize( reader, ctx, params ) );
            }
            reader.endArray();
            return result;

        } else if ( ctx.isAcceptSingleValueAsArray() ) {

            Collection<T> result = new ArrayList<T>();
            result.add( deserializer.deserialize( reader, ctx, params ) );
            return result;

        } else {
            throw ctx.traceError( "Cannot deserialize a java.lang.Iterable out of " + reader.peek() + " token", reader );
        }
    }
}
