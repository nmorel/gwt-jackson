package com.github.nmorel.gwtjackson.client;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base class for all the serializer. It handles null values and exceptions. The rest is delegated to implementations.
 *
 * @author Nicolas Morel
 */
public abstract class JsonSerializer<T> {

    /**
     * Serializes an object into JSON output.
     *
     * @param writer {@link JsonWriter} used to write the serialized JSON
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     *
     * @throws JsonSerializationException if an error occurs during the serialization
     */
    public void serialize( JsonWriter writer, T value, JsonSerializationContext ctx ) throws JsonSerializationException {
        try {
            if ( null == value ) {
                writer.nullValue();
                return;
            }
            doSerialize( writer, value, ctx );
        } catch ( IOException e ) {
            throw ctx.traceError( value, e );
        } catch ( JsonSerializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( value, e );
        }
    }

    /**
     * Serializes a non-null object into JSON output.
     *
     * @param writer {@link JsonWriter} used to write the serialized JSON
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     *
     * @throws IOException if an error occurs while writing the output
     */
    protected abstract void doSerialize( JsonWriter writer, @Nonnull T value, JsonSerializationContext ctx ) throws IOException;
}
