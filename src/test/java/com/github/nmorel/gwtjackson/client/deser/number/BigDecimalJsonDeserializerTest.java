package com.github.nmorel.gwtjackson.client.deser.number;

import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.BigDecimalJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class BigDecimalJsonDeserializerTest extends AbstractJsonDeserializerTest<BigDecimal> {

    @Override
    protected BigDecimalJsonDeserializer createDeserializer() {
        return BigDecimalJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        String value = "15487846511321245665435132032454.1545815468465578451323888744";
        BigDecimal expected = new BigDecimal( value );

        // test with a string
        assertDeserialization( expected, "\"" + value + "\"" );

        // test with a number
        assertDeserialization( expected, value );
    }

}
