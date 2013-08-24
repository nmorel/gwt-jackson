package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Base implementation of {@link JsonMapper} for dates.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractDateJsonMapper<D extends Date> extends AbstractJsonMapper<D>
{
    protected static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 );

    @Override
    public D decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
    {
        if ( JsonToken.NUMBER.equals( reader.peek() ) )
        {
            return decodeNumber( reader.nextLong() );
        }
        else
        {
            return decodeString( reader.nextString() );
        }
    }

    protected abstract D decodeNumber( long millis );

    protected abstract D decodeString( String date );

    @Override
    public void encode( JsonWriter writer, D value, JsonEncodingContext ctx ) throws IOException
    {
        writer.value( DATE_FORMAT.format( value ) );
    }
}
