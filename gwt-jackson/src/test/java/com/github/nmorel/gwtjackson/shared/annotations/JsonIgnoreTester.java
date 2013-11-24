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
