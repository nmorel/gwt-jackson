package com.github.nmorel.gwtjackson.client.deser;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class CharacterJsonDeserializerTest extends AbstractJsonDeserializerTest<Character> {

    @Override
    protected JsonDeserializer<Character> createDeserializer() {
        return CharacterJsonDeserializer.getInstance();
    }

    @Override
    public void testDecodeValue() {
        assertDeserialization( 'e', "e" );
        assertDeserialization( '\u00e9', "\"\u00e9\"" );
    }
}
