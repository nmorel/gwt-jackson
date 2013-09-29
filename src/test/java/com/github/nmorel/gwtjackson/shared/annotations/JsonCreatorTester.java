package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class JsonCreatorTester extends AbstractTester {

    public static class BeanWithDefaultConstructorPrivate {

        public int intProperty;

        public String stringProperty;

        public Boolean booleanProperty;

        private BeanWithDefaultConstructorPrivate() {
        }
    }

    public static class BeanWithoutDefaultConstructorAndNoAnnotation {

        private int intProperty;

        private String stringProperty;

        private Boolean booleanProperty;

        public BeanWithoutDefaultConstructorAndNoAnnotation( int intProperty, String stringProperty ) {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public String getStringProperty() {
            return stringProperty;
        }

        public Boolean getBooleanProperty() {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty ) {
            this.booleanProperty = booleanProperty;
        }
    }

    public static class BeanWithoutDefaultConstructorAndPropertiesAnnotation {

        private int intProperty;

        private String stringProperty;

        private Boolean booleanProperty;

        public BeanWithoutDefaultConstructorAndPropertiesAnnotation( @JsonProperty("intProperty") int intProperty,
                                                                     @JsonProperty(value = "stringProperty",
            required = true) String stringProperty ) {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public String getStringProperty() {
            return stringProperty;
        }

        public Boolean getBooleanProperty() {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty ) {
            this.booleanProperty = booleanProperty;
        }
    }

    public static class BeanWithConstructorAnnotated {

        private int intProperty;

        private String stringProperty;

        private Boolean booleanProperty;

        @JsonCreator
        public BeanWithConstructorAnnotated( @JsonProperty("intProperty") int intProperty, @JsonProperty("stringProperty") String
            stringProperty ) {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public String getStringProperty() {
            return stringProperty;
        }

        public Boolean getBooleanProperty() {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty ) {
            this.booleanProperty = booleanProperty;
        }
    }

    @JsonPropertyOrder(alphabetic = true)
    public static class BeanWithFactoryMethod {

        @JsonCreator
        static BeanWithFactoryMethod newInstance( @JsonProperty("stringProperty") String stringProperty,
                                                  @JsonProperty("intProperty") int intProperty ) {
            return new BeanWithFactoryMethod( intProperty, stringProperty );
        }

        private int intProperty;

        private String stringProperty;

        private Boolean booleanProperty;

        private BeanWithFactoryMethod( int intProperty, String stringProperty ) {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public String getStringProperty() {
            return stringProperty;
        }

        public Boolean getBooleanProperty() {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty ) {
            this.booleanProperty = booleanProperty;
        }
    }

    @JsonPropertyOrder(value = {"booleanProperty", "intProperty", "stringProperty"})
    public static class BeanWithPrivateFactoryMethod {

        @JsonCreator
        private static BeanWithPrivateFactoryMethod newInstance( @JsonProperty("stringProperty") String stringProperty,
                                                                 @JsonProperty("intProperty") int intProperty ) {
            return new BeanWithPrivateFactoryMethod( intProperty, stringProperty );
        }

        private int intProperty;

        private String stringProperty;

        private Boolean booleanProperty;

        private BeanWithPrivateFactoryMethod( int intProperty, String stringProperty ) {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public String getStringProperty() {
            return stringProperty;
        }

        public Boolean getBooleanProperty() {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty ) {
            this.booleanProperty = booleanProperty;
        }
    }

    public static class BeanWithPropertiesOnlyPresentOnConstructor {

        private int result;

        @JsonCreator
        public BeanWithPropertiesOnlyPresentOnConstructor( @JsonProperty("x") int x, @JsonProperty("y") int y ) {
            this.result = x * y;
        }

        public int getResult() {
            return result;
        }
    }

    public static final JsonCreatorTester INSTANCE = new JsonCreatorTester();

    private JsonCreatorTester() {
    }

    public void testSerializeBeanWithDefaultConstructorPrivate( ObjectWriterTester<BeanWithDefaultConstructorPrivate> writer ) {
        BeanWithDefaultConstructorPrivate bean = new BeanWithDefaultConstructorPrivate();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeBeanWithDefaultConstructorPrivate( ObjectReaderTester<BeanWithDefaultConstructorPrivate> reader ) {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithDefaultConstructorPrivate result = reader.read( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testSerializeBeanWithoutDefaultConstructorAndNoAnnotation(
        ObjectWriterTester<BeanWithoutDefaultConstructorAndNoAnnotation> writer ) {
        BeanWithoutDefaultConstructorAndNoAnnotation bean = new BeanWithoutDefaultConstructorAndNoAnnotation( 15, "IAmAString" );
        bean.booleanProperty = true;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeBeanWithoutDefaultConstructorAndNoAnnotation(
        ObjectReaderTester<BeanWithoutDefaultConstructorAndNoAnnotation> reader ) {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        try {
            reader.read( input );
            fail();
        } catch ( JsonDeserializationException e ) {
            // no way to instantiate it
        }
    }

    public void testSerializeBeanWithoutDefaultConstructorAndPropertiesAnnotation(
        ObjectWriterTester<BeanWithoutDefaultConstructorAndPropertiesAnnotation> writer ) {
        BeanWithoutDefaultConstructorAndPropertiesAnnotation bean = new BeanWithoutDefaultConstructorAndPropertiesAnnotation( 15,
            "IAmAString" );
        bean.booleanProperty = true;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeBeanWithoutDefaultConstructorAndPropertiesAnnotation(
        ObjectReaderTester<BeanWithoutDefaultConstructorAndPropertiesAnnotation> reader ) {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithoutDefaultConstructorAndPropertiesAnnotation result = reader.read( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testDeserializeBeanWithMissingRequiredPropertyInCreator(
        ObjectReaderTester<BeanWithoutDefaultConstructorAndPropertiesAnnotation> reader ) {
        String input = "{\"intProperty\":15,\"booleanProperty\":true}";

        try {
            reader.read( input );
            fail( "Expected an exception because a required property is missing" );
        } catch ( JsonDeserializationException e ) {
        }
    }

    public void testSerializeBeanWithConstructorAnnotated( ObjectWriterTester<BeanWithConstructorAnnotated> writer ) {
        BeanWithConstructorAnnotated bean = new BeanWithConstructorAnnotated( 15, "IAmAString" );
        bean.booleanProperty = true;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeBeanWithConstructorAnnotated( ObjectReaderTester<BeanWithConstructorAnnotated> reader ) {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithConstructorAnnotated result = reader.read( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testSerializeBeanWithFactoryMethod( ObjectWriterTester<BeanWithFactoryMethod> writer ) {
        BeanWithFactoryMethod bean = new BeanWithFactoryMethod( 15, "IAmAString" );
        bean.booleanProperty = true;

        // There is an alphabetic order asked but the order of properties in factory method is stronger

        String expected = "{\"stringProperty\":\"IAmAString\"," +
            "\"intProperty\":15," +
            "\"booleanProperty\":true}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeBeanWithFactoryMethod( ObjectReaderTester<BeanWithFactoryMethod> reader ) {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithFactoryMethod result = reader.read( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testSerializeBeanWithPrivateFactoryMethod( ObjectWriterTester<BeanWithPrivateFactoryMethod> writer ) {
        BeanWithPrivateFactoryMethod bean = new BeanWithPrivateFactoryMethod( 15, "IAmAString" );
        bean.booleanProperty = true;

        // There is an explicit order on type. The order of parameters of factory method is overrided.

        String expected = "{\"booleanProperty\":true," +
            "\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeBeanWithPrivateFactoryMethod( ObjectReaderTester<BeanWithPrivateFactoryMethod> reader ) {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithPrivateFactoryMethod result = reader.read( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testSerializeBeanWithPropertiesOnlyPresentOnConstructor( ObjectWriterTester<BeanWithPropertiesOnlyPresentOnConstructor>
                                                                             writer ) {
        BeanWithPropertiesOnlyPresentOnConstructor bean = new BeanWithPropertiesOnlyPresentOnConstructor( 15, 10 );

        String expected = "{\"result\":150}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeBeanWithPropertiesOnlyPresentOnConstructor( ObjectReaderTester<BeanWithPropertiesOnlyPresentOnConstructor> reader ) {
        String input = "{\"x\":15,\"y\":10}";

        BeanWithPropertiesOnlyPresentOnConstructor result = reader.read( input );

        assertEquals( 150, result.result );
    }

}
