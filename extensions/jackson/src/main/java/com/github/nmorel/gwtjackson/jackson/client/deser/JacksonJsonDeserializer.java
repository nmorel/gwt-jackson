package com.github.nmorel.gwtjackson.jackson.client.deser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

public abstract class JacksonJsonDeserializer<T> extends JsonDeserializer<T> {

    com.fasterxml.jackson.databind.JsonDeserializer<T> jacksonDeserializer;

    public JacksonJsonDeserializer(com.fasterxml.jackson.databind.JsonDeserializer<T> jacksonDeserializer) {
        this.jacksonDeserializer = jacksonDeserializer;
    }

    @Override
    protected T doDeserialize(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        try {
            return jacksonDeserializer.deserialize(new JacksonJsonParser(reader), new JacksonDeserializationContext(ctx,
                    params));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
