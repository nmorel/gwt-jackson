package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.DoubleKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class DoubleKeyDeserializerTest extends AbstractKeyDeserializerTest<Double> {

    @Override
    protected DoubleKeyDeserializer createDeserializer() {
        return DoubleKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 34.100247d, "34.100247" );
        assertDeserialization( -487.15487d, "-487.15487" );
        assertDeserialization( -784.15454d, "-784.15454" );
        assertDeserialization( Double.MIN_VALUE, "4.9E-324" );
        assertDeserialization( Double.MAX_VALUE, "1.7976931348623157e+308" );
    }
}
