/*
 * Copyright 2014 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.stream.impl;

import com.github.nmorel.gwtjackson.client.stream.AbstractJsonReaderTest;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;

/**
 * @author Nicolas Morel
 */
public class NonBufferedJsonReaderTest extends AbstractJsonReaderTest {

    @Override
    public JsonReader newJsonReader( String input ) {
        return new NonBufferedJsonReader( input );
    }

    // Below test only works with NonBufferedJsonReader - would need to update
    // DefaultJsonReader to also support this
    public void testNextValueWithDecimalValue() {
        JsonReader reader = newJsonReader( "[1.343423]" );
        reader.beginArray();
        assertEquals( "1.343423", reader.nextValue() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }
}
