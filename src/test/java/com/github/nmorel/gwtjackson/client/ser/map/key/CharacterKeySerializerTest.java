package com.github.nmorel.gwtjackson.client.ser.map.key;

/**
 * @author Nicolas Morel
 */
public class CharacterKeySerializerTest extends AbstractKeySerializerTest<Character> {

    @Override
    protected CharacterKeySerializer createSerializer() {
        return CharacterKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization("e", 'e');
        assertSerialization("ë", 'ë');
    }
}
