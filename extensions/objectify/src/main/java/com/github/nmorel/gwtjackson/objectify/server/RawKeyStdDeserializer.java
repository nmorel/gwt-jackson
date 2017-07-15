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

package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyConstant;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class RawKeyStdDeserializer extends StdDeserializer<Key> {

    public RawKeyStdDeserializer() {
        super( Key.class );
    }

    @Override
    public Key deserialize( JsonParser jp, DeserializationContext ctxt ) throws IOException {
        JsonNode node = jp.readValueAsTree();
        Key parent = null;
        if ( node.has( RawKeyConstant.PARENT ) ) {
            JsonParser parentJsonParser = node.get( RawKeyConstant.PARENT ).traverse();
            parentJsonParser.setCodec( jp.getCodec() );
            parent = parentJsonParser.readValueAs( Key.class );
        }
        String kind = null;
        if ( node.has( RawKeyConstant.KIND ) ) {
            kind = node.get( RawKeyConstant.KIND ).asText();
        }
        long id = node.get( RawKeyConstant.ID ).asLong();

        if ( id != 0 ) {
            return KeyFactory.createKey( parent, kind, id );
        }

        String name = null;
        if ( node.has( RawKeyConstant.NAME ) ) {
            name = node.get( RawKeyConstant.NAME ).asText();
        }
        return KeyFactory.createKey( parent, kind, name );
    }
}
