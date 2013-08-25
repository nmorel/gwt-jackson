package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Double}.
 *
 * @author Nicolas Morel
 */
public class DoubleJsonMapper extends NumberJsonMapper<Double>
{
    @Override
    public Double doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return reader.nextDouble();
    }
}
