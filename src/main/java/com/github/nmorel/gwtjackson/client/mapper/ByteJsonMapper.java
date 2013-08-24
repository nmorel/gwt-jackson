package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Byte}.
 *
 * @author Nicolas Morel
 */
public class ByteJsonMapper extends NumberJsonMapper<Byte>
{
    @Override
    public Byte decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return (byte) reader.nextDouble();
    }
}
