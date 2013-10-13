package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.ShortJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class ShortJsonDeserializerTest extends AbstractJsonDeserializerTest<Short> {

    @Override
    protected ShortJsonDeserializer createDeserializer() {
        return ShortJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Short( "34" ), "34" );
        assertDeserialization( new Short( "-1" ), "\"-1\"" );
        assertDeserialization( Short.MIN_VALUE, "-32768" );
        assertDeserialization( Short.MAX_VALUE, "32767" );
    }
}
