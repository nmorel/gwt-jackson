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

package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.Arrays;
import java.util.Collections;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class IterableJsonDeserializerTest extends AbstractJsonDeserializerTest<Iterable<String>> {

    @Override
    protected JsonDeserializer<Iterable<String>> createDeserializer() {
        return IterableJsonDeserializer.newInstance( StringJsonDeserializer.getInstance() );
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( Arrays.asList( "Hello", " ", "World", "!" ), "[Hello, \" \", \"World\", \"!\"]" );
        assertDeserialization( Collections.<String>emptyList(), "[]" );
    }

}
