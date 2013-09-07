package com.github.nmorel.gwtjackson.client.mapper.number;

import java.io.IOException;
import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonMapper} implementation for {@link BigInteger}.
 *
 * @author Nicolas Morel
 */
public class BigIntegerJsonMapper extends NumberJsonMapper<BigInteger>
{
    @Override
    public BigInteger doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return new BigInteger( reader.nextString() );
    }
}
