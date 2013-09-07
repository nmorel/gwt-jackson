package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for array of char.
 *
 * @author Nicolas Morel
 */
public class PrimitiveCharacterArrayJsonMapper extends AbstractArrayJsonMapper<char[]>
{
    @Override
    public char[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return reader.nextString().toCharArray();
    }

    @Override
    public void doEncode( JsonWriter writer, char[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( new String( values ) );
    }
}
