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

package com.github.nmorel.gwtjackson.client.options;

import java.util.LinkedHashMap;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.options.OrderMapEntriesByKeysOptionTester;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class OrderMapEntriesByKeysOptionGwtTest extends GwtJacksonTestCase {

    public interface LinkedHashMapStringIntegerWriter extends ObjectWriter<LinkedHashMap<String, Integer>> {

        static LinkedHashMapStringIntegerWriter INSTANCE = GWT.create( LinkedHashMapStringIntegerWriter.class );
    }

    public void testWriteUnordered() {
        OrderMapEntriesByKeysOptionTester.INSTANCE.testWriteUnordered( createWriter( LinkedHashMapStringIntegerWriter.INSTANCE ) );
    }

    public void testWriteOrdered() {
        OrderMapEntriesByKeysOptionTester.INSTANCE
            .testWriteOrdered( createWriter( LinkedHashMapStringIntegerWriter.INSTANCE, new JsonSerializationContext.Builder()
                .orderMapEntriesByKeys( true ).build() ) );
    }
}
