package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.BigDecimalKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class BigDecimalKeyDeserializerTest extends AbstractKeyDeserializerTest<BigDecimal> {

    @Override
    protected BigDecimalKeyDeserializer createDeserializer() {
        return BigDecimalKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        String value = "15487846511321245665435132032454.1545815468465578451323888744";
        BigDecimal expected = new BigDecimal( value );
        assertDeserialization( expected, value );
    }

}
