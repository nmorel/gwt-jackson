package com.github.nmorel.gwtjackson.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.gwt.logging.client.LogConfiguration;

/** @author Nicolas Morel */
public class JsonDecodingContext extends JsonMappingContext
{
    private static final Logger logger = Logger.getLogger( "JsonDecoding" );

    @Override
    public Logger getLogger()
    {
        return logger;
    }

    public JsonDecodingException traceError( JsonReader reader, String message )
    {
        getLogger().log( Level.SEVERE, message );
        traceReaderInfo( reader );
        return new JsonDecodingException( message );
    }

    public JsonDecodingException traceError( JsonReader reader, Exception cause )
    {
        getLogger().log( Level.SEVERE, "Error while decoding", cause );
        traceReaderInfo( reader );
        return new JsonDecodingException( cause );
    }

    private void traceReaderInfo( JsonReader reader )
    {
        if ( LogConfiguration.loggingIsEnabled( Level.INFO ) )
        {
            getLogger().log( Level.INFO, "Error at line " + reader.getLineNumber() + " and column " + reader
                .getColumnNumber() + " of input <" + reader.getInput() + ">" );
        }
    }
}
