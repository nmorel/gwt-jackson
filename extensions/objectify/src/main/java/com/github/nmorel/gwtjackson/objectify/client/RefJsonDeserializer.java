/*
 * Copyright 2017 Nicolas Morel
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

package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.objectify.shared.RefConstant;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.impl.ref.DeadRef;

public class RefJsonDeserializer<T> extends JsonDeserializer<Ref<T>> {

    public static <T> RefJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new RefJsonDeserializer<T>( deserializer );
    }

    private final JsonDeserializer<T> deserializer;

    private RefJsonDeserializer( JsonDeserializer<T> deserializer ) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer can't be null" );
        }
        this.deserializer = deserializer;
    }

    @Override
    protected Ref<T> doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        reader.beginObject();
        Key<T> key = null;
        T value = null;
        while ( reader.hasNext() ) {
            String nextName = reader.nextName();
            if ( RefConstant.KEY.equals( nextName ) ) {
                key = KeyJsonDeserializer.<T>getInstance().deserialize( reader, ctx, params );

            } else if ( RefConstant.VALUE.equals( nextName ) ) {
                value = deserializer.deserialize( reader, ctx, params );

            }
        }
        reader.endObject();
        if ( key == null ) {
            throw ctx.traceError( "Missing " + RefConstant.KEY + " property.", reader );
        }
        return new DeadRef<T>( key, value );
    }
}