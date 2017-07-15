package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.nmorel.gwtjackson.objectify.shared.KeyConstant;
import com.googlecode.objectify.Key;

public class KeyStdSerializer extends StdSerializer<Key> {

    public KeyStdSerializer() {
        super( Key.class );
    }

    @Override
    public void serialize( Key value, JsonGenerator jgen, SerializerProvider provider ) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField( KeyConstant.RAW, value.getRaw() );
        jgen.writeEndObject();
    }
}
