package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Long}.
 *
 * @author Nicolas Morel
 */
public class LongJsonMapper extends NumberJsonMapper<Long>
{
    @Override
    public Long decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return reader.nextLong();
    }
}
