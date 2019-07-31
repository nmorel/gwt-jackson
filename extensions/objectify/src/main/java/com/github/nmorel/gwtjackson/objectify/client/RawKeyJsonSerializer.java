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
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyConstant;
import com.google.appengine.api.datastore.Key;

public class RawKeyJsonSerializer extends JsonSerializer<Key> {

    private static final RawKeyJsonSerializer INSTANCE = new RawKeyJsonSerializer();

    public static RawKeyJsonSerializer getInstance() {
        return INSTANCE;
    }

    private RawKeyJsonSerializer() { }

    @Override
    protected void doSerialize( JsonWriter writer, Key key, JsonSerializationContext ctx, JsonSerializerParameters params ) {
        writer.beginObject();
        if ( key.getParent() != null || ctx.isSerializeNulls() ) {
            writer.name( RawKeyConstant.PARENT );
            serialize( writer, key.getParent(), ctx, params );
        }
        if ( key.getKind() != null || ctx.isSerializeNulls() ) {
            writer.name( RawKeyConstant.KIND ).value( key.getKind() );
        }
        writer.name( RawKeyConstant.ID ).value( key.getId() );
        if ( key.getName() != null || ctx.isSerializeNulls() ) {
            writer.name( RawKeyConstant.NAME ).value( key.getName() );
        }
        writer.endObject();
    }
}
