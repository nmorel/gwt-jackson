package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.FloatKeyDeserializer;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class FloatKeyDeserializerTest extends AbstractKeyDeserializerTest<Float> {

    @Override
    protected FloatKeyDeserializer createDeserializer() {
        return FloatKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Float( "34.10245" ), "34.10245" );
        assertDeserialization( new Float( "-784.15454" ), "-784.15454" );
        // the float emulation gives slightly different results => use BigDecimal for precision!
        if ( !GWT.isProdMode() ) {
            assertDeserialization( Float.MIN_VALUE, "1.4e-45" );
            assertDeserialization( Float.MAX_VALUE, "3.4028235e38" );
        }
    }
}
