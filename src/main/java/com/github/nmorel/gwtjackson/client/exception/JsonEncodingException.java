package com.github.nmorel.gwtjackson.client.exception;

/** @author Nicolas Morel */
public class JsonEncodingException extends JsonMappingException
{
    public JsonEncodingException()
    {
    }

    public JsonEncodingException( String message )
    {
        super( message );
    }

    public JsonEncodingException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public JsonEncodingException( Throwable cause )
    {
        super( cause );
    }
}
