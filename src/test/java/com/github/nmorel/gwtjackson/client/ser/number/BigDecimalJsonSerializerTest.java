package com.github.nmorel.gwtjackson.client.ser.number;

import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.NumberJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class BigDecimalJsonSerializerTest extends AbstractJsonSerializerTest<BigDecimal> {

    @Override
    protected NumberJsonSerializer<BigDecimal> createSerializer() {
        return NumberJsonSerializer.getBigDecimalInstance();
    }

    public void testEncodeValue() {
        BigDecimal value = new BigDecimal( "15487846511321245665435132032454.1545815468465578451323888744" );
        assertSerialization( "15487846511321245665435132032454.1545815468465578451323888744", value );
    }

}
