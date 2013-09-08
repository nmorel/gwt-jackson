package com.github.nmorel.gwtjackson.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.logging.client.LogConfiguration;

/** @author Nicolas Morel */
public class JsonEncodingContext extends JsonMappingContext
{
    private static final Logger logger = Logger.getLogger( "JsonEncoding" );
    private final JsonWriter writer;

    public JsonEncodingContext( JsonWriter writer )
    {
        this.writer = writer;
    }

    @Override
    public Logger getLogger()
    {
        return logger;
    }

    public JsonEncodingException traceError( Object value, String message )
    {
        getLogger().log( Level.SEVERE, message );
        traceWriterInfo( value );
        return new JsonEncodingException( message );
    }

    public JsonEncodingException traceError( Object value, Exception cause )
    {
        getLogger().log( Level.SEVERE, "Error while encoding", cause );
        traceWriterInfo( value );
        return new JsonEncodingException( cause );
    }

    private void traceWriterInfo( Object value )
    {
        if ( LogConfiguration.loggingIsEnabled( Level.INFO ) )
        {
            getLogger().log( Level.INFO, "Error on value <" + value + ">. Current output : <" + writer.getCurrentString() + ">" );
        }
    }
}
