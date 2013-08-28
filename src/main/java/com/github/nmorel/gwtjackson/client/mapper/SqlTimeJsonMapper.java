package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.sql.Time;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link JsonMapper} for {@link Time}.
 *
 * @author Nicolas Morel
 */
public class SqlTimeJsonMapper extends AbstractDateJsonMapper<Time>
{
    @Override
    protected Time decodeNumber( long millis )
    {
        return new Time( millis );
    }

    @Override
    protected Time decodeString( String date )
    {
        return Time.valueOf( date );
    }

    @Override
    protected void doEncode( JsonWriter writer, Time value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.toString() );
    }
}
