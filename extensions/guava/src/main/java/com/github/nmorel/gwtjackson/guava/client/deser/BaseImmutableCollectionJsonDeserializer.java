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

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.deser.collection.BaseIterableJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.google.common.collect.ImmutableCollection;

/**
 * Base {@link JsonDeserializer} implementation for {@link ImmutableCollection}.
 *
 * @param <C> {@link ImmutableCollection} type
 * @param <T> Type of the elements inside the {@link ImmutableCollection}
 *
 * @author Nicolas Morel
 */
public abstract class BaseImmutableCollectionJsonDeserializer<C extends ImmutableCollection<T>,
        T> extends BaseIterableJsonDeserializer<C, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link ImmutableCollection}.
     */
    public BaseImmutableCollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    /**
     * Build the {@link ImmutableCollection}. It delegates the element addition to an abstract method because the
     * {@link ImmutableCollection.Builder} is not visible in the emulated class.
     *
     * @param reader {@link JsonReader} used to read the JSON input
     * @param ctx Context for the full deserialization process
     * @param params Parameters for this deserialization
     */
    protected void buildCollection( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        if ( JsonToken.BEGIN_ARRAY == reader.peek() ) {

            reader.beginArray();
            while ( JsonToken.END_ARRAY != reader.peek() ) {
                T element = deserializer.deserialize( reader, ctx, params );
                if ( isNullValueAllowed() || null != element ) {
                    addToCollection( element );
                }
            }
            reader.endArray();

        } else if ( ctx.isAcceptSingleValueAsArray() ) {

            addToCollection( deserializer.deserialize( reader, ctx, params ) );

        } else {
            throw ctx.traceError( "Cannot deserialize a com.google.common.collect.ImmutableCollection out of " + reader
                    .peek() + " token", reader );
        }
    }

    /**
     * Add an element to the current collection
     *
     * @param element Element to add
     */
    protected abstract void addToCollection( T element );

    /**
     * @return true if the collection accepts null value
     */
    protected boolean isNullValueAllowed() {
        return false;
    }

    @Override
    public void setBackReference( String referenceName, Object reference, C value, JsonDeserializationContext ctx ) {
        if ( null != value && !value.isEmpty() ) {
            for ( T val : value ) {
                deserializer.setBackReference( referenceName, reference, val, ctx );
            }
        }
    }
}
