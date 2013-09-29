package com.github.nmorel.gwtjackson.client.ser.number;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.NumberJsonSerializer;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class FloatJsonSerializerTest extends AbstractJsonSerializerTest<Float> {

    @Override
    protected NumberJsonSerializer<Float> createSerializer() {
        return NumberJsonSerializer.getFloatInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "34.10245", new Float( "34.10245" ) );
        assertSerialization( "-784.15454", new Float( "-784.15454" ) );
        // the float emulation gives slightly different results => use double!
        if ( !GWT.isProdMode() ) {
            assertSerialization( "1.4E-45", Float.MIN_VALUE );
            assertSerialization( "3.4028235E38", Float.MAX_VALUE );
        }
    }
}
