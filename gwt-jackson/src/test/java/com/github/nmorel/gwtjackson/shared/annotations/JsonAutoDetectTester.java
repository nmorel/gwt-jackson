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
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class JsonAutoDetectTester extends AbstractTester {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, getterVisibility = JsonAutoDetect.Visibility.NONE)
    public static class BeanOne {

        public String publicFieldVisible;

        protected String protectedFieldVisible;

        private Integer notVisibleField;

        private String visibleWithSetter;

        @JsonProperty
        private String fieldWithJsonProperty;

        public String getVisibleWithSetter() {
            return visibleWithSetter;
        }

        public void setVisibleWithSetter( String visibleWithSetter ) {
            this.visibleWithSetter = visibleWithSetter;
        }

        String getFieldWithJsonProperty() {
            return fieldWithJsonProperty;
        }

        void setFieldWithJsonProperty( String fieldWithJsonProperty ) {
            this.fieldWithJsonProperty = fieldWithJsonProperty;
        }
    }

    public static final JsonAutoDetectTester INSTANCE = new JsonAutoDetectTester();

    private JsonAutoDetectTester() {
    }

    public void testSerializeAutoDetection( ObjectWriterTester<BeanOne> writer ) {
        BeanOne bean = new BeanOne();
        bean.notVisibleField = 1;
        bean.setVisibleWithSetter( "Hello" );
        bean.protectedFieldVisible = "protectedField";
        bean.publicFieldVisible = "publicField";
        bean.fieldWithJsonProperty = "jsonProperty";

        String expected = "{\"publicFieldVisible\":\"publicField\"," +
                "\"protectedFieldVisible\":\"protectedField\"," +
                "\"fieldWithJsonProperty\":\"jsonProperty\"}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserializeAutoDetection( ObjectReaderTester<BeanOne> reader ) {
        String input = "{\"visibleWithSetter\":\"Hello\"," +
                "\"publicFieldVisible\":\"publicField\"," +
                "\"protectedFieldVisible\":\"protectedField\"," +
                "\"notVisibleField\":2," +
                "\"fieldWithJsonProperty\":\"jsonProperty\"}";

        BeanOne result = reader.read( input );

        assertNull( result.notVisibleField );
        assertEquals( "Hello", result.visibleWithSetter );
        assertEquals( "protectedField", result.protectedFieldVisible );
        assertEquals( "publicField", result.publicFieldVisible );
        assertEquals( "jsonProperty", result.fieldWithJsonProperty );
    }

}
