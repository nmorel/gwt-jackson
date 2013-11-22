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

package com.github.nmorel.gwtjackson.shared.options;

import java.util.LinkedHashMap;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class OrderMapEntriesByKeysOptionTester extends AbstractTester {

    public static final OrderMapEntriesByKeysOptionTester INSTANCE = new OrderMapEntriesByKeysOptionTester();

    private OrderMapEntriesByKeysOptionTester() {
    }

    public void testWriteUnordered( ObjectWriterTester<LinkedHashMap<String, Integer>> writer ) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put( "b", 3 );
        map.put( "a", 6 );
        assertEquals( "{\"b\":3,\"a\":6}", writer.write( map ) );
    }

    public void testWriteOrdered( ObjectWriterTester<LinkedHashMap<String, Integer>> writer ) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put( "b", 3 );
        map.put( "a", 6 );
        assertEquals( "{\"a\":6,\"b\":3}", writer.write( map ) );
    }

}
