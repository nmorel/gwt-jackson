package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.sql.Date;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Base implementation of {@link JsonMapper} for {@link Date}.
 *
 * @author Nicolas Morel
 */
public class SqlDateJsonMapper extends AbstractDateJsonMapper<Date>
{
    @Override
    protected Date decodeNumber( long millis )
    {
        return new Date( millis );
    }

    @Override
    protected Date decodeString( String date )
    {
        return Date.valueOf( date );
    }

    @Override
    protected void doEncode( JsonWriter writer, Date value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.toString() );
    }
}
