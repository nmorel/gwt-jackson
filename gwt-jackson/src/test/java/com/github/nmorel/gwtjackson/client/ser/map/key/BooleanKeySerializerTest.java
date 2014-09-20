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

package com.github.nmorel.gwtjackson.client.ser.map.key;

/**
 * @author Nicolas Morel
 */
public class BooleanKeySerializerTest extends AbstractKeySerializerTest<Boolean> {

    @Override
    protected ToStringKeySerializer createSerializer() {
        return ToStringKeySerializer.getInstance();
    }

    public void testSerializeValue() {
        assertSerialization( "true", true );
        assertSerialization( "true", Boolean.TRUE );
        assertSerialization( "false", false );
        assertSerialization( "false", Boolean.FALSE );
    }
}
