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
 * Default {@link JsonMapper} implementation for {@link Character}.
 *
 * @author Nicolas Morel
 */
public class CharacterJsonMapper extends AbstractJsonMapper<Character>
{
    @Override
    public Character doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        if ( JsonToken.NUMBER.equals( reader.peek() ) )
        {
            return (char) reader.nextInt();
        }
        else
        {
            String value = reader.nextString();
            if ( value.isEmpty() )
            {
                return null;
            }
            return value.charAt( 0 );
        }
    }

    @Override
    public void doEncode( JsonWriter writer, Character value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.toString() );
    }
}
