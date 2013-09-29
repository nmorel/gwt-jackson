package com.github.nmorel.gwtjackson.client.ser;

import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializerTest.EnumTest;

/**
 * @author Nicolas Morel
 */
public class EnumJsonSerializerTest extends AbstractJsonSerializerTest<EnumTest> {

    protected static enum EnumTest {
        ONE, TWO, THREE, FOUR
    }

    @Override
    protected JsonSerializer<EnumTest> createSerializer() {
        return EnumJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "\"ONE\"", EnumTest.ONE );
        assertSerialization( "\"TWO\"", EnumTest.TWO );
        assertSerialization( "\"THREE\"", EnumTest.THREE );
        assertSerialization( "\"FOUR\"", EnumTest.FOUR );
    }
}
