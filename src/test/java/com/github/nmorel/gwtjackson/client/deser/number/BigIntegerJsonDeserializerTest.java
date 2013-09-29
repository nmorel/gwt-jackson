package com.github.nmorel.gwtjackson.client.deser.number;

import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class BigIntegerJsonDeserializerTest extends AbstractJsonDeserializerTest<BigInteger> {

    @Override
    protected JsonDeserializer<BigInteger> createDeserializer() {
        return NumberJsonDeserializer.getBigIntegerInstance();
    }

    @Override
    public void testDecodeValue() {
        String value = "1548784651132124566543513203245448715154542123114001571970";
        BigInteger expected = new BigInteger( value );

        // test with a string
        assertDeserialization( expected, "\"" + value + "\"" );

        // test with a number
        assertDeserialization( expected, value );
    }

}
