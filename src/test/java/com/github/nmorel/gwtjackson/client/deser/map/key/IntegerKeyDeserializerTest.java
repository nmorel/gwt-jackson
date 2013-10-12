package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.IntegerKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class IntegerKeyDeserializerTest extends AbstractKeyDeserializerTest<Integer> {

    @Override
    protected IntegerKeyDeserializer createDeserializer() {
        return IntegerKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 34, "34" );
        assertDeserialization( -1, "-1" );
        assertDeserialization( Integer.MIN_VALUE, "-2147483648" );
        assertDeserialization( Integer.MAX_VALUE, "2147483647" );
    }
}
