package com.github.nmorel.gwtjackson.client.mapper;

/** @author Nicolas Morel */
public class BooleanJsonMapperTest extends AbstractJsonMapperTest<BooleanJsonMapper>
{
    @Override
    protected BooleanJsonMapper createMapper()
    {
        return new BooleanJsonMapper();
    }

    @Override
    protected void testDecodeValue( BooleanJsonMapper mapper )
    {
        assertTrue( mapper.decode( "true" ) );
        assertTrue( mapper.decode( "\"trUe\"" ) );
        assertTrue( mapper.decode( "1" ) );

        assertFalse( mapper.decode( "faLse" ) );
        assertFalse( mapper.decode( "\"false\"" ) );
        assertFalse( mapper.decode( "0" ) );
        assertFalse( mapper.decode( "other" ) );
    }

    @Override
    protected void testEncodeValue( BooleanJsonMapper mapper )
    {
        assertEquals( "true", mapper.encode( true ) );
        assertEquals( "true", mapper.encode( Boolean.TRUE ) );
        assertEquals( "false", mapper.encode( false ) );
        assertEquals( "false", mapper.encode( Boolean.FALSE ) );
    }
}
