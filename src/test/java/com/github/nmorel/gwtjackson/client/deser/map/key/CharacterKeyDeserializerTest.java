package com.github.nmorel.gwtjackson.client.deser.map.key;

/**
 * @author Nicolas Morel
 */
public class CharacterKeyDeserializerTest extends AbstractKeyDeserializerTest<Character> {

    @Override
    protected CharacterKeyDeserializer createDeserializer() {
        return CharacterKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 'e', "e" );
        assertDeserialization( '\u00e9', "\u00e9" );
    }
}
