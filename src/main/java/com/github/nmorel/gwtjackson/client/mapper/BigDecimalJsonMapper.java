package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Default {@link JsonMapper} implementation for {@link BigDecimal}.
 *
 * @author Nicolas Morel
 */
public class BigDecimalJsonMapper extends NumberJsonMapper<BigDecimal>
{
    @Override
    public BigDecimal doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return new BigDecimal( reader.nextString() );
    }
}
