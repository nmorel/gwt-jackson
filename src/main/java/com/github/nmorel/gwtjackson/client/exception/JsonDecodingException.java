package com.github.nmorel.gwtjackson.client.exception;

/** @author Nicolas Morel */
public class JsonDecodingException extends JsonMappingException
{
    public JsonDecodingException()
    {
    }

    public JsonDecodingException( String message )
    {
        super( message );
    }

    public JsonDecodingException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public JsonDecodingException( Throwable cause )
    {
        super( cause );
    }
}
