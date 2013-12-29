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
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.deser.collection.BaseIterableJsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableCollection.Builder;

/**
 * Base {@link JsonDeserializer} implementation for {@link ImmutableCollection}.
 *
 * @param <C> {@link ImmutableCollection} type
 * @param <T> Type of the elements inside the {@link ImmutableCollection}
 *
 * @author Nicolas Morel
 */
public abstract class BaseImmutableCollectionJsonDeserializer<C extends ImmutableCollection<T>, T> extends BaseIterableJsonDeserializer<C, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link ImmutableCollection}.
     */
    public BaseImmutableCollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    protected void buildCollection( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                            Builder<T> result ) throws IOException {
        if ( JsonToken.BEGIN_ARRAY == reader.peek() ) {

            reader.beginArray();
            while ( JsonToken.END_ARRAY != reader.peek() ) {
                T element = deserializer.deserialize( reader, ctx, params );
                if ( isNullValueAllowed() || null != element ) {
                    result.add( element );
                }
            }
            reader.endArray();

        } else if ( ctx.isAcceptSingleValueAsArray() ) {

            result.add( deserializer.deserialize( reader, ctx, params ) );

        } else {
            throw ctx.traceError( "Cannot deserialize a com.google.common.collect.ImmutableCollection out of " + reader.peek() + " token", reader );
        }
    }

    /**
     * @return true if the collection accepts null value
     */
    protected boolean isNullValueAllowed() {
        return true;
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
