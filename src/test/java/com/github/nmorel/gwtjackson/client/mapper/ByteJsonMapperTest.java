package com.github.nmorel.gwtjackson.client.mapper;

/** @author Nicolas Morel */
public class ByteJsonMapperTest extends AbstractJsonMapperTest<ByteJsonMapper>
{
    @Override
    protected ByteJsonMapper createMapper()
    {
        return new ByteJsonMapper();
    }

    @Override
    protected void testDecodeValue( ByteJsonMapper mapper )
    {
        assertEquals( new Byte( "34" ), mapper.decode( "34" ) );
        assertEquals( new Byte( "1" ), mapper.decode( "\"1\"" ) );
        assertEquals( (Byte) (Byte.MIN_VALUE), mapper.decode( "-128" ) );
        assertEquals( (Byte) Byte.MAX_VALUE, mapper.decode( "127" ) );
    }

    @Override
    protected void testEncodeValue( ByteJsonMapper mapper )
    {
        assertEquals( "34", mapper.encode( (byte) 34 ) );
        assertEquals( "1", mapper.encode( new Byte( "1" ) ) );
        assertEquals( "-128", mapper.encode( Byte.MIN_VALUE ) );
        assertEquals( "127", mapper.encode( Byte.MAX_VALUE ) );
    }
}
