package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonMapper} implementation for {@link String}.
 *
 * @author Nicolas Morel
 */
public class StringJsonMapper extends AbstractJsonMapper<String>
{
    @Override
    public String decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return reader.nextString();
    }

    @Override
    public void encode( JsonWriter writer, String value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value );
    }
}
