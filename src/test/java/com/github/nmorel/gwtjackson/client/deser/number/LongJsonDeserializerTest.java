package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.LongJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class LongJsonDeserializerTest extends AbstractJsonDeserializerTest<Long> {

    @Override
    protected LongJsonDeserializer createDeserializer() {
        return LongJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 3441764551145441542l, "3441764551145441542" );
        assertDeserialization( new Long( "-3441764551145441542" ), "\"-3441764551145441542\"" );
        assertDeserialization( Long.MIN_VALUE, "-9223372036854775808" );
        assertDeserialization( Long.MAX_VALUE, "9223372036854775807" );
    }
}
