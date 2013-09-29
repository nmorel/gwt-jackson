package com.github.nmorel.gwtjackson.client.ser.number;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.NumberJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class ByteJsonSerializerTest extends AbstractJsonSerializerTest<Byte> {

    @Override
    protected NumberJsonSerializer<Byte> createSerializer() {
        return NumberJsonSerializer.getByteInstance();
    }

    public void testEncodeValue() {
        assertSerialization( "34", (byte) 34 );
        assertSerialization( "1", new Byte( "1" ) );
        assertSerialization( "-128", Byte.MIN_VALUE );
        assertSerialization( "127", Byte.MAX_VALUE );
    }
}
