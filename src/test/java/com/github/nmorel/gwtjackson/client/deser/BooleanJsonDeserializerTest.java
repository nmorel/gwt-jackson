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

package com.github.nmorel.gwtjackson.client.deser;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * @author Nicolas Morel
 */
public class BooleanJsonDeserializerTest extends AbstractJsonDeserializerTest<Boolean> {

    @Override
    protected JsonDeserializer<Boolean> createDeserializer() {
        return BooleanJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertTrue( deserialize( "true" ) );
        assertTrue( deserialize( "\"trUe\"" ) );
        assertTrue( deserialize( "1" ) );

        assertFalse( deserialize( "faLse" ) );
        assertFalse( deserialize( "\"false\"" ) );
        assertFalse( deserialize( "0" ) );
        assertFalse( deserialize( "other" ) );
    }
}
