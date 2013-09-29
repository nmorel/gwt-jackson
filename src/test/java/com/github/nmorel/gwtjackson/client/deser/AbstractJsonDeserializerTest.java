package com.github.nmorel.gwtjackson.client.deser;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractJsonDeserializerTest<T> extends GwtJacksonTestCase {

    protected abstract JsonDeserializer<T> createDeserializer();

    public void testDecodeNullValue() {
        assertNull( deserialize( "null" ) );
    }

    protected T deserialize( String value ) {
        JsonReader reader = new JsonReader( value );
        reader.setLenient( true );
        return createDeserializer().decode( reader, new JsonDecodingContext( reader ) );
    }

    protected void assertDeserialization( T expected, String value ) {
        assertEquals( expected, deserialize( value ) );
    }

    public abstract void testDecodeValue();
}
