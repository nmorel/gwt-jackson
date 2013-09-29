package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class FloatJsonDeserializerTest extends AbstractJsonDeserializerTest<Float> {

    @Override
    protected JsonDeserializer<Float> createDeserializer() {
        return NumberJsonDeserializer.getFloatInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Float( "34.10245" ), "34.10245" );
        assertDeserialization( new Float( "-784.15454" ), "\"-784.15454\"" );
        // the float emulation gives slightly different results => use double!
        if ( !GWT.isProdMode() ) {
            assertDeserialization( Float.MIN_VALUE, "1.4e-45" );
            assertDeserialization( Float.MAX_VALUE, "3.4028235e38" );
        }
    }
}
