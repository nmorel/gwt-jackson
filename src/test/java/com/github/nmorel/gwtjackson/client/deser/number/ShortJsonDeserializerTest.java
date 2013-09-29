package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class ShortJsonDeserializerTest extends AbstractJsonDeserializerTest<Short> {

    @Override
    protected JsonDeserializer<Short> createDeserializer() {
        return NumberJsonDeserializer.getShortInstance();
    }

    @Override
    public void testDecodeValue() {
        assertDeserialization( new Short( "34" ), "34" );
        assertDeserialization( new Short( "-1" ), "\"-1\"" );
        assertDeserialization( Short.MIN_VALUE, "-32768" );
        assertDeserialization( Short.MAX_VALUE, "32767" );
    }
}
