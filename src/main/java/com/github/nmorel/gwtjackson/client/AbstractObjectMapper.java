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
        JsonReader reader = new JsonReader( in );
        return read( reader, new JsonDeserializationContext( reader ) );
    }

    @Override
    public T read( JsonReader reader, JsonDeserializationContext ctx ) throws JsonDeserializationException {
        try {
            return newDeserializer( ctx ).deserialize( reader, ctx );
        } catch ( JsonDeserializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( e );
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
        StringBuilder builder = new StringBuilder();
        JsonWriter writer = new JsonWriter( builder );
        writer.setSerializeNulls( false );
        write( writer, value, new JsonSerializationContext( writer ) );
        return builder.toString();
    }

    @Override
    public void write( JsonWriter writer, T value, JsonSerializationContext ctx ) throws JsonSerializationException {
        try {
            newSerializer( ctx ).serialize( writer, value, ctx );
        } catch ( JsonSerializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( value, e );
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
