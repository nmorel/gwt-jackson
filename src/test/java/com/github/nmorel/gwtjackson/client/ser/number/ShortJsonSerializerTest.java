package com.github.nmorel.gwtjackson.client.ser.number;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.ShortJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class ShortJsonSerializerTest extends AbstractJsonSerializerTest<Short> {

    @Override
    protected ShortJsonSerializer createSerializer() {
        return ShortJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "34", new Short( "34" ) );
        assertSerialization( "-1", new Short( "-1" ) );
        assertSerialization( "-32768", Short.MIN_VALUE );
        assertSerialization( "32767", Short.MAX_VALUE );
    }
}
