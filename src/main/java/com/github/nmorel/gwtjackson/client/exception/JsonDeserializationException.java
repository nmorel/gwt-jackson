package com.github.nmorel.gwtjackson.client.exception;

/**
 * Base exception for deserialization process
 *
 * @author Nicolas Morel
 */
public class JsonDeserializationException extends JsonMappingException {

    public JsonDeserializationException() {
    }

    public JsonDeserializationException( String message ) {
        super( message );
    }

    public JsonDeserializationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public JsonDeserializationException( Throwable cause ) {
        super( cause );
    }
}
