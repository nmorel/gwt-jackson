package com.github.nmorel.gwtjackson.client.ser.map.key;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.ShortKeySerializer;

/**
 * @author Nicolas Morel
 */
public class ShortKeySerializerTest extends AbstractKeySerializerTest<Short> {

    @Override
    protected ShortKeySerializer createSerializer() {
        return ShortKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "34", new Short( "34" ) );
        assertSerialization( "-1", new Short( "-1" ) );
        assertSerialization( "-32768", Short.MIN_VALUE );
        assertSerialization( "32767", Short.MAX_VALUE );
    }
}
