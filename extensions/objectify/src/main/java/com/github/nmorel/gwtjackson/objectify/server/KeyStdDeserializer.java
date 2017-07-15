package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
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
