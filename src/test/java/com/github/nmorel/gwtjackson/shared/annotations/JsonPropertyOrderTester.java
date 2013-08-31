package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;

import static junit.framework.Assert.assertEquals;

/** @author Nicolas Morel */
public final class JsonPropertyOrderTester extends AbstractTester
{
    @JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.ANY )
    public static class BeanWithPropertiesNotOrdered
    {
        protected int intProperty;
        protected String stringProperty;
        @JsonProperty( "aBooleanProperty" )
        protected boolean booleanProperty;
        protected double doubleProperty;
    }

    @JsonPropertyOrder( value = {"doubleProperty", "stringProperty", "aBooleanProperty", "intProperty"} )
    public static class BeanWithDefinedOrder extends BeanWithPropertiesNotOrdered
    {
    }

    @JsonPropertyOrder( value = {"doubleProperty"} )
    public static class BeanWithSomeDefinedOrder extends BeanWithPropertiesNotOrdered
    {
    }

    @JsonPropertyOrder( alphabetic = true)
    public static class BeanWithAlphabeticOrder extends BeanWithPropertiesNotOrdered
    {
    }

    @JsonPropertyOrder( value = {"doubleProperty"} , alphabetic = true)
    public static class BeanWithSomeDefinedAndRestAlphabeticOrder extends BeanWithPropertiesNotOrdered
    {
    }

    public static final JsonPropertyOrderTester INSTANCE = new JsonPropertyOrderTester();

    private JsonPropertyOrderTester()
    {
    }

    public void testEncodingBeanWithPropertiesNotOrdered( JsonEncoderTester<BeanWithPropertiesNotOrdered> encoder )
    {
        BeanWithPropertiesNotOrdered bean = new BeanWithPropertiesNotOrdered();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true," +
            "\"doubleProperty\":45.7}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testEncodingBeanWithDefinedOrder( JsonEncoderTester<BeanWithDefinedOrder> encoder )
    {
        BeanWithDefinedOrder bean = new BeanWithDefinedOrder();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"doubleProperty\":45.7," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true," +
            "\"intProperty\":15}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testEncodingBeanWithSomeDefinedOrder( JsonEncoderTester<BeanWithSomeDefinedOrder> encoder )
    {
        BeanWithSomeDefinedOrder bean = new BeanWithSomeDefinedOrder();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"doubleProperty\":45.7," +
            "\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testEncodingBeanWithAlphabeticOrder( JsonEncoderTester<BeanWithAlphabeticOrder> encoder )
    {
        BeanWithAlphabeticOrder bean = new BeanWithAlphabeticOrder();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"aBooleanProperty\":true," +
            "\"doubleProperty\":45.7," +
            "\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testEncodingBeanWithSomeDefinedAndRestAlphabeticOrder( JsonEncoderTester<BeanWithSomeDefinedAndRestAlphabeticOrder> encoder )
    {
        BeanWithSomeDefinedAndRestAlphabeticOrder bean = new BeanWithSomeDefinedAndRestAlphabeticOrder();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"doubleProperty\":45.7," +
            "\"aBooleanProperty\":true," +
            "\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

}
