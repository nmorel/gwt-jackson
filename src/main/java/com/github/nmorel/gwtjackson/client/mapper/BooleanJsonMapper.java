package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Boolean}.
 *
 * @author Nicolas Morel
 */
public class BooleanJsonMapper extends AbstractJsonMapper<Boolean>
{
    @Override
    public Boolean decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return reader.nextBoolean();
    }

    @Override
    public void encode( JsonWriter writer, Boolean value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value );
    }
}
