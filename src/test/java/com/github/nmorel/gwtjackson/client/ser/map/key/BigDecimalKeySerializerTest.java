package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.BigDecimalKeySerializer;

/**
 * @author Nicolas Morel
 */
public class BigDecimalKeySerializerTest extends AbstractKeySerializerTest<BigDecimal> {

    @Override
    protected BigDecimalKeySerializer createSerializer() {
        return BigDecimalKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        BigDecimal value = new BigDecimal( "15487846511321245665435132032454.1545815468465578451323888744" );
        assertSerialization( "15487846511321245665435132032454.1545815468465578451323888744", value );
    }

}
