package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.client.JsonMapper;

/**
 * Default {@link JsonMapper} implementation for {@link Character}.
 *
 * @author Nicolas Morel
 */
public class CharacterJsonMapper extends AbstractJsonMapper<Character>
{
    @Override
    public Character decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return (char) reader.nextDouble();
    }

    @Override
    public void encode( JsonWriter writer, Character value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value );
    }
}
