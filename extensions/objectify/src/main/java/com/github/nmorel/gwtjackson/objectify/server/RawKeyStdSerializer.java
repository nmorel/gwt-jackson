package com.github.nmorel.gwtjackson.objectify.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyConstant;
import com.google.appengine.api.datastore.Key;

public class RawKeyStdSerializer extends StdSerializer<Key> {

    public RawKeyStdSerializer() {
        super( Key.class );
    }

    @Override
    public void serialize( Key value, JsonGenerator jgen, SerializerProvider provider ) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField( RawKeyConstant.PARENT, value.getParent() );
        jgen.writeStringField( RawKeyConstant.KIND, value.getKind() );
        jgen.writeNumberField( RawKeyConstant.ID, value.getId() );
        jgen.writeStringField( RawKeyConstant.NAME, value.getName() );
        jgen.writeEndObject();
    }

}
