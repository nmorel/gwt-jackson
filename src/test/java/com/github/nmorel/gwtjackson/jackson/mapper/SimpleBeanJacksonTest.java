package com.github.nmorel.gwtjackson.jackson.mapper;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.model.SimpleBean;
import com.github.nmorel.gwtjackson.shared.mapper.SimpleBeanJsonMapperTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class SimpleBeanJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncodeValue()
    {
        SimpleBeanJsonMapperTester.INSTANCE.testEncodeValue( createEncoder( SimpleBean.class ) );
    }

    @Test
    public void testDecodeValue()
    {
        SimpleBeanJsonMapperTester.INSTANCE.testDecodeValue( createDecoder( SimpleBean.class ) );
    }

    @Test
    public void testWriteBeanWithNullProperties()
    {
        SimpleBeanJsonMapperTester.INSTANCE.testWriteWithNullProperties( createEncoder( SimpleBean.class ) );
    }
}
