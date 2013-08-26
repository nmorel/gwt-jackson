package com.github.nmorel.gwtjackson.client.mapper;

import java.sql.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/** @author Nicolas Morel */
public class SqlDateJsonMapperTest extends AbstractJsonMapperTest<SqlDateJsonMapper>
{
    @Override
    protected SqlDateJsonMapper createMapper()
    {
        return new SqlDateJsonMapper();
    }

    @Override
    protected void testDecodeValue( SqlDateJsonMapper mapper )
    {
        assertEquals( new Date( 1377543971773l ), mapper.decode( "1377543971773" ) );
        assertEquals( new Date( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) ), mapper.decode( "\"2012-08-18T17:45:56.543+02:00\"" ) );
    }

    @Override
    protected void testEncodeValue( SqlDateJsonMapper mapper )
    {
        // don't know how to deal with the timezone so we just use the same formatter
        Date date = new Date( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) );
        assertEquals( "\"" + DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 ).format( date ) + "\"", mapper
            .encode( date ) );
    }
}
