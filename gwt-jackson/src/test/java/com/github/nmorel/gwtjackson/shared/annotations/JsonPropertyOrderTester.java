/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class JsonPropertyOrderTester extends AbstractTester {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class BeanWithPropertiesNotOrdered {

        protected int intProperty;

        protected String stringProperty;

        @JsonProperty(value = "aBooleanProperty", required = true)
        protected boolean booleanProperty;

        @JsonProperty(required = true)
        protected double doubleProperty;
    }

    @JsonPropertyOrder(value = {"doubleProperty", "stringProperty", "aBooleanProperty", "intProperty"})
    public static class BeanWithDefinedOrder extends BeanWithPropertiesNotOrdered {}

    @JsonPropertyOrder(value = {"doubleProperty"})
    public static class BeanWithSomeDefinedOrder extends BeanWithPropertiesNotOrdered {}

    @JsonPropertyOrder(alphabetic = true)
    public static class BeanWithAlphabeticOrder extends BeanWithPropertiesNotOrdered {}

    @JsonPropertyOrder(value = {"doubleProperty"}, alphabetic = true)
    public static class BeanWithSomeDefinedAndRestAlphabeticOrder extends BeanWithPropertiesNotOrdered {}

    public static final JsonPropertyOrderTester INSTANCE = new JsonPropertyOrderTester();

    private JsonPropertyOrderTester() {
    }

    public void testSerializeBeanWithPropertiesNotOrdered( ObjectWriterTester<BeanWithPropertiesNotOrdered> writer ) {
        BeanWithPropertiesNotOrdered bean = new BeanWithPropertiesNotOrdered();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true," +
            "\"doubleProperty\":45.7}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testSerializeBeanWithDefinedOrder( ObjectWriterTester<BeanWithDefinedOrder> writer ) {
        BeanWithDefinedOrder bean = new BeanWithDefinedOrder();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"doubleProperty\":45.7," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true," +
            "\"intProperty\":15}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testSerializeBeanWithSomeDefinedOrder( ObjectWriterTester<BeanWithSomeDefinedOrder> writer ) {
        BeanWithSomeDefinedOrder bean = new BeanWithSomeDefinedOrder();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"doubleProperty\":45.7," +
            "\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testSerializeBeanWithAlphabeticOrder( ObjectWriterTester<BeanWithAlphabeticOrder> writer ) {
        BeanWithAlphabeticOrder bean = new BeanWithAlphabeticOrder();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"aBooleanProperty\":true," +
            "\"doubleProperty\":45.7," +
            "\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testSerializeBeanWithSomeDefinedAndRestAlphabeticOrder( ObjectWriterTester<BeanWithSomeDefinedAndRestAlphabeticOrder>
                                                                            writer ) {
        BeanWithSomeDefinedAndRestAlphabeticOrder bean = new BeanWithSomeDefinedAndRestAlphabeticOrder();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String expected = "{\"doubleProperty\":45.7," +
            "\"aBooleanProperty\":true," +
            "\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeBeanWithMissingRequiredProperties( ObjectReaderTester<BeanWithPropertiesNotOrdered> reader ) {
        BeanWithPropertiesNotOrdered bean = new BeanWithPropertiesNotOrdered();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.doubleProperty = 45.7d;

        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true}";

        try {
            reader.read( input );
            fail( "Expected an exception because a required property is missing" );
        } catch ( JsonDeserializationException e ) {
        }
    }

}
