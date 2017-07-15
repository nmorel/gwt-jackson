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
import com.github.nmorel.gwtjackson.objectify.shared.KeyConstant;
import com.googlecode.objectify.Key;

public class KeyStdDeserializer extends StdDeserializer<Key> {

    public KeyStdDeserializer() {
        super( Key.class );
    }

    @Override
    public Key deserialize( JsonParser jp, DeserializationContext ctxt ) throws IOException {
        JsonNode node = jp.readValueAsTree();

        JsonParser rawJsonParser = node.get( KeyConstant.RAW ).traverse();
        rawJsonParser.setCodec( jp.getCodec() );
        com.google.appengine.api.datastore.Key key = rawJsonParser.readValueAs( com.google.appengine.api.datastore.Key.class );

        return Key.create( key );
    }
}
