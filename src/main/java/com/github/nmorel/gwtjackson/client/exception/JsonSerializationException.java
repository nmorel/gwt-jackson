package com.github.nmorel.gwtjackson.client.exception;

/**
 * Base exception for serialization process
 *
 * @author Nicolas Morel
 */
public class JsonSerializationException extends JsonMappingException {

    public JsonSerializationException() {
    }

    public JsonSerializationException( String message ) {
        super( message );
    }

    public JsonSerializationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public JsonSerializationException( Throwable cause ) {
        super( cause );
    }
}
