package com.github.nmorel.gwtjackson.client.mapper;

import java.util.Arrays;
import java.util.Collections;

/** @author Nicolas Morel */
public class CollectionJsonMapperTest extends AbstractJsonMapperTest<CollectionJsonMapper<String>>
{
    @Override
    protected CollectionJsonMapper<String> createMapper()
    {
        return new CollectionJsonMapper<String>( new StringJsonMapper() );
    }

    @Override
    protected void testDecodeValue( CollectionJsonMapper<String> mapper )
    {
        assertEquals( Arrays.asList( "Hello", " ", "World", "!" ), mapper.decode( "[Hello, \" \", \"World\", \"!\"]" ) );
        assertEquals( Collections.<String>emptyList(), mapper.decode( "[]" ) );
    }

    @Override
    protected void testEncodeValue( CollectionJsonMapper<String> mapper )
    {
        assertEquals( "[\"Hello\",\" \",\"World\",\"!\"]", mapper.encode( Arrays.asList( "Hello", " ", "World", "!" ) ) );
        assertEquals( "[]", mapper.encode( Collections.<String>emptyList() ) );
    }

}
