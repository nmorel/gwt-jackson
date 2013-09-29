package com.github.nmorel.gwtjackson.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * @author Nicolas Morel
 */
public abstract class JsonDeserializer<T> {

    /**
     * Decodes a JSON input into an object.
     *
     * @param reader {@link JsonReader} used to read the JSON input
     * @param ctx Context for the full decoding process
     *
     * @return the decoded object
     * @throws JsonDecodingException if an exception occurs while decoding the input
     */
    public T decode( JsonReader reader, JsonDecodingContext ctx ) throws JsonDecodingException {
        try {
            if ( JsonToken.NULL.equals( reader.peek() ) ) {
                reader.skipValue();
                return null;
            }
            return doDecode( reader, ctx );
        } catch ( IOException e ) {
            throw ctx.traceError( e );
        } catch ( JsonDecodingException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( e );
        }
    }

    protected abstract T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException;

    /**
     * Set the back reference.
     *
     * @param referenceName name of the reference
     * @param reference reference to set
     * @param value value to set the reference to.
     * @param ctx Context for the full decoding process
     *
     * @see com.fasterxml.jackson.annotation.JsonBackReference
     */
    public void setBackReference( String referenceName, Object reference, T value, JsonDecodingContext ctx ) {
        throw new JsonDecodingException( "Cannot set a back reference to the type mapped by this mapper" );
    }
}
