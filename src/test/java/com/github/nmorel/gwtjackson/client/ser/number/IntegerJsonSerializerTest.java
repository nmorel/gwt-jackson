package com.github.nmorel.gwtjackson.client.ser.number;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.NumberJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class IntegerJsonSerializerTest extends AbstractJsonSerializerTest<Integer> {

    @Override
    protected NumberJsonSerializer<Integer> createSerializer() {
        return NumberJsonSerializer.getIntegerInstance();
    }

    public void testEncodeValue() {
        assertSerialization( "34", 34 );
        assertSerialization( "-1", -1 );
        assertSerialization( "-2147483648", Integer.MIN_VALUE );
        assertSerialization( "2147483647", Integer.MAX_VALUE );
    }
}
