package com.github.nmorel.gwtjackson.client;

import javax.annotation.Nonnull;
import java.io.IOException;

import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public abstract class JsonSerializer<T> {

    /**
     * Encodes an object into JSON output.
     *
     * @param writer {@link JsonWriter} used to write the JSON output
     * @param value Object to encode
     * @param ctx Context for the full encoding process
     *
     * @throws JsonEncodingException if an exception occurs while encoding the output
     */
    public void encode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws JsonEncodingException {
        try {
            if ( null == value ) {
                writer.nullValue();
                return;
            }
            doEncode( writer, value, ctx );
        } catch ( IOException e ) {
            throw ctx.traceError( value, e );
        } catch ( JsonEncodingException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( value, e );
        }
    }

    protected abstract void doEncode( JsonWriter writer, @Nonnull T value, JsonEncodingContext ctx ) throws IOException;
}
