package com.github.nmorel.gwtjackson.client.ser.map.key;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractKeySerializerTest<T> extends GwtJacksonTestCase {

    protected abstract KeySerializer<T> createSerializer();

    public void testSerializeNullValue() {
        assertSerialization( null, null );
    }

    protected String serialize( T value ) {
        JsonSerializationContext ctx = new JsonSerializationContext.Builder().build();
        return createSerializer().serialize( value, ctx );
    }

    protected void assertSerialization( String expected, T value ) {
        assertEquals( expected, serialize( value ) );
    }

    public abstract void testSerializeValue();
}
