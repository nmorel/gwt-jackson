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
        return decode( reader, new JsonDecodingContext( reader ) );
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
            throw ctx.traceError( e );
        }
        catch ( JsonDecodingException e )
        {
            // already logged, we just throw it
            throw e;
        }
        catch ( Exception e )
        {
            throw ctx.traceError( e );
        }
    }

    protected abstract T doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException;

    @Override
    public String encode( T value ) throws JsonEncodingException
    {
        StringBuilder builder = new StringBuilder();
        JsonWriter writer = new JsonWriter( builder );
        writer.setLenient( true );
        writer.setSerializeNulls( false );
        encode( writer, value, new JsonEncodingContext( writer ) );
        return builder.toString();
    }

    @Override
    public void encode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws JsonEncodingException
    {
        try
        {
            if ( null == value )
            {
                writer.nullValue();
                return;
            }
            doEncode( writer, value, ctx );
        }
        catch ( IOException e )
        {
            throw ctx.traceError( value, e );
        }
        catch ( JsonEncodingException e )
        {
            // already logged, we just throw it
            throw e;
        }
        catch ( Exception e )
        {
            throw ctx.traceError( value, e );
        }
    }

    protected abstract void doEncode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException;
}
