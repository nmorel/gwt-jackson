package com.github.nmorel.gwtjackson.client.ser.number;

import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.NumberJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class BigIntegerJsonSerializerTest extends AbstractJsonSerializerTest<BigInteger> {

    @Override
    protected NumberJsonSerializer<BigInteger> createSerializer() {
        return NumberJsonSerializer.getBigIntegerInstance();
    }

    public void testEncodeValue() {
        BigInteger value = new BigInteger( "1548784651132124566543513203245448715154542123114001571970" );
        assertSerialization( "1548784651132124566543513203245448715154542123114001571970", value );
    }

}
