package com.github.nmorel.gwtjackson.client.mapper.number;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonMapper} implementation for {@link Byte}.
 *
 * @author Nicolas Morel
 */
public class ByteJsonMapper extends NumberJsonMapper<Byte>
{
    @Override
    public Byte doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return (byte) reader.nextDouble();
    }
}
