package com.github.nmorel.gwtjackson.client.mapper;

import java.sql.Time;

/** @author Nicolas Morel */
public class SqlTimeJsonMapperTest extends AbstractJsonMapperTest<SqlTimeJsonMapper>
{
    @Override
    protected SqlTimeJsonMapper createMapper()
    {
        return new SqlTimeJsonMapper();
    }

    @Override
    protected void testDecodeValue( SqlTimeJsonMapper mapper )
    {
        assertEquals( new Time( 1377543971773l ), mapper.decode( "1377543971773" ) );
        Time time = new Time( getUTCTime( 2012, 8, 18, 15, 45, 56, 543 ) );
        assertEquals( time.toString(), mapper.decode( "\"" + time.toString() + "\"" ).toString() );
    }

    @Override
    protected void testEncodeValue( SqlTimeJsonMapper mapper )
    {
        Time time = new Time( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) );
        assertEquals( "\"" + time.toString() + "\"", mapper.encode( time ) );
    }
}
