package com.github.nmorel.gwtjackson.client.mapper.date;

import java.io.IOException;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.AbstractJsonMapper;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * Base implementation of {@link JsonMapper} for dates.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractDateJsonMapper<D extends Date> extends AbstractJsonMapper<D>
{
    @Override
    public D doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException
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
}
