package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonMapper} implementation for {@link Boolean}.
 *
 * @author Nicolas Morel
 */
public class BooleanJsonMapper extends AbstractJsonMapper<Boolean>
{
    @Override
    public Boolean doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        JsonToken token = reader.peek();
        if ( JsonToken.BOOLEAN.equals( token ) )
        {
            return reader.nextBoolean();
        }
        else if ( JsonToken.STRING.equals( token ) )
        {
            return Boolean.valueOf( reader.nextString() );
        }
        else if ( JsonToken.NUMBER.equals( token ) )
        {
            return reader.nextInt() == 1;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void doEncode( JsonWriter writer, Boolean value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value );
    }
}
