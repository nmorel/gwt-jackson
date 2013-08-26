package com.github.nmorel.gwtjackson.client.mapper;

/** @author Nicolas Morel */
public class EnumJsonMapperTest extends AbstractJsonMapperTest<EnumJsonMapper<EnumJsonMapperTest.EnumTest>>
{
    protected static enum EnumTest {
        ONE, TWO, THREE, FOUR;
    }

    @Override
    protected EnumJsonMapper<EnumTest> createMapper()
    {
        return new EnumJsonMapper<EnumTest>( EnumTest.class );
    }

    @Override
    protected void testDecodeValue( EnumJsonMapper<EnumTest> mapper )
    {
        assertEquals( EnumTest.ONE, mapper.decode( "\"ONE\"" ) );
        assertEquals( EnumTest.TWO, mapper.decode( "\"TWO\"" ) );
        assertEquals( EnumTest.THREE, mapper.decode( "\"THREE\"" ) );
        assertEquals( EnumTest.FOUR, mapper.decode( "\"FOUR\"" ) );
    }

    @Override
    protected void testEncodeValue( EnumJsonMapper<EnumTest> mapper )
    {
        assertEquals( "\"ONE\"", mapper.encode( EnumTest.ONE ) );
        assertEquals( "\"TWO\"", mapper.encode( EnumTest.TWO ) );
        assertEquals( "\"THREE\"", mapper.encode( EnumTest.THREE ) );
        assertEquals( "\"FOUR\"", mapper.encode( EnumTest.FOUR ) );
    }
}
