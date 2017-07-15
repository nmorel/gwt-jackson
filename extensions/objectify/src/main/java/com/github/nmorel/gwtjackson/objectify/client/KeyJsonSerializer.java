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

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.objectify.shared.KeyConstant;
import com.googlecode.objectify.Key;

public class KeyJsonSerializer<T> extends JsonSerializer<Key<T>> {

    private static final KeyJsonSerializer INSTANCE = new KeyJsonSerializer();

    public static <T> KeyJsonSerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return INSTANCE;
    }

    public static KeyJsonSerializer getInstance() {
        return INSTANCE;
    }

    private KeyJsonSerializer() {
    }

    @Override
    protected void doSerialize( JsonWriter writer, Key<T> key, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.beginObject();
        writer.name( KeyConstant.RAW );
        RawKeyJsonSerializer.getInstance().serialize( writer, key.getRaw(), ctx, params );
        writer.endObject();
    }
}