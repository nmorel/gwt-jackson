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

import java.util.HashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class WriteNullMapValuesOptionTester extends AbstractTester {

    public static final WriteNullMapValuesOptionTester INSTANCE = new WriteNullMapValuesOptionTester();

    private WriteNullMapValuesOptionTester() {
    }

    public void testWriteNullValues( ObjectWriterTester<Map<String, String>> writer ) {
        Map<String, String> map = new HashMap<String, String>();
        map.put( "a", null );
        assertEquals( "{\"a\":null}", writer.write( map ) );
    }

    public void testWriteNonNullValues( ObjectWriterTester<Map<String, String>> writer ) {
        Map<String, String> map = new HashMap<String, String>();
        map.put( "a", null );
        assertEquals( "{}", writer.write( map ) );
    }

}
