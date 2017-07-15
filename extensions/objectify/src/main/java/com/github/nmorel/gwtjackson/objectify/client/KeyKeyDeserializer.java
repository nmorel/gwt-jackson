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
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.googlecode.objectify.Key;

public class KeyKeyDeserializer<T> extends KeyDeserializer<Key<T>> {

    public static <T> KeyKeyDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new KeyKeyDeserializer<T>( deserializer );
    }

    private final KeyJsonDeserializer<T> deserializer;

    private KeyKeyDeserializer(JsonDeserializer<T> deserializer) {
        if ( null == deserializer ) {
            throw new IllegalArgumentException( "deserializer can't be null" );
        }
        this.deserializer = KeyJsonDeserializer.newInstance( deserializer );
    }

    @Override
    protected Key<T> doDeserialize( String key, JsonDeserializationContext ctx ) {
        JsonReader jsonReader = ctx.newJsonReader( key );
        return deserializer.deserialize( jsonReader, ctx );
    }
}
