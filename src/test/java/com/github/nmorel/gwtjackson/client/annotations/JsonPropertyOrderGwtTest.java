package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
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
        ObjectMapperTester<BeanWithPropertiesNotOrdered> {

        static BeanWithPropertiesNotOrderedMapper INSTANCE = GWT.create( BeanWithPropertiesNotOrderedMapper.class );
    }

    public interface BeanWithDefinedOrderMapper extends ObjectMapper<BeanWithDefinedOrder>, ObjectMapperTester<BeanWithDefinedOrder> {

        static BeanWithDefinedOrderMapper INSTANCE = GWT.create( BeanWithDefinedOrderMapper.class );
    }

    public interface BeanWithSomeDefinedOrderMapper extends ObjectMapper<BeanWithSomeDefinedOrder>,
        ObjectMapperTester<BeanWithSomeDefinedOrder> {

        static BeanWithSomeDefinedOrderMapper INSTANCE = GWT.create( BeanWithSomeDefinedOrderMapper.class );
    }

    public interface BeanWithAlphabeticOrderMapper extends ObjectMapper<BeanWithAlphabeticOrder>,
        ObjectMapperTester<BeanWithAlphabeticOrder> {

        static BeanWithAlphabeticOrderMapper INSTANCE = GWT.create( BeanWithAlphabeticOrderMapper.class );
    }

    public interface BeanWithSomeDefinedAndRestAlphabeticOrderMapper extends ObjectMapper<BeanWithSomeDefinedAndRestAlphabeticOrder>,
        ObjectMapperTester<BeanWithSomeDefinedAndRestAlphabeticOrder> {

        static BeanWithSomeDefinedAndRestAlphabeticOrderMapper INSTANCE = GWT
            .create( BeanWithSomeDefinedAndRestAlphabeticOrderMapper.class );
    }

    private JsonPropertyOrderTester tester = JsonPropertyOrderTester.INSTANCE;

    public void testSerializeBeanWithPropertiesNotOrdered() {
        tester.testSerializeBeanWithPropertiesNotOrdered( BeanWithPropertiesNotOrderedMapper.INSTANCE );
    }

    public void testSerializeBeanWithDefinedOrder() {
        tester.testSerializeBeanWithDefinedOrder( BeanWithDefinedOrderMapper.INSTANCE );
    }

    public void testSerializeBeanWithSomeDefinedOrder() {
        tester.testSerializeBeanWithSomeDefinedOrder( BeanWithSomeDefinedOrderMapper.INSTANCE );
    }

    public void testSerializeBeanWithAlphabeticOrder() {
        tester.testSerializeBeanWithAlphabeticOrder( BeanWithAlphabeticOrderMapper.INSTANCE );
    }

    public void testSerializeBeanWithSomeDefinedAndRestAlphabeticOrder() {
        tester.testSerializeBeanWithSomeDefinedAndRestAlphabeticOrder( BeanWithSomeDefinedAndRestAlphabeticOrderMapper.INSTANCE );
    }

    public void testDeserializeBeanWithMissingRequiredProperties() {
        tester.testDeserializeBeanWithMissingRequiredProperties( BeanWithPropertiesNotOrderedMapper.INSTANCE );
    }
}
