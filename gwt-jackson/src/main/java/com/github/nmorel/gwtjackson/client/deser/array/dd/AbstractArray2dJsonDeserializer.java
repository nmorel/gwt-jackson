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

package com.github.nmorel.gwtjackson.client.deser.array.dd;

import java.util.ArrayList;
import java.util.Collections;
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
 * @version $Id: $
 */
public abstract class AbstractArray2dJsonDeserializer<T> extends JsonDeserializer<T> {

    /**
     * Deserializes the array into a {@link List}. We need the length of the array before creating it.
     *
     * @param reader reader
     * @param ctx context of the deserialization process
     * @param deserializer deserializer for element inside the array
     * @param params Parameters for the deserializer
     * @param <C> type of the element inside the array
     * @return a list containing all the elements of the array
     */
    protected <C> List<List<C>> deserializeIntoList( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializer<C> deserializer,
                                                     JsonDeserializerParameters params ) {
        List<List<C>> list;

        reader.beginArray();
        JsonToken token = reader.peek();

        if ( JsonToken.END_ARRAY == token ) {

            // empty array, no need to create a list
            list = Collections.emptyList();

        } else {

            list = doDeserializeIntoList( reader, ctx, deserializer, params, token );

        }

        reader.endArray();
        return list;
    }

    /**
     * <p>doDeserializeIntoList</p>
     *
     * @param reader a {@link com.github.nmorel.gwtjackson.client.stream.JsonReader} object.
     * @param ctx a {@link com.github.nmorel.gwtjackson.client.JsonDeserializationContext} object.
     * @param deserializer a {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} object.
     * @param params a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     * @param token a {@link com.github.nmorel.gwtjackson.client.stream.JsonToken} object.
     * @param <C> a C object.
     * @return a {@link java.util.List} object.
     */
    protected <C> List<List<C>> doDeserializeIntoList( JsonReader reader, JsonDeserializationContext ctx,
                                                       JsonDeserializer<C> deserializer, JsonDeserializerParameters params,
                                                       JsonToken token ) {
        List<List<C>> list;
        list = new ArrayList<List<C>>();
        // we keep the size of the first inner list to initialize the next lists with the correct size
        int size = -1;

        while ( JsonToken.END_ARRAY != token ) {

            // Creating a new
            List<C> innerList;
            reader.beginArray();
            JsonToken innerToken = reader.peek();
            if ( JsonToken.END_ARRAY == innerToken ) {
                // empty array, no need to create a list
                innerList = Collections.emptyList();
            } else {
                if ( size >= 0 ) {
                    innerList = new ArrayList<C>( size );
                } else {
                    innerList = new ArrayList<C>();
                }
                while ( JsonToken.END_ARRAY != innerToken ) {
                    innerList.add( deserializer.deserialize( reader, ctx, params ) );
                    innerToken = reader.peek();
                }
                size = innerList.size();
            }
            reader.endArray();
            list.add( innerList );

            token = reader.peek();
        }
        return list;
    }
}
