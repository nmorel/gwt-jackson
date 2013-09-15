package com.github.nmorel.gwtjackson.client;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.gwt.logging.client.LogConfiguration;

/** @author Nicolas Morel */
public class JsonDecodingContext extends JsonMappingContext
{
    private static final Logger logger = Logger.getLogger( "JsonDecoding" );
    private final JsonReader reader;
    private Map<IdKey, Object> idToObject;

    public JsonDecodingContext( JsonReader reader )
    {
        this.reader = reader;
    }

    @Override
    public Logger getLogger()
    {
        return logger;
    }

    public JsonDecodingException traceError( String message )
    {
        getLogger().log( Level.SEVERE, message );
        traceReaderInfo();
        return new JsonDecodingException( message );
    }

    public JsonDecodingException traceError( Exception cause )
    {
        getLogger().log( Level.SEVERE, "Error while decoding", cause );
        traceReaderInfo();
        return new JsonDecodingException( cause );
    }

    private void traceReaderInfo()
    {
        if ( LogConfiguration.loggingIsEnabled( Level.INFO ) )
        {
            getLogger().log( Level.INFO, "Error at line " + reader.getLineNumber() + " and column " + reader
                .getColumnNumber() + " of input <" + reader.getInput() + ">" );
        }
    }

    public void addObjectId( IdKey id, Object instance )
    {
        if ( null == idToObject )
        {
            idToObject = new HashMap<IdKey, Object>();
        }
        idToObject.put( id, instance );
    }

    public Object getObjectWithId( IdKey id )
    {
        if ( null != idToObject )
        {
            return idToObject.get( id );
        }
        return null;
    }
}
