package com.github.nmorel.gwtjackson.client.ser.map.key;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.IntegerKeySerializer;

/**
 * @author Nicolas Morel
 */
public class IntegerKeySerializerTest extends AbstractKeySerializerTest<Integer> {

    @Override
    protected IntegerKeySerializer createSerializer() {
        return IntegerKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "34", 34 );
        assertSerialization( "-1", -1 );
        assertSerialization( "-2147483648", Integer.MIN_VALUE );
        assertSerialization( "2147483647", Integer.MAX_VALUE );
    }
}
