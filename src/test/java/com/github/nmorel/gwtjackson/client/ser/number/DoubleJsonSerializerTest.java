package com.github.nmorel.gwtjackson.client.ser.number;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.NumberJsonSerializer;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class DoubleJsonSerializerTest extends AbstractJsonSerializerTest<Double> {

    @Override
    protected NumberJsonSerializer<Double> createSerializer() {
        return NumberJsonSerializer.getDoubleInstance();
    }

    public void testEncodeValue() {
        assertSerialization( "34.100247", 34.100247 );
        assertSerialization( "-784.15454", -784.15454d );
        assertSerialization( (GWT.isProdMode() ? "5e-324" : "4.9E-324"), Double.MIN_VALUE );
        assertSerialization( (GWT.isProdMode() ? "1.7976931348623157e+308" : "1.7976931348623157E308"), Double.MAX_VALUE );
    }
}
