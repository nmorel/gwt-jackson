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

package com.github.nmorel.gwtjackson.client.ser.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class SetJsonSerializerTest extends AbstractJsonSerializerTest<Set<String>> {

    @Override
    protected JsonSerializer<Set<String>> createSerializer() {
        return (JsonSerializer) IterableJsonSerializer.newInstance( StringJsonSerializer.getInstance() );
    }

    public void testSerializeValue() {
        // can't predict the order so we just serialize one element
        assertSerialization( "[\"Hello\"]", new HashSet<String>( Arrays.asList( "Hello", "Hello" ) ) );
        assertSerialization( "[]", Collections.<String>emptySet() );
    }

}
