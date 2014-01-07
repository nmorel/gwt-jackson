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

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext.Builder;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractJsonDeserializerTest<T> extends GwtJacksonTestCase {

    protected abstract JsonDeserializer<T> createDeserializer();

    public void testDeserializeNullValue() {
        assertNull( deserialize( "null" ) );
    }

    protected T deserialize( String value ) {
        JsonDeserializationContext ctx = new Builder().build();
        JsonReader reader = ctx.newJsonReader( value );
        return createDeserializer().deserialize( reader, ctx );
    }

    protected void assertDeserialization( T expected, String value ) {
        assertEquals( expected, deserialize( value ) );
    }

    public abstract void testDeserializeValue();
}
