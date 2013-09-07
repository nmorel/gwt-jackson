package com.github.nmorel.gwtjackson.client.mapper.date;

import java.io.IOException;
import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link JsonMapper} for {@link Timestamp}.
 *
 * @author Nicolas Morel
 */
public class SqlTimestampJsonMapper extends AbstractDateJsonMapper<Timestamp>
{
    @Override
    protected Timestamp decodeNumber( long millis )
    {
        return new Timestamp( millis );
    }

    @Override
    protected Timestamp decodeString( String date )
    {
        return Timestamp.valueOf( date );
    }

    @Override
    protected void doEncode( JsonWriter writer, Timestamp value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.getTime() );
    }
}
