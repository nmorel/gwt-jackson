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
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.googlecode.objectify.Ref;

public class RefKeySerializer<T> extends KeySerializer<Ref<T>> {

    public static <T> RefKeySerializer<T> newInstance( JsonSerializer<T> serializer ) {
        return new RefKeySerializer<T>( serializer );
    }

    private final JsonSerializer<T> serializer;

    private RefKeySerializer( JsonSerializer<T> serializer ) {
        if ( null == serializer ) {
            throw new IllegalArgumentException( "serializer cannot be null" );
        }
        this.serializer = serializer;
    }

    @Override
    protected String doSerialize( Ref<T> value, JsonSerializationContext ctx ) {
        JsonWriter jsonWriter = ctx.newJsonWriter();
        RefJsonSerializer.newInstance( serializer ).serialize( jsonWriter, value, ctx );
        jsonWriter.close();
        return jsonWriter.getOutput();
    }
}
