package com.github.nmorel.gwtjackson.client.mapper;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;

/** @author Nicolas Morel */
public class BigDecimalJsonMapperTest extends GwtJacksonTestCase
{
    public void testNullValue() throws IOException
    {
        BigDecimalJsonMapper mapper = new BigDecimalJsonMapper();
        assertNull( mapper.decode( "null" ) );
    }

}
