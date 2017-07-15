package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.nmorel.gwtjackson.objectify.shared.RefConstant;
import com.googlecode.objectify.Ref;

public class RefStdSerializer extends StdSerializer<Ref> {

    public RefStdSerializer() {
        super( Ref.class );
    }

    @Override
    public void serialize( Ref ref, JsonGenerator jgen, SerializerProvider provider ) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField( RefConstant.KEY, ref.key() );
        jgen.writeObjectField( RefConstant.VALUE, ref.getValue() );
        jgen.writeEndObject();
    }

}
