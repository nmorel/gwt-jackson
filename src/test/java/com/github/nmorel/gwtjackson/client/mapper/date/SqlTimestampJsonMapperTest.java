package com.github.nmorel.gwtjackson.client.mapper.date;

import java.sql.Timestamp;

import com.github.nmorel.gwtjackson.client.mapper.AbstractJsonMapperTest;

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
        // can't do better without timezone
        assertNotNull( mapper.decode( "\"2012-08-18 17:45:56.543\"" ) );
    }

    @Override
    protected void testEncodeValue( SqlTimestampJsonMapper mapper )
    {
        // don't know how to deal with the timezone so we just use the same date
        Timestamp date = new Timestamp( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) );
        assertEquals( "" + date.getTime(), mapper.encode( date ) );
    }
}
