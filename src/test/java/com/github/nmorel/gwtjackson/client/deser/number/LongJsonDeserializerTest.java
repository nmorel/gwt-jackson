package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class LongJsonDeserializerTest extends AbstractJsonDeserializerTest<Long> {

    @Override
    protected JsonDeserializer<Long> createDeserializer() {
        return NumberJsonDeserializer.getLongInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 3441764551145441542l, "3441764551145441542" );
        assertDeserialization( new Long( "-3441764551145441542" ), "\"-3441764551145441542\"" );
        assertDeserialization( Long.MIN_VALUE, "-9223372036854775808" );
        assertDeserialization( Long.MAX_VALUE, "9223372036854775807" );
    }
}
