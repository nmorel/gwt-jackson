package com.github.nmorel.gwtjackson.client.mapper.array;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.client.utils.Base64;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for array of byte.
 *
 * @author Nicolas Morel
 */
public class PrimitiveByteArrayJsonMapper extends AbstractArrayJsonMapper<byte[]>
{
    @Override
    public byte[] doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return Base64.decode( reader.nextString() ).getBytes();
    }

    @Override
    public void doEncode( JsonWriter writer, byte[] values, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( Base64.encode( new String( values ) ) );
    }
}
