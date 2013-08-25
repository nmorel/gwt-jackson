package com.github.nmorel.gwtjackson.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Gives default method implementation for {@link JsonMapper} and utility methods for children.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractJsonMapper<T> implements JsonMapper<T>
{
    @Override
    public T decode( String in ) throws JsonDecodingException
    {
        JsonReader reader = new JsonReader( in );
        reader.setLenient( true );
        return decode( reader, new JsonDecodingContext() );
    }

    @Override
    public T decode( JsonReader reader, JsonDecodingContext ctx ) throws JsonDecodingException
    {
        try
        {
            if ( JsonToken.NULL.equals( reader.peek() ) )
            {
                reader.skipValue();
                return null;
            }
            return doDecode( reader, ctx );
        }
        catch ( IOException e )
        {
            throw new JsonDecodingException( e );
        }
    }

    protected abstract T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException;

    @Override
    public String encode( T value ) throws JsonEncodingException
    {
        StringBuilder builder = new StringBuilder();
        encode( new JsonWriter( builder ), value, new JsonEncodingContext() );
        return builder.toString();
    }

    @Override
    public void encode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws JsonEncodingException
    {
        try
        {
            if ( null == value )
            {
                return;
            }
            doEncode( writer, value, ctx );
        }
        catch ( IOException e )
        {
            throw new JsonEncodingException( e );
        }
    }

    protected abstract void doEncode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException;
}
