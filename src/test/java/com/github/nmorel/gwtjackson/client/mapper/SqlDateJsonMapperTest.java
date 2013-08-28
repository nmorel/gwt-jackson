package com.github.nmorel.gwtjackson.client.mapper;

import java.sql.Date;

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
        assertEquals( new Date( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) ).toString(), mapper.decode( "\"2012-08-18\"" ).toString() );
    }

    @Override
    protected void testEncodeValue( SqlDateJsonMapper mapper )
    {
        assertEquals( "\"2012-08-18\"", mapper.encode( new Date( getUTCTime( 2012, 8, 18, 12, 45, 56, 543 ) ) ) );
    }
}
