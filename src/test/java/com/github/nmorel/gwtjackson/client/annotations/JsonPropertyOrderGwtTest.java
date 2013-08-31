package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester;
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonPropertyOrderGwtTest extends GwtJacksonTestCase
{
    public interface BeanWithPropertiesNotOrderedMapper extends JsonMapper<JsonPropertyOrderTester.BeanWithPropertiesNotOrdered>
    {
    }

    public interface BeanWithDefinedOrderMapper extends JsonMapper<JsonPropertyOrderTester.BeanWithDefinedOrder>
    {
    }

    public interface BeanWithSomeDefinedOrderMapper extends JsonMapper<JsonPropertyOrderTester.BeanWithSomeDefinedOrder>
    {
    }

    public interface BeanWithAlphabeticOrderMapper extends JsonMapper<JsonPropertyOrderTester.BeanWithAlphabeticOrder>
    {
    }

    public interface BeanWithSomeDefinedAndRestAlphabeticOrderMapper extends JsonMapper<JsonPropertyOrderTester
        .BeanWithSomeDefinedAndRestAlphabeticOrder>
    {
    }

    public void testEncodingBeanWithPropertiesNotOrdered()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithPropertiesNotOrdered( new JsonEncoderTester<JsonPropertyOrderTester.BeanWithPropertiesNotOrdered>()
            {
                @Override
                public String encode( JsonPropertyOrderTester.BeanWithPropertiesNotOrdered input )
                {
                    return GWT.<BeanWithPropertiesNotOrderedMapper>create( BeanWithPropertiesNotOrderedMapper.class ).encode( input );
                }
            } );
    }

    public void testEncodingBeanWithDefinedOrder()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithDefinedOrder( new JsonEncoderTester<JsonPropertyOrderTester.BeanWithDefinedOrder>()
            {
                @Override
                public String encode( JsonPropertyOrderTester.BeanWithDefinedOrder input )
                {
                    return GWT.<BeanWithDefinedOrderMapper>create( BeanWithDefinedOrderMapper.class ).encode( input );
                }
            } );
    }

    public void testEncodingBeanWithSomeDefinedOrder()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithSomeDefinedOrder( new JsonEncoderTester<JsonPropertyOrderTester.BeanWithSomeDefinedOrder>()
            {
                @Override
                public String encode( JsonPropertyOrderTester.BeanWithSomeDefinedOrder input )
                {
                    return GWT.<BeanWithSomeDefinedOrderMapper>create( BeanWithSomeDefinedOrderMapper.class ).encode( input );
                }
            } );
    }

    public void testEncodingBeanWithAlphabeticOrder()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithAlphabeticOrder( new JsonEncoderTester<JsonPropertyOrderTester.BeanWithAlphabeticOrder>()
            {
                @Override
                public String encode( JsonPropertyOrderTester.BeanWithAlphabeticOrder input )
                {
                    return GWT.<BeanWithAlphabeticOrderMapper>create( BeanWithAlphabeticOrderMapper.class ).encode( input );
                }
            } );
    }

    public void testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder()
    {
        JsonPropertyOrderTester.INSTANCE
            .testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder( new JsonEncoderTester<JsonPropertyOrderTester
                .BeanWithSomeDefinedAndRestAlphabeticOrder>()
            {
                @Override
                public String encode( JsonPropertyOrderTester.BeanWithSomeDefinedAndRestAlphabeticOrder input )
                {
                    return GWT
                        .<BeanWithSomeDefinedAndRestAlphabeticOrderMapper>create( BeanWithSomeDefinedAndRestAlphabeticOrderMapper.class )
                        .encode( input );
                }
            } );
    }
}
