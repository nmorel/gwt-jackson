package com.github.nmorel.gwtjackson.client.mapper.collection;

import java.util.Arrays;
import java.util.Collections;

import com.github.nmorel.gwtjackson.client.mapper.AbstractJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.StringJsonMapper;

/** @author Nicolas Morel */
public class ListJsonMapperTest extends AbstractJsonMapperTest<ListJsonMapper<String>>
{
    @Override
    protected ListJsonMapper<String> createMapper()
    {
        return new ListJsonMapper<String>( new StringJsonMapper() );
    }

    @Override
    protected void testDecodeValue( ListJsonMapper<String> mapper )
    {
        assertEquals( Arrays.asList( "Hello", " ", "World", "!" ), mapper.decode( "[Hello, \" \", \"World\", \"!\"]" ) );
        assertEquals( Collections.<String>emptyList(), mapper.decode( "[]" ) );
    }

    @Override
    protected void testEncodeValue( ListJsonMapper<String> mapper )
    {
        assertEquals( "[\"Hello\",\" \",\"World\",\"!\"]", mapper.encode( Arrays.asList( "Hello", " ", "World", "!" ) ) );
        assertEquals( "[]", mapper.encode( Collections.<String>emptyList() ) );
    }

}
