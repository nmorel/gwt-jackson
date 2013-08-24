package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonMapper} for {@link Number}.
 *
 * @author Nicolas Morel
 */
public abstract class NumberJsonMapper<N extends Number> extends AbstractJsonMapper<N>
{
    @Override
    public void encode( JsonWriter writer, N value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value );
    }
}
