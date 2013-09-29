package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class JsonIgnoreTester extends AbstractTester {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonIgnoreProperties({"stringProperty", "aBooleanProperty", "unknownProperty"})
    public static class BeanWithIgnoredProperties {

        @JsonIgnore(false)
        protected int intProperty;

        @JsonProperty("aStringProperty")
        protected String stringProperty;

        @JsonProperty("aBooleanProperty")
        protected Boolean booleanProperty;

        @JsonIgnore
        protected Double ignoredProperty;
    }

    public static final JsonIgnoreTester INSTANCE = new JsonIgnoreTester();

    private JsonIgnoreTester() {
    }

    public void testSerialize( ObjectWriterTester<BeanWithIgnoredProperties> writer ) {
        BeanWithIgnoredProperties bean = new BeanWithIgnoredProperties();
        bean.intProperty = 15;
        bean.stringProperty = "IAmAString";
        bean.booleanProperty = true;
        bean.ignoredProperty = 45.7d;

        String expected = "{\"intProperty\":15," + "\"aStringProperty\":\"IAmAString\"}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserialize( ObjectReaderTester<BeanWithIgnoredProperties> reader ) {
        String input = "{\"ignoredProperty\":45.7," +
            "\"aStringProperty\":\"IAmAString\"," +
            "\"aBooleanProperty\":true," +
            "\"intProperty\":15}";

        BeanWithIgnoredProperties result = reader.read( input );

        assertNull( result.booleanProperty );
        assertNull( result.ignoredProperty );
        assertEquals( "IAmAString", result.stringProperty );
        assertEquals( 15, result.intProperty );
    }

}
