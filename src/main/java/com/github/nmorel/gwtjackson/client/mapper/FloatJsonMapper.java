package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonMapper} implementation for {@link Float}.
 *
 * @author Nicolas Morel
 */
public class FloatJsonMapper extends NumberJsonMapper<Float>
{
    @Override
    public Float decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return Float.parseFloat( reader.nextString() );
    }
}
