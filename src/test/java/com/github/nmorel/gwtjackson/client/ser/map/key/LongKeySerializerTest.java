package com.github.nmorel.gwtjackson.client.ser.map.key;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.LongKeySerializer;

/**
 * @author Nicolas Morel
 */
public class LongKeySerializerTest extends AbstractKeySerializerTest<Long> {

    @Override
    protected LongKeySerializer createSerializer() {
        return LongKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "3441764551145441542", 3441764551145441542l );
        assertSerialization( "-3441764551145441542", -3441764551145441542l );
        assertSerialization( "-9223372036854775808", Long.MIN_VALUE );
        assertSerialization( "9223372036854775807", Long.MAX_VALUE );
    }
}
