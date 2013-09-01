package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/** @author Nicolas Morel */
public final class JsonIgnoreTester extends AbstractTester
{
    @JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.ANY )
    @JsonIgnoreProperties( {"stringProperty", "aBooleanProperty", "unknownProperty"} )
    public static class BeanWithIgnoredProperties
    {
        @JsonIgnore( false )
        protected int intProperty;
        @JsonProperty( "aStringProperty" )
        protected String stringProperty;
        @JsonProperty( "aBooleanProperty" )
        protected Boolean booleanProperty;
        @JsonIgnore
        protected Double ignoredProperty;
    }

    public static final JsonIgnoreTester INSTANCE = new JsonIgnoreTester();

    private JsonIgnoreTester()
    {
    }

    public void testEncoding( JsonEncoderTester<BeanWithIgnoredProperties> encoder )
    {
        BeanWithIgnoredProperties bean = new BeanWithIgnoredProperties();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.ignoredProperty = 45.7d;

        String expected = "{\"intProperty\":15," + "\"aStringProperty\":\"IAmAString\"}";
        String result = encoder.encode( bean );

        assertEquals( expected, result );
    }

    public void testDecoding( JsonDecoderTester<BeanWithIgnoredProperties> decoder )
    {
        String input = "{\"ignoredProperty\":45.7," +
            "\"aStringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true," +
            "\"intProperty\":15}";

        BeanWithIgnoredProperties result = decoder.decode( input );

        assertNull( result.booleanProperty );
        assertNull( result.ignoredProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertEquals( 15, result.intProperty );
    }

}
