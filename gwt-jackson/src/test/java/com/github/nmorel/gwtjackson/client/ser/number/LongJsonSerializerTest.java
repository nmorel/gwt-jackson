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

package com.github.nmorel.gwtjackson.client.ser.number;

import com.github.nmorel.gwtjackson.client.ser.AbstractJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.LongJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class LongJsonSerializerTest extends AbstractJsonSerializerTest<Long> {

    @Override
    protected LongJsonSerializer createSerializer() {
        return LongJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "3441764551145441542", 3441764551145441542l );
        assertSerialization( "-3441764551145441542", -3441764551145441542l );
        assertSerialization( "-9223372036854775808", Long.MIN_VALUE );
        assertSerialization( "9223372036854775807", Long.MAX_VALUE );
    }
}
