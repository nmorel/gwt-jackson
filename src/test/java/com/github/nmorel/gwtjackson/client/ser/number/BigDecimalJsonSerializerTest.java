package com.github.nmorel.gwtjackson.client.ser.number;

import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.BigDecimalJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class BigDecimalJsonSerializerTest extends AbstractJsonSerializerTest<BigDecimal> {

    @Override
    protected BigDecimalJsonSerializer createSerializer() {
        return BigDecimalJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        BigDecimal value = new BigDecimal( "15487846511321245665435132032454.1545815468465578451323888744" );
        assertSerialization( "15487846511321245665435132032454.1545815468465578451323888744", value );
    }

}
