package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

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

    @Override
    public void doEncode( JsonWriter writer, BigDecimal value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.toString() );
    }
}
