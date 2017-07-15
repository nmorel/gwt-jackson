package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.googlecode.objectify.Key;

public class KeyStdKeyDeserializer extends StdKeyDeserializer {

    private final KeyStdDeserializer keyJsonDeserializer = new KeyStdDeserializer();

    public KeyStdKeyDeserializer() {
        super( StdKeyDeserializer.TYPE_CLASS, Key.class );
    }

    @Override
    public Object deserializeKey( String key, DeserializationContext ctxt ) throws IOException {
        JsonParser jsonParser = ctxt.getParser().getCodec().getFactory().createParser( key );
        return keyJsonDeserializer.deserialize( jsonParser, ctxt );
    }
}
