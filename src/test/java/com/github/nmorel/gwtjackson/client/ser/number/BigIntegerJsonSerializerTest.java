package com.github.nmorel.gwtjackson.client.ser.number;

import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.BigIntegerJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class BigIntegerJsonSerializerTest extends AbstractJsonSerializerTest<BigInteger> {

    @Override
    protected BigIntegerJsonSerializer createSerializer() {
        return BigIntegerJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        BigInteger value = new BigInteger( "1548784651132124566543513203245448715154542123114001571970" );
        assertSerialization( "1548784651132124566543513203245448715154542123114001571970", value );
    }

}
