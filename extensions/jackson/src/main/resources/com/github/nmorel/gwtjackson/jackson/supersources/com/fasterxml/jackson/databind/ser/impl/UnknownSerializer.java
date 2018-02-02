package com.fasterxml.jackson.databind.ser.impl;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

@SuppressWarnings("serial")
public class UnknownSerializer
        extends JsonSerializer<Object> {

    public UnknownSerializer() {

    }

    public UnknownSerializer(Object o) {

    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // 27-Nov-2009, tatu: As per [JACKSON-201] may or may not fail...
        // But if it's fine, we'll just output empty JSON Object:
        gen.writeStartObject();
        gen.writeEndObject();
    }

    @Override
    public final void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider,
            TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForObject(value, gen);
        typeSer.writeTypeSuffixForObject(value, gen);
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, Object value) {
        return true;
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
            throws JsonMappingException {
        visitor.expectAnyFormat(typeHint);
    }

    protected void failForEmpty(SerializerProvider prov, Object value)
            throws JsonMappingException {
        prov.reportMappingProblem(
                "No serializer found for class %s and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)",
                value.getClass().getName());
    }
}
