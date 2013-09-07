package com.github.nmorel.gwtjackson.client.mapper.number;

import java.math.BigDecimal;

import com.github.nmorel.gwtjackson.client.mapper.AbstractJsonMapperTest;

/** @author Nicolas Morel */
public class BigDecimalJsonMapperTest extends AbstractJsonMapperTest<BigDecimalJsonMapper>
{
    @Override
    protected BigDecimalJsonMapper createMapper()
    {
        return new BigDecimalJsonMapper();
    }

    @Override
    protected void testDecodeValue( BigDecimalJsonMapper mapper )
    {
        String value = "15487846511321245665435132032454.1545815468465578451323888744";
        BigDecimal expected = new BigDecimal( value );

        // test with a string
        BigDecimal result = mapper.decode( "\"" + value + "\"" );
        assertEquals( expected, result );

        // test with a number
        result = mapper.decode( value );
        assertEquals( expected, result );
    }

    @Override
    protected void testEncodeValue( BigDecimalJsonMapper mapper )
    {
        BigDecimal value = new BigDecimal( "15487846511321245665435132032454.1545815468465578451323888744" );
        assertEquals( "15487846511321245665435132032454.1545815468465578451323888744", mapper.encode( value ) );
    }

}
