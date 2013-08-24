package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link JsonMapper} implementation for {@link BigInteger}.
 *
 * @author Nicolas Morel
 */
public class BigIntegerJsonMapper extends NumberJsonMapper<BigInteger>
{
    @Override
    public BigInteger decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return new BigInteger( reader.nextString() );
    }

    @Override
    public void encode( JsonWriter writer, BigInteger value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.toString() );
    }
}
