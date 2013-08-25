package com.github.nmorel.gwtjackson.client.exception;

/** @author Nicolas Morel */
public class JsonMappingException extends RuntimeException
{
    public JsonMappingException()
    {
    }

    public JsonMappingException( String message )
    {
        super( message );
    }

    public JsonMappingException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public JsonMappingException( Throwable cause )
    {
        super( cause );
    }
}
