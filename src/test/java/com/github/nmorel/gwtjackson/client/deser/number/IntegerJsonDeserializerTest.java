package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.IntegerJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class IntegerJsonDeserializerTest extends AbstractJsonDeserializerTest<Integer> {

    @Override
    protected IntegerJsonDeserializer createDeserializer() {
        return IntegerJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 34, "34" );
        assertDeserialization( -1, "\"-1\"" );
        assertDeserialization( Integer.MIN_VALUE, "-2147483648" );
        assertDeserialization( Integer.MAX_VALUE, "2147483647" );
    }
}
