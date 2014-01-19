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

package com.github.nmorel.gwtjackson.client.deser.array;

import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link JsonDeserializer} for array.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractArrayJsonDeserializer<T> extends JsonDeserializer<T> {

    @Override
    public T doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        if ( JsonToken.BEGIN_ARRAY == reader.peek() ) {
            return doDeserializeArray( reader, ctx, params );
        } else {
            return doDeserializeNonArray( reader, ctx, params );
        }
    }

    protected abstract T doDeserializeArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params );

    protected T doDeserializeNonArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        if ( ctx.isAcceptSingleValueAsArray() ) {
            return doDeserializeSingleArray( reader, ctx, params );
        } else {
            throw ctx.traceError( "Cannot deserialize an array out of " + reader.peek() + " token", reader );
        }
    }

    protected abstract T doDeserializeSingleArray( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params );

    /**
     * Deserializes the array into a {@link List}. We need the length of the array before creating it.
     *
     * @param reader reader
     * @param ctx context of the deserialization process
     * @param deserializer deserializer for element inside the array
     * @param params Parameters for the deserializer
     * @param <C> type of the element inside the array
     *
     * @return a list containing all the elements of the array
     */
    protected <C> List<C> deserializeIntoList( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializer<C> deserializer,
                                               JsonDeserializerParameters params ) {
        List<C> list = new ArrayList<C>();
        reader.beginArray();
        while ( JsonToken.END_ARRAY != reader.peek() ) {
            list.add( deserializer.deserialize( reader, ctx, params ) );
        }
        reader.endArray();
        return list;
    }
}
