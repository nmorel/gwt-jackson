package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.LongKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class LongKeyDeserializerTest extends AbstractKeyDeserializerTest<Long> {

    @Override
    protected LongKeyDeserializer createDeserializer() {
        return LongKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 3441764551145441542l, "3441764551145441542" );
        assertDeserialization( new Long( "-3441764551145441542" ), "-3441764551145441542" );
        assertDeserialization( Long.MIN_VALUE, "-9223372036854775808" );
        assertDeserialization( Long.MAX_VALUE, "9223372036854775807" );
    }
}
