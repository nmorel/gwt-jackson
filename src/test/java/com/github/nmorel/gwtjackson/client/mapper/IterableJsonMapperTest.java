package com.github.nmorel.gwtjackson.client.mapper;

import java.util.Arrays;
import java.util.Collections;

/** @author Nicolas Morel */
public class IterableJsonMapperTest extends AbstractJsonMapperTest<IterableJsonMapper<String>>
{
    @Override
    protected IterableJsonMapper<String> createMapper()
    {
        return new IterableJsonMapper<String>( new StringJsonMapper() );
    }

    @Override
    protected void testDecodeValue( IterableJsonMapper<String> mapper )
    {
        assertEquals( Arrays.asList( "Hello", " ", "World", "!" ), mapper.decode( "[Hello, \" \", \"World\", \"!\"]" ) );
        assertEquals( Collections.<String>emptyList(), mapper.decode( "[]" ) );
    }

    @Override
    protected void testEncodeValue( IterableJsonMapper<String> mapper )
    {
        assertEquals( "[\"Hello\",\" \",\"World\",\"!\"]", mapper.encode( Arrays.asList( "Hello", " ", "World", "!" ) ) );
        assertEquals( "[]", mapper.encode( Collections.<String>emptyList() ) );
    }

}
