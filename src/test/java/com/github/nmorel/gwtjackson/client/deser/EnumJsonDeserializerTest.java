package com.github.nmorel.gwtjackson.client.deser;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializerTest.EnumTest;

/**
 * @author Nicolas Morel
 */
public class EnumJsonDeserializerTest extends AbstractJsonDeserializerTest<EnumTest> {

    protected static enum EnumTest {
        ONE, TWO, THREE, FOUR
    }

    @Override
    protected JsonDeserializer<EnumTest> createDeserializer() {
        return EnumJsonDeserializer.newInstance( EnumTest.class );
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( EnumTest.ONE, "\"ONE\"" );
        assertDeserialization( EnumTest.TWO, "\"TWO\"" );
        assertDeserialization( EnumTest.THREE, "\"THREE\"" );
        assertDeserialization( EnumTest.FOUR, "\"FOUR\"" );
    }
}
