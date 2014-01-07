package com.github.nmorel.gwtjackson.client.stream.impl;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.stream.*;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public class DefaultJsonReaderTest extends AbstractJsonReaderTest {

    @Override
    public JsonReader newJsonReader( String input ) {
        return new DefaultJsonReader( new StringReader( input ) );
    }

    public void testStrictVeryLongNumber() throws IOException {
        JsonReader reader = newJsonReader( "[0." + repeat( '9', 8192 ) + "]" );
        reader.beginArray();
        try {
            assertEquals( 1d, reader.nextDouble() );
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testLenientVeryLongNumber() throws IOException {
        JsonReader reader = newJsonReader( "[0." + repeat( '9', 8192 ) + "]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( JsonToken.STRING, reader.peek() );
        assertEquals( 1d, reader.nextDouble() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }
}
