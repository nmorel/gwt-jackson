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

        JsonParser parentJsonParser = node.get( RawKeyConstant.PARENT ).traverse();
        parentJsonParser.setCodec( jp.getCodec() );
        Key parent = parentJsonParser.readValueAs( Key.class );
        String kind = node.get( RawKeyConstant.KIND ).asText();
        long id = node.get( RawKeyConstant.ID ).asLong();

        if ( id != 0 ) {
            return KeyFactory.createKey( parent, kind, id );
        }

        return KeyFactory.createKey( parent, kind, node.get( RawKeyConstant.NAME ).asText() );
    }
}
