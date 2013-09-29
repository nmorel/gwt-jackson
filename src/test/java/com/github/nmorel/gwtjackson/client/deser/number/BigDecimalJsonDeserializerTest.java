package com.github.nmorel.gwtjackson.client.deser.number;

import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class BigDecimalJsonDeserializerTest extends AbstractJsonDeserializerTest<BigDecimal> {

    @Override
    protected JsonDeserializer<BigDecimal> createDeserializer() {
        return NumberJsonDeserializer.getBigDecimalInstance();
    }

    @Override
    public void testDecodeValue() {
        String value = "15487846511321245665435132032454.1545815468465578451323888744";
        BigDecimal expected = new BigDecimal( value );

        // test with a string
        assertDeserialization( expected, "\"" + value + "\"" );

        // test with a number
        assertDeserialization( expected, value );
    }

}
