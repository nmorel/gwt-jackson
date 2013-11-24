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

package com.github.nmorel.gwtjackson.jackson.options;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.WriteNullMapValuesOptionTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class WriteNullMapValuesOptionJacksonTest extends AbstractJacksonTest {

    @Test
    public void testWriteNullValues() {
        WriteNullMapValuesOptionTester.INSTANCE.testWriteNullValues( createWriter( new TypeReference<Map<String, String>>() {} ) );
    }

    @Test
    public void testWriteNonNullValues() {
        objectMapper.configure( SerializationFeature.WRITE_NULL_MAP_VALUES, false );
        WriteNullMapValuesOptionTester.INSTANCE.testWriteNonNullValues( createWriter( new TypeReference<Map<String, String>>() {} ) );
    }
}
