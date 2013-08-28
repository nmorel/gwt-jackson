package com.github.nmorel.gwtjackson.client.mapper;

import java.math.BigInteger;

/** @author Nicolas Morel */
public class BigIntegerJsonMapperTest extends AbstractJsonMapperTest<BigIntegerJsonMapper>
{

    @Override
    protected BigIntegerJsonMapper createMapper()
    {
        return new BigIntegerJsonMapper();
    }

    @Override
    protected void testDecodeValue( BigIntegerJsonMapper mapper )
    {
        String value = "1548784651132124566543513203245448715154542123114001571970";
        BigInteger expected = new BigInteger( value );

        // test with a string
        BigInteger result = mapper.decode( "\"" + value + "\"" );
        assertEquals( expected, result );

        // test with a number
        result = mapper.decode( value );
        assertEquals( expected, result );
    }

    @Override
    protected void testEncodeValue( BigIntegerJsonMapper mapper )
    {
        BigInteger value = new BigInteger( "1548784651132124566543513203245448715154542123114001571970" );
        assertEquals( "1548784651132124566543513203245448715154542123114001571970", mapper.encode( value ) );
    }

}
