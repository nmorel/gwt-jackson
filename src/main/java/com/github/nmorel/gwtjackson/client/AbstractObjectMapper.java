package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractObjectMapper<T> implements ObjectMapper<T> {

    protected abstract JsonDeserializer<T> newDeserializer( JsonDecodingContext ctx );

    protected abstract JsonSerializer<T> newSerializer( JsonEncodingContext ctx );

    @Override
    public T decode( String in ) throws JsonDecodingException {
        JsonReader reader = new JsonReader( in );
        reader.setLenient( true );
        return decode( reader, new JsonDecodingContext( reader ) );
    }

    @Override
    public T decode( JsonReader reader, JsonDecodingContext ctx ) throws JsonDecodingException {
        try {
            return newDeserializer( ctx ).decode( reader, ctx );
        } catch ( JsonDecodingException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( e );
        }
    }

    @Override
    public String encode( T value ) throws JsonEncodingException {
        StringBuilder builder = new StringBuilder();
        JsonWriter writer = new JsonWriter( builder );
        writer.setLenient( true );
        writer.setSerializeNulls( false );
        encode( writer, value, new JsonEncodingContext( writer ) );
        return builder.toString();
    }

    @Override
    public void encode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws JsonEncodingException {
        try {
            newSerializer( ctx ).encode( writer, value, ctx );
        } catch ( JsonEncodingException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( value, e );
        }
    }
}
