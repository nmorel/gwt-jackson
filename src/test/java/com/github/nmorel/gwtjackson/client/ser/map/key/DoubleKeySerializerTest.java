package com.github.nmorel.gwtjackson.client.ser.map.key;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.DoubleKeySerializer;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class DoubleKeySerializerTest extends AbstractKeySerializerTest<Double> {

    @Override
    protected DoubleKeySerializer createSerializer() {
        return DoubleKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "34.100247", 34.100247 );
        assertSerialization( "-784.15454", -784.15454d );
        assertSerialization( (GWT.isProdMode() ? "5e-324" : "4.9E-324"), Double.MIN_VALUE );
        assertSerialization( (GWT.isProdMode() ? "1.7976931348623157e+308" : "1.7976931348623157E308"), Double.MAX_VALUE );
    }
}
