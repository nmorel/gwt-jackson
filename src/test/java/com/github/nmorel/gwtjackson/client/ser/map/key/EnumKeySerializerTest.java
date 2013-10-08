package com.github.nmorel.gwtjackson.client.ser.map.key;

import com.github.nmorel.gwtjackson.client.ser.map.key.EnumKeySerializerTest.EnumTest;

/**
 * @author Nicolas Morel
 */
public class EnumKeySerializerTest extends AbstractKeySerializerTest<EnumTest> {

    protected static enum EnumTest {
        ONE, TWO, THREE, FOUR
    }

    @Override
    protected EnumKeySerializer<EnumTest> createSerializer() {
        return EnumKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "ONE", EnumTest.ONE );
        assertSerialization( "TWO", EnumTest.TWO );
        assertSerialization( "THREE", EnumTest.THREE );
        assertSerialization( "FOUR", EnumTest.FOUR );
    }
}
