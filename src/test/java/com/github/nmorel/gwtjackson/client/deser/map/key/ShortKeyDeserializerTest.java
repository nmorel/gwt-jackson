package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.ShortKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class ShortKeyDeserializerTest extends AbstractKeyDeserializerTest<Short> {

    @Override
    protected ShortKeyDeserializer createDeserializer() {
        return ShortKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Short( "34" ), "34" );
        assertDeserialization( new Short( "-1" ), "-1" );
        assertDeserialization( Short.MIN_VALUE, "-32768" );
        assertDeserialization( Short.MAX_VALUE, "32767" );
    }
}
