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

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.OrderMapEntriesByKeysOptionTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class OrderMapEntriesByKeysOptionJacksonTest extends AbstractJacksonTest {

    @Test
    public void testWriteUnordered() {
        OrderMapEntriesByKeysOptionTester.INSTANCE
                .testWriteUnordered( createWriter( new TypeReference<LinkedHashMap<String, Integer>>() {} ) );
    }

    @Test
    public void testWriteOrdered() {
        objectMapper.configure( SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true );
        OrderMapEntriesByKeysOptionTester.INSTANCE
                .testWriteOrdered( createWriter( new TypeReference<LinkedHashMap<String, Integer>>() {} ) );
    }
}
