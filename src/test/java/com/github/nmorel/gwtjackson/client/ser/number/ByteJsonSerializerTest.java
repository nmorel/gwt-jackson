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
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.ByteJsonSerializer;

/**
 * @author Nicolas Morel
 */
public class ByteJsonSerializerTest extends AbstractJsonSerializerTest<Byte> {

    @Override
    protected ByteJsonSerializer createSerializer() {
        return ByteJsonSerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "34", (byte) 34 );
        assertSerialization( "1", new Byte( "1" ) );
        assertSerialization( "-128", Byte.MIN_VALUE );
        assertSerialization( "127", Byte.MAX_VALUE );
    }
}
