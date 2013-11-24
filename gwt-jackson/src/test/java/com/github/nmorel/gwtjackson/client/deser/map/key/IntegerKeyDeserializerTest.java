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

package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.IntegerKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class IntegerKeyDeserializerTest extends AbstractKeyDeserializerTest<Integer> {

    @Override
    protected IntegerKeyDeserializer createDeserializer() {
        return IntegerKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 34, "34" );
        assertDeserialization( -1, "-1" );
        assertDeserialization( Integer.MIN_VALUE, "-2147483648" );
        assertDeserialization( Integer.MAX_VALUE, "2147483647" );
    }
}
