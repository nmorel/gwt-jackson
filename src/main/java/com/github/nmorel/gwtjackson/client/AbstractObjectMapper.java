package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link ObjectMapper}. It delegates the serialization/deserialization to a serializer/deserializer.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractObjectMapper<T> implements ObjectMapper<T> {

    @Override
    public T read( String in ) throws JsonDeserializationException {
        return read( in, new JsonDeserializationContext.Builder().build() );
    }

    @Override
    public T read( String in, JsonDeserializationContext ctx ) throws JsonDeserializationException {
        JsonReader reader = ctx.newJsonReader( in );
        try {
            return newDeserializer( ctx ).deserialize( reader, ctx );
        } catch ( JsonDeserializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( e, reader );
        }
    }

    /**
     * Instantiates a new deserializer
     *
     * @param ctx Context of the current deserialization process
     *
     * @return a new deserializer
     */
    protected abstract JsonDeserializer<T> newDeserializer( JsonDeserializationContext ctx );

    @Override
    public String write( T value ) throws JsonSerializationException {
        return write( value, new JsonSerializationContext.Builder().build() );
    }

    @Override
    public String write( T value, JsonSerializationContext ctx ) throws JsonSerializationException {
        JsonWriter writer = ctx.newJsonWriter();
        try {
            newSerializer( ctx ).serialize( writer, value, ctx );
            return writer.getOutput();
        } catch ( JsonSerializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( value, e, writer );
        }
    }

    /**
     * Instantiates a new serializer
     *
     * @param ctx Context of the current serialization process
     *
     * @return a new serializer
     */
    protected abstract JsonSerializer<T> newSerializer( JsonSerializationContext ctx );
}
