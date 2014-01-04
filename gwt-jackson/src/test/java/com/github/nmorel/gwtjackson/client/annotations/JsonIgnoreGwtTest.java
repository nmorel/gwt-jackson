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

package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester.BeanWithIgnorePropertiesAsProperty;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester.BeanWithIgnoredProperties;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonIgnoreGwtTest extends GwtJacksonTestCase {

    public interface BeanWithIgnoredPropertiesMapper extends ObjectMapper<BeanWithIgnoredProperties>,
            ObjectMapperTester<BeanWithIgnoredProperties> {

        static BeanWithIgnoredPropertiesMapper INSTANCE = GWT.create( BeanWithIgnoredPropertiesMapper.class );
    }

    public interface BeanWithUnknownPropertyMapper extends ObjectReader<JsonIgnoreTester.BeanWithUnknownProperty>,
            ObjectReaderTester<JsonIgnoreTester.BeanWithUnknownProperty> {

        static BeanWithUnknownPropertyMapper INSTANCE = GWT.create( BeanWithUnknownPropertyMapper.class );
    }

    public interface BeanWithIgnorePropertiesAsPropertyWriter extends ObjectWriter<BeanWithIgnorePropertiesAsProperty>,
            ObjectWriterTester<BeanWithIgnorePropertiesAsProperty> {

        static BeanWithIgnorePropertiesAsPropertyWriter INSTANCE = GWT.create( BeanWithIgnorePropertiesAsPropertyWriter.class );
    }

    public interface BeanWithIgnorePropertiesAsPropertyReader extends ObjectReader<JsonIgnoreTester.BeanWithIgnorePropertiesAsProperty>,
            ObjectReaderTester<BeanWithIgnorePropertiesAsProperty> {

        static BeanWithIgnorePropertiesAsPropertyReader INSTANCE = GWT.create( BeanWithIgnorePropertiesAsPropertyReader.class );
    }

    private JsonIgnoreTester tester = JsonIgnoreTester.INSTANCE;

    public void testSerializeBeanWithIgnoredProperties() {
        tester.testSerializeBeanWithIgnoredProperties( BeanWithIgnoredPropertiesMapper.INSTANCE );
    }

    public void testDeserializeBeanWithIgnoredProperties() {
        tester.testDeserializeBeanWithIgnoredProperties( BeanWithIgnoredPropertiesMapper.INSTANCE );
    }

    public void testDeserializeBeanWithUnknownProperty() {
        tester.testDeserializeBeanWithUnknownProperty( BeanWithUnknownPropertyMapper.INSTANCE );
    }

    public void testSerializeBeanWithIgnorePropertiesAsProperty() {
        JsonIgnoreTester.INSTANCE.testSerializeBeanWithIgnorePropertiesAsProperty( BeanWithIgnorePropertiesAsPropertyWriter.INSTANCE );
    }

    public void testDeserializeBeanWithIgnorePropertiesAsProperty() {
        JsonIgnoreTester.INSTANCE.testDeserializeBeanWithIgnorePropertiesAsProperty( BeanWithIgnorePropertiesAsPropertyReader.INSTANCE );
    }
}
