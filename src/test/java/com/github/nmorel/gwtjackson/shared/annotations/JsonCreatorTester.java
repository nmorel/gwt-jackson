package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/** @author Nicolas Morel */
public final class JsonCreatorTester extends AbstractTester
{
    public static class BeanWithDefaultConstructorPrivate
    {
        public int intProperty;
        public String stringProperty;
        public Boolean booleanProperty;

        private BeanWithDefaultConstructorPrivate()
        {
        }
    }

    public static class BeanWithoutDefaultConstructorAndNoAnnotation
    {
        private int intProperty;
        private String stringProperty;
        private Boolean booleanProperty;

        public BeanWithoutDefaultConstructorAndNoAnnotation( int intProperty, String stringProperty )
        {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty()
        {
            return intProperty;
        }

        public String getStringProperty()
        {
            return stringProperty;
        }

        public Boolean getBooleanProperty()
        {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty )
        {
            this.booleanProperty = booleanProperty;
        }
    }

    public static class BeanWithoutDefaultConstructorAndPropertiesAnnotation
    {
        private int intProperty;
        private String stringProperty;
        private Boolean booleanProperty;

        public BeanWithoutDefaultConstructorAndPropertiesAnnotation( @JsonProperty( "intProperty" ) int intProperty,
                                                                     @JsonProperty( "stringProperty" ) String stringProperty )
        {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty()
        {
            return intProperty;
        }

        public String getStringProperty()
        {
            return stringProperty;
        }

        public Boolean getBooleanProperty()
        {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty )
        {
            this.booleanProperty = booleanProperty;
        }
    }

    public static class BeanWithConstructorAnnotated
    {
        private int intProperty;
        private String stringProperty;
        private Boolean booleanProperty;

        @JsonCreator
        public BeanWithConstructorAnnotated( @JsonProperty( "intProperty" ) int intProperty, @JsonProperty( "stringProperty" ) String
            stringProperty )
        {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty()
        {
            return intProperty;
        }

        public String getStringProperty()
        {
            return stringProperty;
        }

        public Boolean getBooleanProperty()
        {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty )
        {
            this.booleanProperty = booleanProperty;
        }
    }

    @JsonPropertyOrder( alphabetic = true )
    public static class BeanWithFactoryMethod
    {
        @JsonCreator
        static BeanWithFactoryMethod newInstance( @JsonProperty( "stringProperty" ) String stringProperty,
                                                  @JsonProperty( "intProperty" ) int intProperty )
        {
            return new BeanWithFactoryMethod( intProperty, stringProperty );
        }

        private int intProperty;
        private String stringProperty;
        private Boolean booleanProperty;

        private BeanWithFactoryMethod( int intProperty, String stringProperty )
        {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty()
        {
            return intProperty;
        }

        public String getStringProperty()
        {
            return stringProperty;
        }

        public Boolean getBooleanProperty()
        {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty )
        {
            this.booleanProperty = booleanProperty;
        }
    }

    @JsonPropertyOrder( value = {"booleanProperty", "intProperty", "stringProperty"} )
    public static class BeanWithPrivateFactoryMethod
    {
        @JsonCreator
        private static BeanWithPrivateFactoryMethod newInstance( @JsonProperty( "stringProperty" ) String stringProperty,
                                                                 @JsonProperty( "intProperty" ) int intProperty )
        {
            return new BeanWithPrivateFactoryMethod( intProperty, stringProperty );
        }

        private int intProperty;
        private String stringProperty;
        private Boolean booleanProperty;

        private BeanWithPrivateFactoryMethod( int intProperty, String stringProperty )
        {
            this.intProperty = intProperty;
            this.stringProperty = stringProperty;
        }

        public int getIntProperty()
        {
            return intProperty;
        }

        public String getStringProperty()
        {
            return stringProperty;
        }

        public Boolean getBooleanProperty()
        {
            return booleanProperty;
        }

        public void setBooleanProperty( Boolean booleanProperty )
        {
            this.booleanProperty = booleanProperty;
        }
    }

    public static class BeanWithPropertiesOnlyPresentOnConstructor
    {
        private int result;

        @JsonCreator
        public BeanWithPropertiesOnlyPresentOnConstructor( @JsonProperty( "x" ) int x, @JsonProperty( "y" ) int y )
        {
            this.result = x * y;
        }

        public int getResult()
        {
            return result;
        }
    }

    public static final JsonCreatorTester INSTANCE = new JsonCreatorTester();

    private JsonCreatorTester()
    {
    }

    public void testEncodingBeanWithDefaultConstructorPrivate( JsonEncoderTester<BeanWithDefaultConstructorPrivate> encoder )
    {
        BeanWithDefaultConstructorPrivate bean = new BeanWithDefaultConstructorPrivate();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecodingBeanWithDefaultConstructorPrivate( JsonDecoderTester<BeanWithDefaultConstructorPrivate> decoder )
    {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithDefaultConstructorPrivate result = decoder.decode( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testEncodingBeanWithoutDefaultConstructorAndNoAnnotation( JsonEncoderTester<BeanWithoutDefaultConstructorAndNoAnnotation>
                                                                              encoder )
    {
        BeanWithoutDefaultConstructorAndNoAnnotation bean = new BeanWithoutDefaultConstructorAndNoAnnotation( 15, "IAmAString" );
        bean.booleanProperty = true;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecodingBeanWithoutDefaultConstructorAndNoAnnotation( JsonDecoderTester<BeanWithoutDefaultConstructorAndNoAnnotation>
                                                                              decoder )
    {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        try
        {
            decoder.decode( input );
            fail();
        }
        catch ( JsonDecodingException e )
        {
            // no way to instantiate it
        }
    }

    public void testEncodingBeanWithoutDefaultConstructorAndPropertiesAnnotation(
        JsonEncoderTester<BeanWithoutDefaultConstructorAndPropertiesAnnotation> encoder )
    {
        BeanWithoutDefaultConstructorAndPropertiesAnnotation bean = new BeanWithoutDefaultConstructorAndPropertiesAnnotation( 15,
            "IAmAString" );
        bean.booleanProperty = true;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecodingBeanWithoutDefaultConstructorAndPropertiesAnnotation(
        JsonDecoderTester<BeanWithoutDefaultConstructorAndPropertiesAnnotation> decoder )
    {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithoutDefaultConstructorAndPropertiesAnnotation result = decoder.decode( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testEncodingBeanWithConstructorAnnotated( JsonEncoderTester<BeanWithConstructorAnnotated> encoder )
    {
        BeanWithConstructorAnnotated bean = new BeanWithConstructorAnnotated( 15, "IAmAString" );
        bean.booleanProperty = true;

        String expected = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecodingBeanWithConstructorAnnotated( JsonDecoderTester<BeanWithConstructorAnnotated> decoder )
    {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithConstructorAnnotated result = decoder.decode( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testEncodingBeanWithFactoryMethod( JsonEncoderTester<BeanWithFactoryMethod> encoder )
    {
        BeanWithFactoryMethod bean = new BeanWithFactoryMethod( 15, "IAmAString" );
        bean.booleanProperty = true;

        // There is an alphabetic order asked but the order of properties in factory method is stronger

        String expected = "{\"stringProperty\":\"IAmAString\"," +
            "\"intProperty\":15," +
            "\"booleanProperty\":true}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecodingBeanWithFactoryMethod( JsonDecoderTester<BeanWithFactoryMethod> decoder )
    {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithFactoryMethod result = decoder.decode( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testEncodingBeanWithPrivateFactoryMethod( JsonEncoderTester<BeanWithPrivateFactoryMethod> encoder )
    {
        BeanWithPrivateFactoryMethod bean = new BeanWithPrivateFactoryMethod( 15, "IAmAString" );
        bean.booleanProperty = true;

        // There is an explicit order on type. The order of parameters of factory method is overrided.

        String expected = "{\"booleanProperty\":true," +
            "\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecodingBeanWithPrivateFactoryMethod( JsonDecoderTester<BeanWithPrivateFactoryMethod> decoder )
    {
        String input = "{\"intProperty\":15," +
            "\"stringProperty\":\"IAmAString\"," +
            "\"booleanProperty\":true}";

        BeanWithPrivateFactoryMethod result = decoder.decode( input );

        assertEquals( 15, result.intProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertTrue( result.booleanProperty );
    }

    public void testEncodingBeanWithPropertiesOnlyPresentOnConstructor( JsonEncoderTester<BeanWithPropertiesOnlyPresentOnConstructor>
                                                                            encoder )
    {
        BeanWithPropertiesOnlyPresentOnConstructor bean = new BeanWithPropertiesOnlyPresentOnConstructor( 15, 10 );

        String expected = "{\"result\":150}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecodingBeanWithPropertiesOnlyPresentOnConstructor( JsonDecoderTester<BeanWithPropertiesOnlyPresentOnConstructor> decoder )
    {
        String input = "{\"x\":15,\"y\":10}";

        BeanWithPropertiesOnlyPresentOnConstructor result = decoder.decode( input );

        assertEquals( 150, result.result );
    }

}
