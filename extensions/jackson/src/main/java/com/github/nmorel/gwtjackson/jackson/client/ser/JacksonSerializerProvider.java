package com.github.nmorel.gwtjackson.jackson.client.ser;

import java.io.IOException;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.github.nmorel.gwtjackson.client.AbstractObjectWriter;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.core.client.GWT;

/**
 * Provides Serializers for Jackson serializers
 */
public class JacksonSerializerProvider extends SerializerProvider {

    JsonWriter writer;
    JsonSerializationContext context;
    JsonSerializerParameters parameters;

    @SuppressWarnings("rawtypes")
    com.github.nmorel.gwtjackson.client.JsonSerializer genericSerializer;

    static interface GenericJsonWriter extends ObjectWriter<Object> {
    }

    @SuppressWarnings("rawtypes")
    public JacksonSerializerProvider(JsonWriter writer, JsonSerializationContext context, JsonSerializerParameters parameters) {
        this.context = context;
        this.parameters = parameters;
        this.genericSerializer = (GWT.<AbstractObjectWriter> create(GenericJsonWriter.class)).getSerializer();
    }

    @Override
    public WritableObjectId findObjectId(Object forPojo, ObjectIdGenerator<?> generatorType) {
        ObjectIdGenerator<?> generator = context.findObjectIdGenerator(generatorType);
        return new WritableObjectId(generator);
    }

    @Override
    public JsonSerializer<Object> serializerInstance(Annotated annotated, Object serDef) throws JsonMappingException {
        return new JsonSerializer<Object>() {

            @SuppressWarnings("unchecked")
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                genericSerializer.serialize(writer, value, context, parameters);
            }

        };
    }

}
