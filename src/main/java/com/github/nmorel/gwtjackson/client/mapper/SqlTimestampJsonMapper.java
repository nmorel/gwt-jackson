package com.github.nmorel.gwtjackson.client.mapper;

import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.JsonMapper;

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
        return new Timestamp( DATE_FORMAT.parseStrict( date ).getTime() );
    }
}
