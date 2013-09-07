package com.github.nmorel.gwtjackson.client.mapper.number;

import com.github.nmorel.gwtjackson.client.mapper.AbstractJsonMapperTest;

/** @author Nicolas Morel */
public class LongJsonMapperTest extends AbstractJsonMapperTest<LongJsonMapper>
{
    @Override
    protected LongJsonMapper createMapper()
    {
        return new LongJsonMapper();
    }

    @Override
    protected void testDecodeValue( LongJsonMapper mapper )
    {
        assertEquals( (Long) 3441764551145441542l, mapper.decode( "3441764551145441542" ) );
        assertEquals( new Long( "-3441764551145441542" ), mapper.decode( "\"-3441764551145441542\"" ) );
        assertEquals( (Long) Long.MIN_VALUE, mapper.decode( "-9223372036854775808" ) );
        assertEquals( (Long) Long.MAX_VALUE, mapper.decode( "9223372036854775807" ) );
    }

    @Override
    protected void testEncodeValue( LongJsonMapper mapper )
    {
        assertEquals( "3441764551145441542", mapper.encode( 3441764551145441542l ) );
        assertEquals( "-3441764551145441542", mapper.encode( -3441764551145441542l ) );
        assertEquals( "-9223372036854775808", mapper.encode( Long.MIN_VALUE ) );
        assertEquals( "9223372036854775807", mapper.encode( Long.MAX_VALUE ) );
    }
}
