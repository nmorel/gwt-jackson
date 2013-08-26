package com.github.nmorel.gwtjackson.client.mapper;

import java.sql.Timestamp;

import com.google.gwt.i18n.client.DateTimeFormat;

/** @author Nicolas Morel */
public class SqlTimestampJsonMapperTest extends AbstractJsonMapperTest<SqlTimestampJsonMapper>
{
    @Override
    protected SqlTimestampJsonMapper createMapper()
    {
        return new SqlTimestampJsonMapper();
    }

    @Override
    protected void testDecodeValue( SqlTimestampJsonMapper mapper )
    {
        assertEquals( new Timestamp( 1377543971773l ), mapper.decode( "1377543971773" ) );
        assertEquals( new Timestamp( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) ), mapper.decode( "\"2012-08-18T17:45:56.543+02:00\"" ) );
    }

    @Override
    protected void testEncodeValue( SqlTimestampJsonMapper mapper )
    {
        // don't know how to deal with the timezone so we just use the same formatter
        Timestamp date = new Timestamp( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) );
        assertEquals( "\"" + DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 ).format( date ) + "\"", mapper
            .encode( date ) );
    }
}
