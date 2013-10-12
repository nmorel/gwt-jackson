package com.github.nmorel.gwtjackson.client.ser.map.key;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.ByteKeySerializer;

/**
 * @author Nicolas Morel
 */
public class ByteKeySerializerTest extends AbstractKeySerializerTest<Byte> {

    @Override
    protected ByteKeySerializer createSerializer() {
        return ByteKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "34", (byte) 34 );
        assertSerialization( "1", new Byte( "1" ) );
        assertSerialization( "-128", Byte.MIN_VALUE );
        assertSerialization( "127", Byte.MAX_VALUE );
    }
}
