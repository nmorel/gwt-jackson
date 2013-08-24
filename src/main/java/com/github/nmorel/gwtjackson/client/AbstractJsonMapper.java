package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/** @author Nicolas Morel */
public abstract class AbstractJsonMapper<T> implements JsonMapper<T>
{
    @Override
    public T decode( String in )
    {
        JsonReader reader = new JsonReader( in );
        return decode( reader );
    }

    @Override
    public String encode( T value )
    {
        StringBuilder builder = new StringBuilder();
        encode( new JsonWriter( builder ), value );
        return builder.toString();
    }
}
