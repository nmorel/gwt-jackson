package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonPropertyOrderGwtTest extends GwtJacksonTestCase
{
    public interface BeanWithPropertiesNotOrderedMapper extends JsonMapper<JsonPropertyOrderTester.BeanWithPropertiesNotOrdered>
    {
        static BeanWithPropertiesNotOrderedMapper INSTANCE = GWT.create( BeanWithPropertiesNotOrderedMapper.class );
    }

    public interface BeanWithDefinedOrderMapper extends JsonMapper<JsonPropertyOrderTester.BeanWithDefinedOrder>
    {
        static BeanWithDefinedOrderMapper INSTANCE = GWT.create( BeanWithDefinedOrderMapper.class );
    }

    public interface BeanWithSomeDefinedOrderMapper extends JsonMapper<JsonPropertyOrderTester.BeanWithSomeDefinedOrder>
    {
        static BeanWithSomeDefinedOrderMapper INSTANCE = GWT.create( BeanWithSomeDefinedOrderMapper.class );
    }

    public interface BeanWithAlphabeticOrderMapper extends JsonMapper<JsonPropertyOrderTester.BeanWithAlphabeticOrder>
    {
        static BeanWithAlphabeticOrderMapper INSTANCE = GWT.create( BeanWithAlphabeticOrderMapper.class );
    }

    public interface BeanWithSomeDefinedAndRestAlphabeticOrderMapper extends JsonMapper<JsonPropertyOrderTester
        .BeanWithSomeDefinedAndRestAlphabeticOrder>
    {
        static BeanWithSomeDefinedAndRestAlphabeticOrderMapper INSTANCE = GWT
            .create( BeanWithSomeDefinedAndRestAlphabeticOrderMapper.class );
    }

    private JsonPropertyOrderTester tester = JsonPropertyOrderTester.INSTANCE;

    public void testEncodingBeanWithPropertiesNotOrdered()
    {
        tester.testEncodingBeanWithPropertiesNotOrdered( createEncoder( BeanWithPropertiesNotOrderedMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithDefinedOrder()
    {
        tester.testEncodingBeanWithDefinedOrder( createEncoder( BeanWithDefinedOrderMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithSomeDefinedOrder()
    {
        tester.testEncodingBeanWithSomeDefinedOrder( createEncoder( BeanWithSomeDefinedOrderMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithAlphabeticOrder()
    {
        tester.testEncodingBeanWithAlphabeticOrder( createEncoder( BeanWithAlphabeticOrderMapper.INSTANCE ) );
    }

    public void testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder()
    {
        tester
            .testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder( createEncoder( BeanWithSomeDefinedAndRestAlphabeticOrderMapper
                .INSTANCE ) );
    }

    public void testDecodingBeanWithMissingRequiredProperties()
    {
        tester.testDecodingBeanWithMissingRequiredProperties( createDecoder( BeanWithPropertiesNotOrderedMapper.INSTANCE ) );
    }
}
