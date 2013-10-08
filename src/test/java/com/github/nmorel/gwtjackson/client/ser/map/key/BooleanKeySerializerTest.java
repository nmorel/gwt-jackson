package com.github.nmorel.gwtjackson.client.ser.map.key;

/**
 * @author Nicolas Morel
 */
public class BooleanKeySerializerTest extends AbstractKeySerializerTest<Boolean> {

    @Override
    protected BooleanKeySerializer createSerializer() {
        return BooleanKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization("true", true);
        assertSerialization("true", Boolean.TRUE);
        assertSerialization("false", false);
        assertSerialization("false", Boolean.FALSE);
    }
}
