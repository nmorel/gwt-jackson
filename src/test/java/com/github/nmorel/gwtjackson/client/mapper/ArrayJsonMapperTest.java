package com.github.nmorel.gwtjackson.client.mapper;

import java.util.Arrays;

/** @author Nicolas Morel */
public class ArrayJsonMapperTest extends AbstractJsonMapperTest<ArrayJsonMapper<String>>
{
    @Override
    protected ArrayJsonMapper<String> createMapper()
    {
        return new ArrayJsonMapper<String>( new StringJsonMapper(), new ArrayJsonMapper.ArrayCreator<String>()
        {
            @Override
            public String[] create( int length )
            {
                return new String[length];
            }
        } );
    }

    @Override
    protected void testDecodeValue( ArrayJsonMapper<String> mapper )
    {
        assertTrue( Arrays.deepEquals( new String[]{"Hello", " ", "World", "!"}, mapper.decode( "[Hello, \" \", \"World\", " +
            "" + "\"!\"]" ) ) );
        assertTrue( Arrays.deepEquals( new String[0], mapper.decode( "[]" ) ) );
    }

    @Override
    protected void testEncodeValue( ArrayJsonMapper<String> mapper )
    {
        assertEquals( "[\"Hello\",\" \",\"World\",\"!\"]", mapper.encode( new String[]{"Hello", " ", "World", "!"} ) );
        assertEquals( "[]", mapper.encode( new String[0] ) );
    }

}
