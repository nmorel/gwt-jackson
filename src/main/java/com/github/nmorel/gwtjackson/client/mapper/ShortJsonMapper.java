package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Short}.
 *
 * @author Nicolas Morel
 */
public class ShortJsonMapper extends NumberJsonMapper<Short>
{
    @Override
    public Short decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return (short) reader.nextInt();
    }
}
