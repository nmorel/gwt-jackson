package com.github.nmorel.gwtjackson.jackson.shared;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PersonSerializer extends JsonSerializer<Person> {

    @Override
    public void serialize(Person person, JsonGenerator gen, SerializerProvider serializers) throws IOException,
            JsonProcessingException {
        gen.writeStartObject();
        gen.writeStringField("firstName", person.getFirstName());
        gen.writeStringField("lastName", person.getLastName());
        gen.writeEndObject();
    }

}
