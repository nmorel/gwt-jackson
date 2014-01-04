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

package com.github.nmorel.gwtjackson.shared.advanced;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class PrivateAccessTester extends AbstractTester {

    @JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonPropertyOrder(alphabetic = true)
    public static class PrivateBean {

        @JsonProperty(value = "private")
        private String privateField;

        private boolean usedPrivateGetter;

        private boolean usedPrivateSetter;

        private PrivateBean privateAccessors;

        private PrivateBean getPrivateAccessors() {
            usedPrivateGetter = true;
            return privateAccessors;
        }

        private void setPrivateAccessors( PrivateBean privateAccessors ) {
            usedPrivateSetter = true;
            this.privateAccessors = privateAccessors;
        }
    }

    public static final PrivateAccessTester INSTANCE = new PrivateAccessTester();

    private PrivateAccessTester() {
    }

    public void testSerializePrivateField( ObjectWriterTester<PrivateBean> writer ) {
        PrivateBean bean = new PrivateBean();
        bean.privateField = "IAmAPrivateField";
        PrivateBean internalBean = new PrivateBean();
        internalBean.privateField = "IHavePrivateAccessors";
        bean.privateAccessors = internalBean;

        String expected = "{\"private\":\"IAmAPrivateField\",\"privateAccessors\":{\"private\":\"IHavePrivateAccessors\"," +
                "" + "\"privateAccessors\":null}}";
        String result = writer.write( bean );

        assertTrue( bean.usedPrivateGetter );
        assertFalse( bean.usedPrivateSetter );
        assertEquals( expected, result );
    }

    public void testDeserializePrivateField( ObjectReaderTester<PrivateBean> reader ) {
        String input = "{\"private\":\"IAmAPrivateField\",\"privateAccessors\":{\"private\":\"IHavePrivateAccessors\"}}";

        PrivateBean result = reader.read( input );

        assertTrue( result.usedPrivateSetter );
        assertFalse( result.usedPrivateGetter );
        assertEquals( "IAmAPrivateField", result.privateField );
        assertEquals( "IHavePrivateAccessors", result.privateAccessors.privateField );
    }

}
