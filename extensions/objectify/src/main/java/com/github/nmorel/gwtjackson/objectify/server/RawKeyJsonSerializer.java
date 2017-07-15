package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.appengine.api.datastore.Key;

public class RawKeyJsonSerializer extends JsonSerializer<Key> {

    private final RawKeyStdSerializer rawKeyStdSerializer = new RawKeyStdSerializer();

    @Override
    public void serialize( Key value, JsonGenerator jgen, SerializerProvider provider ) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jgen.getCodec().getFactory().createGenerator( writer );
        rawKeyStdSerializer.serialize( value, jsonGenerator, provider );
        jsonGenerator.close();
        jgen.writeFieldName( writer.toString() );
    }
}
