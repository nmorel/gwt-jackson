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
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.appengine.api.datastore.Key;

public class RawKeyKeyDeserializer extends KeyDeserializer<Key> {

    private static final RawKeyKeyDeserializer INSTANCE = new RawKeyKeyDeserializer();

    public static RawKeyKeyDeserializer getInstance() {
        return INSTANCE;
    }

    private RawKeyKeyDeserializer() { }

    @Override
    protected Key doDeserialize( String key, JsonDeserializationContext ctx ) {
        JsonReader jsonReader = ctx.newJsonReader( key );
        return RawKeyJsonDeserializer.getInstance().deserialize( jsonReader, ctx );
    }
}