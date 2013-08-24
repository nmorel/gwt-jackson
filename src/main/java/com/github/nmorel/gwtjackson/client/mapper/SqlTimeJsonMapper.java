package com.github.nmorel.gwtjackson.client.mapper;

import java.sql.Time;

import com.github.nmorel.gwtjackson.client.JsonMapper;
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
        return new Time( DATE_FORMAT.parseStrict( date ).getTime() );
    }
}
