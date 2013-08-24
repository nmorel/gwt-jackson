package com.github.nmorel.gwtjackson.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Gives default method implementation for {@link JsonMapper} and utility methods for children.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractJsonMapper<T> implements JsonMapper<T>
{
    @Override
    public T decode( String in ) throws IOException
    {
        JsonReader reader = new JsonReader( in );
        reader.setLenient( true );
        return decode( reader, new JsonDecodingContext() );
    }

    @Override
    public String encode( T value ) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        encode( new JsonWriter( builder ), value, new JsonEncodingContext() );
        return builder.toString();
    }
}
