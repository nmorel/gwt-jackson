package com.github.nmorel.gwtjackson.client.mapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/** @author Nicolas Morel */
public class SetJsonMapperTest extends AbstractJsonMapperTest<SetJsonMapper<String>>
{
    @Override
    protected SetJsonMapper<String> createMapper()
    {
        return new SetJsonMapper<String>( new StringJsonMapper() );
    }

    @Override
    protected void testDecodeValue( SetJsonMapper<String> mapper )
    {
        assertEquals( new HashSet<String>( Arrays.asList( "Hello", " ", "World", "!" ) ), mapper
            .decode( "[Hello, \" \", \"World\", " + "\"!\"]" ) );
        assertEquals( Collections.<String>emptySet(), mapper.decode( "[]" ) );
    }

    @Override
    protected void testEncodeValue( SetJsonMapper<String> mapper )
    {
        // can't predict the order so we just encode one element
        assertEquals( "[\"Hello\"]", mapper.encode( new HashSet<String>( Arrays.asList( "Hello", "Hello" ) ) ) );
        assertEquals( "[]", mapper.encode( Collections.<String>emptySet() ) );
    }

}
