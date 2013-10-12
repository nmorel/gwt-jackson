package com.github.nmorel.gwtjackson.client.ser.map.key;

import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.BigIntegerKeySerializer;

/**
 * @author Nicolas Morel
 */
public class BigIntegerKeySerializerTest extends AbstractKeySerializerTest<BigInteger> {

    @Override
    protected BigIntegerKeySerializer createSerializer() {
        return BigIntegerKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        BigInteger value = new BigInteger( "1548784651132124566543513203245448715154542123114001571970" );
        assertSerialization( "1548784651132124566543513203245448715154542123114001571970", value );
    }

}
