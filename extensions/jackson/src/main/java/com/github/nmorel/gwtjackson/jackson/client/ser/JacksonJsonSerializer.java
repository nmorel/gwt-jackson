package com.github.nmorel.gwtjackson.jackson.client.ser;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Serializes a value by using a supplied Jackson JsonSerializer 
 *
 * @param <T> The type this Serializer serializes
 */
public abstract class JacksonJsonSerializer<T> extends JsonSerializer<T> {

    com.fasterxml.jackson.databind.JsonSerializer<T> jacksonSerializer;

    public JacksonJsonSerializer(com.fasterxml.jackson.databind.JsonSerializer<T> jacksonSerializer) {
        this.jacksonSerializer = jacksonSerializer;
    }

    @Override
    protected void doSerialize(JsonWriter writer,
                               T value,
                               JsonSerializationContext ctx,
                               JsonSerializerParameters params) {
        try {
            jacksonSerializer.serialize(value, new JacksonGeneratorImpl(writer),
                                        new JacksonSerializerProvider(writer, ctx, params));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
