package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link JsonMapper} implementation for {@link Short}.
 *
 * @author Nicolas Morel
 */
public class ShortJsonMapper extends NumberJsonMapper<Short>
{
    @Override
    public Short doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        if ( JsonToken.NUMBER.equals( reader.peek() ) )
        {
            return (short) reader.nextInt();
        }
        else
        {
            return Short.parseShort( reader.nextString() );
        }
    }
}
