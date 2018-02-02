package com.github.nmorel.gwtjackson.jackson.shared;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PersonDeserializer extends JsonDeserializer<Person> {

    @Override
    public Person deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode tree = p.readValueAsTree();
        String firstName = tree.get("firstName").textValue();
        String lastName = tree.get("lastName").textValue();
        return new Person(firstName, lastName);
    }

}
