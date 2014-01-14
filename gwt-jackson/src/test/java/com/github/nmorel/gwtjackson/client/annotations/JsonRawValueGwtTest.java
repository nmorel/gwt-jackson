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
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRawValueTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRawValueTester.ClassGetter;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRawValueTester.ClassWithJsonAsString;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonRawValueGwtTest extends GwtJacksonTestCase {

    public interface ClassGetterString extends ObjectWriter<ClassGetter<String>>, ObjectWriterTester<ClassGetter<String>> {

        static ClassGetterString INSTANCE = GWT.create( ClassGetterString.class );
    }

    public interface ClassGetterInteger extends ObjectWriter<ClassGetter<Integer>>, ObjectWriterTester<ClassGetter<Integer>> {

        static ClassGetterInteger INSTANCE = GWT.create( ClassGetterInteger.class );
    }

    public interface ClassWithJsonAsStringMapper extends ObjectMapper<ClassWithJsonAsString>, ObjectMapperTester<ClassWithJsonAsString> {

        static ClassWithJsonAsStringMapper INSTANCE = GWT.create( ClassWithJsonAsStringMapper.class );
    }

    private JsonRawValueTester tester = JsonRawValueTester.INSTANCE;

    public void testSimpleStringGetter() {
        tester.testSimpleStringGetter( ClassGetterString.INSTANCE );
    }

    public void testSimpleNonStringGetter() {
        tester.testSimpleNonStringGetter( ClassGetterInteger.INSTANCE );
    }

    public void testNullStringGetter() {
        tester.testNullStringGetter( ClassGetterString.INSTANCE );
    }

    public void testJsonString() {
        tester.testJsonString( ClassWithJsonAsStringMapper.INSTANCE );
    }
}
