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

    @Override
    public Logger getLogger()
    {
        return logger;
    }

    public JsonEncodingException traceError( JsonWriter writer, Object value, String message )
    {
        getLogger().log( Level.SEVERE, message );
        traceWriterInfo( writer, value );
        return new JsonEncodingException( message );
    }

    public JsonEncodingException traceError( JsonWriter writer, Object value, Exception cause )
    {
        getLogger().log( Level.SEVERE, "Error while encoding", cause );
        traceWriterInfo( writer, value );
        return new JsonEncodingException( cause );
    }

    private void traceWriterInfo( JsonWriter writer, Object value )
    {
        if ( LogConfiguration.loggingIsEnabled( Level.INFO ) )
        {
            getLogger().log( Level.INFO, "Error on value <" + value + ">. Current output : <" + writer.getCurrentString() + ">" );
        }
    }
}
