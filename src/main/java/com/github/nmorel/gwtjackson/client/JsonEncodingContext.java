package com.github.nmorel.gwtjackson.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.client.utils.ObjectIdEncoder;
import com.google.gwt.logging.client.LogConfiguration;

/** @author Nicolas Morel */
public class JsonEncodingContext extends JsonMappingContext
{
    private static final Logger logger = Logger.getLogger( "JsonEncoding" );
    private final JsonWriter writer;
    private Map<Object, ObjectIdEncoder<?>> mapObjectId;
    private List<ObjectIdGenerator<?>> generators;
    /*
     * Encoding options
     */
    private boolean useEqualityForObjectId = false;

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

    public void addObjectId( Object object, ObjectIdEncoder<?> id )
    {
        if ( null == mapObjectId )
        {
            if ( useEqualityForObjectId )
            {
                mapObjectId = new HashMap<Object, ObjectIdEncoder<?>>();
            }
            else
            {
                mapObjectId = new IdentityHashMap<Object, ObjectIdEncoder<?>>();
            }
        }
        mapObjectId.put( object, id );
    }

    public ObjectIdEncoder<?> getObjectId( Object object )
    {
        if ( null != mapObjectId )
        {
            return mapObjectId.get( object );
        }
        return null;
    }

    public boolean isUseEqualityForObjectId()
    {
        return useEqualityForObjectId;
    }

    public void setUseEqualityForObjectId( boolean useEqualityForObjectId )
    {
        this.useEqualityForObjectId = useEqualityForObjectId;
    }

    public void addGenerator( ObjectIdGenerator<?> generator )
    {
        if ( null == generators )
        {
            generators = new ArrayList<ObjectIdGenerator<?>>();
        }
        generators.add( generator );
    }

    public <T> ObjectIdGenerator<T> findObjectIdGenerator( ObjectIdGenerator<T> gen )
    {
        if ( null != generators )
        {
            for ( ObjectIdGenerator<?> generator : generators )
            {
                if ( generator.canUseFor( gen ) )
                {
                    return (ObjectIdGenerator<T>) generator;
                }
            }
        }
        return null;
    }
}
