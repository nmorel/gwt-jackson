package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.UUID;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Default {@link com.github.nmorel.gwtjackson.client.JsonMapper} implementation for {@link UUID}.
 *
 * @author Nicolas Morel
 */
public class UUIDJsonMapper extends AbstractJsonMapper<UUID>
{
    @Override
    public UUID doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        return UUID.fromString( reader.nextString());
    }

    @Override
    public void doEncode( JsonWriter writer, UUID value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.toString() );
    }
}
