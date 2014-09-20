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

package com.github.nmorel.gwtjackson.client.ser;

import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializerTest.EnumTest;

/**
 * @author Nicolas Morel
 */
public class EnumJsonSerializerTest extends AbstractJsonSerializerTest<EnumTest> {

    protected static enum EnumTest {
        ONE, TWO, THREE, FOUR
    }

    @Override
    protected JsonSerializer<EnumTest> createSerializer() {
        return EnumJsonSerializer.<EnumJsonSerializer<EnumTest>>getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "\"ONE\"", EnumTest.ONE );
        assertSerialization( "\"TWO\"", EnumTest.TWO );
        assertSerialization( "\"THREE\"", EnumTest.THREE );
        assertSerialization( "\"FOUR\"", EnumTest.FOUR );
    }
}
