package com.github.nmorel.gwtjackson.client.ser;

/**
 * @author Nicolas Morel
 */
public class CharacterJsonSerializerTest extends AbstractJsonSerializerTest<Character> {

    @Override
    protected CharacterJsonSerializer createSerializer() {
        return CharacterJsonSerializer.getInstance();
    }

    public void testEncodeValue() {
        assertSerialization( "\"e\"", 'e' );
        assertSerialization( "\"\\u0000\"", '\u0000' );
    }
}
