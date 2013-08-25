package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;

/** @author Nicolas Morel */
public abstract class AbstractJsonMapperTest<T extends JsonMapper<?>> extends GwtJacksonTestCase
{
    protected abstract T createMapper();

    public void testDecodeNullValue()
    {
        assertNull( createMapper().decode( "null" ) );
    }

    public void testEncodeNullValue()
    {
        assertEquals( "null", createMapper().encode( null ) );
    }

    public void testDecodeValue()
    {
        testDecodeValue( createMapper() );
    }

    protected abstract void testDecodeValue( T mapper );

    public void testEncodeValue()
    {
        testEncodeValue( createMapper() );
    }

    protected abstract void testEncodeValue( T mapper );
}
