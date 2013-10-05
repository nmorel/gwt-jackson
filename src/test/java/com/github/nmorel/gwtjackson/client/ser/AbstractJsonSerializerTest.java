package com.github.nmorel.gwtjackson.client.ser;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractJsonSerializerTest<T> extends GwtJacksonTestCase {

    protected abstract JsonSerializer<T> createSerializer();

    public void testSerializeNullValue() {
        assertSerialization( "null", null );
    }

    protected String serialize( T value ) {
        JsonSerializationContext ctx = new JsonSerializationContext.Builder().build();
        JsonWriter writer = ctx.newJsonWriter();
        writer.setLenient( true );
        createSerializer().serialize( writer, value, ctx );
        return writer.getOutput();
    }

    protected void assertSerialization( String expected, T value ) {
        assertEquals( expected, serialize( value ) );
    }

    public abstract void testSerializeValue();
}
