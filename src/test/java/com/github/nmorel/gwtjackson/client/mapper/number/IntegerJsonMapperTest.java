package com.github.nmorel.gwtjackson.client.mapper.number;

import com.github.nmorel.gwtjackson.client.mapper.AbstractJsonMapperTest;

/** @author Nicolas Morel */
public class IntegerJsonMapperTest extends AbstractJsonMapperTest<IntegerJsonMapper>
{
    @Override
    protected IntegerJsonMapper createMapper()
    {
        return new IntegerJsonMapper();
    }

    @Override
    protected void testDecodeValue( IntegerJsonMapper mapper )
    {
        assertEquals( (Integer) 34, mapper.decode( "34" ) );
        assertEquals( new Integer( -1 ), mapper.decode( "\"-1\"" ) );
        assertEquals( (Integer) Integer.MIN_VALUE, mapper.decode( "-2147483648" ) );
        assertEquals( (Integer) Integer.MAX_VALUE, mapper.decode( "2147483647" ) );
    }

    @Override
    protected void testEncodeValue( IntegerJsonMapper mapper )
    {
        assertEquals( "34", mapper.encode( 34 ) );
        assertEquals( "-1", mapper.encode( -1 ) );
        assertEquals( "-2147483648", mapper.encode( Integer.MIN_VALUE ) );
        assertEquals( "2147483647", mapper.encode( Integer.MAX_VALUE ) );
    }
}
