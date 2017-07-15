package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.googlecode.objectify.Ref;

public class RefJsonSerializer extends JsonSerializer<Ref> {

    private final RefStdSerializer refStdSerializer = new RefStdSerializer();

    @Override
    public void serialize( Ref value, JsonGenerator jgen, SerializerProvider provider ) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jgen.getCodec().getFactory().createGenerator( writer );
        refStdSerializer.serialize( value, jsonGenerator, provider );
        jsonGenerator.close();
        jgen.writeFieldName( writer.toString() );
    }
}
