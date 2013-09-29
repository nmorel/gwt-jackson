package com.github.nmorel.gwtjackson.client.ser.number;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.NumberJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class LongJsonSerializerTest extends AbstractJsonSerializerTest<Long> {

    @Override
    protected NumberJsonSerializer<Long> createSerializer() {
        return NumberJsonSerializer.getLongInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "3441764551145441542", 3441764551145441542l );
        assertSerialization( "-3441764551145441542", -3441764551145441542l );
        assertSerialization( "-9223372036854775808", Long.MIN_VALUE );
        assertSerialization( "9223372036854775807", Long.MAX_VALUE );
    }
}
