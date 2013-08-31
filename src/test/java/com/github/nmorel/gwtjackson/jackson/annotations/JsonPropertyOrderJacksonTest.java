package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester;
import org.junit.Ignore;
import org.junit.Test;

/** @author Nicolas Morel */
public class JsonPropertyOrderJacksonTest extends AbstractJacksonTest
{
    @Test
    @Ignore( "jackson has a different natural order" )
    public void testEncodingBeanWithPropertiesNotOrdered()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithPropertiesNotOrdered( createEncoder( JsonPropertyOrderTester.BeanWithPropertiesNotOrdered.class ) );
    }

    @Test
    public void testEncodingBeanWithDefinedOrder()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithDefinedOrder( createEncoder( JsonPropertyOrderTester.BeanWithDefinedOrder.class ) );
    }

    @Test
    public void testEncodingBeanWithSomeDefinedOrder()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithSomeDefinedOrder( createEncoder( JsonPropertyOrderTester.BeanWithSomeDefinedOrder.class ) );
    }

    @Test
    public void testEncodingBeanWithAlphabeticOrder()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithAlphabeticOrder( createEncoder( JsonPropertyOrderTester.BeanWithAlphabeticOrder.class ) );
    }

    @Test
    public void testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder( createEncoder( JsonPropertyOrderTester
                .BeanWithSomeDefinedAndRestAlphabeticOrder.class ) );
    }
}
