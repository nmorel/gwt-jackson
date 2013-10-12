package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext.Builder;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractKeyDeserializerTest<T> extends GwtJacksonTestCase {

    protected abstract KeyDeserializer<T> createDeserializer();

    public void testDeserializeNullValue() {
        assertNull( deserialize( null ) );
    }

    protected T deserialize( String value ) {
        JsonDeserializationContext ctx = new Builder().build();
        return createDeserializer().deserialize( value, ctx );
    }

    protected void assertDeserialization( T expected, String value ) {
        assertEquals( expected, deserialize( value ) );
    }

    public abstract void testDeserializeValue();
}
