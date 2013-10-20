package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonPropertyOrderJacksonTest extends AbstractJacksonTest {

    @Test
    @Ignore("jackson has a different natural order")
    public void testSerializeBeanWithPropertiesNotOrdered() {
        JsonPropertyOrderTester.INSTANCE
            .testSerializeBeanWithPropertiesNotOrdered( createWriter( JsonPropertyOrderTester.BeanWithPropertiesNotOrdered.class ) );
    }

    @Test
    public void testSerializeBeanWithDefinedOrder() {
        JsonPropertyOrderTester.INSTANCE
            .testSerializeBeanWithDefinedOrder( createWriter( JsonPropertyOrderTester.BeanWithDefinedOrder.class ) );
    }

    @Test
    public void testSerializeBeanWithSomeDefinedOrder() {
        JsonPropertyOrderTester.INSTANCE
            .testSerializeBeanWithSomeDefinedOrder( createWriter( JsonPropertyOrderTester.BeanWithSomeDefinedOrder.class ) );
    }

    @Test
    public void testSerializeBeanWithAlphabeticOrder() {
        JsonPropertyOrderTester.INSTANCE
            .testSerializeBeanWithAlphabeticOrder( createWriter( JsonPropertyOrderTester.BeanWithAlphabeticOrder.class ) );
    }

    @Test
    public void testSerializeBeanWithSomeDefinedAndRestAlphabeticOrder() {
        JsonPropertyOrderTester.INSTANCE
            .testSerializeBeanWithSomeDefinedAndRestAlphabeticOrder( createWriter( JsonPropertyOrderTester
                .BeanWithSomeDefinedAndRestAlphabeticOrder.class ) );
    }

    @Test
    @Ignore("jackson doesn't support it yet")
    public void testDeserializeBeanWithMissingRequiredProperties() {
        JsonPropertyOrderTester.INSTANCE
            .testDeserializeBeanWithMissingRequiredProperties( createReader( JsonPropertyOrderTester.BeanWithPropertiesNotOrdered.class ) );
    }
}
