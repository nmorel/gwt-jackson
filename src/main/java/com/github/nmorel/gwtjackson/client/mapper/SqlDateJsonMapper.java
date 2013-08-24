package com.github.nmorel.gwtjackson.client.mapper;

import java.sql.Date;

import com.github.nmorel.gwtjackson.client.JsonMapper;

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
        return new Date( DATE_FORMAT.parseStrict( date ).getTime() );
    }
}
