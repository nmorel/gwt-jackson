package com.github.nmorel.gwtjackson.client.ser.map.key;

/**
 * @author Nicolas Morel
 */
public class StringKeySerializerTest extends AbstractKeySerializerTest<String> {

    @Override
    protected StringKeySerializer createSerializer() {
        return StringKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization("Hello World!", "Hello World!");
        assertSerialization("", "");
    }
}
