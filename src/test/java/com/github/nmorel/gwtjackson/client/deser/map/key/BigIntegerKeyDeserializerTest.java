package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.BigIntegerKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class BigIntegerKeyDeserializerTest extends AbstractKeyDeserializerTest<BigInteger> {

    @Override
    protected BigIntegerKeyDeserializer createDeserializer() {
        return BigIntegerKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        String value = "1548784651132124566543513203245448715154542123114001571970";
        BigInteger expected = new BigInteger( value );
        assertDeserialization( expected, value );
    }

}
