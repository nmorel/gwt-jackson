package com.github.nmorel.gwtjackson.client.ser;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractJsonSerializerTest<T> extends GwtJacksonTestCase {

    protected abstract JsonSerializer<T> createSerializer();

    public void testEncodeNullValue() {
        assertSerialization( "null", null );
    }

    protected String serialize( T value ) {
        StringBuilder builder = new StringBuilder();
        JsonWriter writer = new JsonWriter( builder );
        writer.setLenient( true );
        writer.setSerializeNulls( false );
        createSerializer().encode( writer, value, new JsonEncodingContext( writer ) );
        return builder.toString();
    }

    protected void assertSerialization( String expected, T value ) {
        assertEquals( expected, serialize( value ) );
    }

    public abstract void testEncodeValue();
}
