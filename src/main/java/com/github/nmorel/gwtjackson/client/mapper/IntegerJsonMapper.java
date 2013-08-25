package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Default {@link JsonMapper} implementation for {@link Integer}.
 *
 * @author Nicolas Morel
 */
public class IntegerJsonMapper extends NumberJsonMapper<Integer>
{
    @Override
    public Integer doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        if ( JsonToken.NUMBER.equals( reader.peek() ) )
        {
            return reader.nextInt();
        }
        else
        {
            return Integer.parseInt( reader.nextString() );
        }
    }
}
