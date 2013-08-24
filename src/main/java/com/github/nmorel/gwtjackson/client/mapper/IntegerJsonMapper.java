package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Integer}.
 *
 * @author Nicolas Morel
 */
public class IntegerJsonMapper extends NumberJsonMapper<Integer>
{
    @Override
    public Integer decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return reader.nextInt();
    }
}
