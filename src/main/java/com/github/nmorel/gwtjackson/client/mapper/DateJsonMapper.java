package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Base implementation of {@link JsonMapper} for {@link Date}.
 *
 * @author Nicolas Morel
 */
public class DateJsonMapper extends AbstractDateJsonMapper<Date>
{
    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 );

    @Override
    protected Date decodeNumber( long millis )
    {
        return new Date( millis );
    }

    @Override
    protected Date decodeString( String date )
    {
        return DATE_FORMAT.parseStrict( date );
    }

    @Override
    protected void doEncode( JsonWriter writer, Date value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( value.getTime() );
    }
}
