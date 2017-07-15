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
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyConstant;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class RawKeyJsonDeserializer extends JsonDeserializer<Key> {

    private static final RawKeyJsonDeserializer INSTANCE = new RawKeyJsonDeserializer();

    public static RawKeyJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private RawKeyJsonDeserializer() { }

    @Override
    protected Key doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        reader.beginObject();
        Key parent = null;
        String kind = null;
        Long id = null;
        String name = null;

        while ( reader.hasNext() ) {
            String nextName = reader.nextName();
            if ( RawKeyConstant.PARENT.equals( nextName ) ) {
                parent = deserialize( reader, ctx );

            } else if ( RawKeyConstant.KIND.equals( nextName ) ) {
                kind = reader.nextString();

            } else if ( RawKeyConstant.ID.equals( nextName ) ) {
                id = reader.nextLong();

            } else if ( RawKeyConstant.NAME.equals( nextName ) ) {
                try {
                    name = reader.nextString();
                } catch ( Exception e ) {
                    reader.nextNull();
                }

            } else {
                throw ctx.traceError( "Unknown " + nextName + " property.", reader );
            }
        }

        reader.endObject();

        if ( id == null ) {
            throw ctx.traceError( "Missing " + RawKeyConstant.ID + " property.", reader );
        } else if ( id != 0 ) {
            return KeyFactory.createKey( parent, kind, id );
        }

        return KeyFactory.createKey( parent, kind, name );
    }
}
