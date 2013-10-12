package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.EnumKeyDeserializerTest.EnumTest;

/**
 * @author Nicolas Morel
 */
public class EnumKeyDeserializerTest extends AbstractKeyDeserializerTest<EnumTest> {

    protected static enum EnumTest {
        ONE, TWO, THREE, FOUR
    }

    @Override
    protected EnumKeyDeserializer<EnumTest> createDeserializer() {
        return EnumKeyDeserializer.newInstance( EnumTest.class );
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( EnumTest.ONE, "ONE" );
        assertDeserialization( EnumTest.TWO, "TWO" );
        assertDeserialization( EnumTest.THREE, "THREE" );
        assertDeserialization( EnumTest.FOUR, "FOUR" );
    }
}
