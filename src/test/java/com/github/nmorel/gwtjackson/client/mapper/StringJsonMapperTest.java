package com.github.nmorel.gwtjackson.client.mapper;

/** @author Nicolas Morel */
public class StringJsonMapperTest extends AbstractJsonMapperTest<StringJsonMapper>
{
    @Override
    protected StringJsonMapper createMapper()
    {
        return new StringJsonMapper();
    }

    @Override
    protected void testDecodeValue( StringJsonMapper mapper )
    {
        assertEquals( "", mapper.decode( "\"\"" ) );
        assertEquals( "Json", mapper.decode( "Json" ) );
        assertEquals( "&é(-è_ çà)='", mapper.decode( "\"&é(-è_ çà)='\"" ) );
    }

    @Override
    protected void testEncodeValue( StringJsonMapper mapper )
    {
        assertEquals( "\"Hello World!\"", mapper.encode( "Hello World!" ) );
        assertEquals( "\"\"", mapper.encode( "" ) );
    }
}
