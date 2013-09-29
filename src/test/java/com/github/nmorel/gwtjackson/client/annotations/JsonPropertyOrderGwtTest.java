package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester.BeanWithAlphabeticOrder;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester.BeanWithDefinedOrder;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester.BeanWithPropertiesNotOrdered;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester.BeanWithSomeDefinedAndRestAlphabeticOrder;
import com.github.nmorel.gwtjackson.shared.annotations.JsonPropertyOrderTester.BeanWithSomeDefinedOrder;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonPropertyOrderGwtTest extends GwtJacksonTestCase {

    public interface BeanWithPropertiesNotOrderedMapper extends ObjectMapper<BeanWithPropertiesNotOrdered>,
        JsonMapperTester<BeanWithPropertiesNotOrdered> {

        static BeanWithPropertiesNotOrderedMapper INSTANCE = GWT.create( BeanWithPropertiesNotOrderedMapper.class );
    }

    public interface BeanWithDefinedOrderMapper extends ObjectMapper<BeanWithDefinedOrder>, JsonMapperTester<BeanWithDefinedOrder> {

        static BeanWithDefinedOrderMapper INSTANCE = GWT.create( BeanWithDefinedOrderMapper.class );
    }

    public interface BeanWithSomeDefinedOrderMapper extends ObjectMapper<BeanWithSomeDefinedOrder>,
        JsonMapperTester<BeanWithSomeDefinedOrder> {

        static BeanWithSomeDefinedOrderMapper INSTANCE = GWT.create( BeanWithSomeDefinedOrderMapper.class );
    }

    public interface BeanWithAlphabeticOrderMapper extends ObjectMapper<BeanWithAlphabeticOrder>,
        JsonMapperTester<BeanWithAlphabeticOrder> {

        static BeanWithAlphabeticOrderMapper INSTANCE = GWT.create( BeanWithAlphabeticOrderMapper.class );
    }

    public interface BeanWithSomeDefinedAndRestAlphabeticOrderMapper extends ObjectMapper<BeanWithSomeDefinedAndRestAlphabeticOrder>,
        JsonMapperTester<BeanWithSomeDefinedAndRestAlphabeticOrder> {

        static BeanWithSomeDefinedAndRestAlphabeticOrderMapper INSTANCE = GWT
            .create( BeanWithSomeDefinedAndRestAlphabeticOrderMapper.class );
    }

    private JsonPropertyOrderTester tester = JsonPropertyOrderTester.INSTANCE;

    public void testEncodingBeanWithPropertiesNotOrdered() {
        tester.testEncodingBeanWithPropertiesNotOrdered( BeanWithPropertiesNotOrderedMapper.INSTANCE );
    }

    public void testEncodingBeanWithDefinedOrder() {
        tester.testEncodingBeanWithDefinedOrder( BeanWithDefinedOrderMapper.INSTANCE );
    }

    public void testEncodingBeanWithSomeDefinedOrder() {
        tester.testEncodingBeanWithSomeDefinedOrder( BeanWithSomeDefinedOrderMapper.INSTANCE );
    }

    public void testEncodingBeanWithAlphabeticOrder() {
        tester.testEncodingBeanWithAlphabeticOrder( BeanWithAlphabeticOrderMapper.INSTANCE );
    }

    public void testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder() {
        tester.testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder( BeanWithSomeDefinedAndRestAlphabeticOrderMapper.INSTANCE );
    }

    public void testDecodingBeanWithMissingRequiredProperties() {
        tester.testDecodingBeanWithMissingRequiredProperties( BeanWithPropertiesNotOrderedMapper.INSTANCE );
    }
}
