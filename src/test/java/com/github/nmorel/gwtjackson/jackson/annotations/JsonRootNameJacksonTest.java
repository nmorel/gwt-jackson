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

package com.github.nmorel.gwtjackson.jackson.annotations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.Bean;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.RootBeanWithEmpty;
import com.github.nmorel.gwtjackson.shared.annotations.JsonRootNameTester.RootBeanWithNoAnnotation;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonRootNameJacksonTest extends AbstractJacksonTest {

    @Override
    public void setUp() {
        super.setUp();
        objectMapper.configure( SerializationFeature.WRAP_ROOT_VALUE, true );
        objectMapper.configure( DeserializationFeature.UNWRAP_ROOT_VALUE, true );
    }

    @Test
    public void testRootName() {
        JsonRootNameTester.INSTANCE.testRootName( createMapper( Bean.class ) );
    }

    @Test
    public void testRootNameEmpty() {
        JsonRootNameTester.INSTANCE.testRootNameEmpty( createMapper( RootBeanWithEmpty.class ) );
    }

    @Test
    public void testRootNameNoAnnotation() {
        JsonRootNameTester.INSTANCE.testRootNameNoAnnotation( createMapper( RootBeanWithNoAnnotation.class ) );
    }

    @Test
    public void testUnwrappingFailing() {
        JsonRootNameTester.INSTANCE.testUnwrappingFailing( createReader( Bean.class ) );
    }
}
