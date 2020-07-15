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

package com.github.nmorel.gwtjackson.client.deser.number;

import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.FloatJsonDeserializer;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class FloatJsonDeserializerTest extends AbstractJsonDeserializerTest<Float> {

    @Override
    protected FloatJsonDeserializer createDeserializer() {
        return FloatJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new Float( "34.10245" ), "34.10245" );
        assertDeserialization( new Float( "-784.15454" ), "\"-784.15454\"" );
        // the float emulation gives slightly different results => use BigDecimal for precision!
        if ( !GWT.isProdMode() ) {
            assertDeserialization( Float.MIN_VALUE, "1.4e-45" );
            assertDeserialization( Float.MAX_VALUE, "3.4028235e38" );
        }
        // We cannot use assertDeserialization for \"NaN\" for a simple reason: `Float.NaN` is not equal to `Float.NaN` see following test
        assertNotSame( Float.NaN, Float.NaN );
        // So use Double#isNaN
        assertTrue( Float.isNaN( deserialize( "\"NaN\"" ) ) );
        assertDeserialization( Float.NEGATIVE_INFINITY, "\"-Infinity\"" );
        assertDeserialization( Float.POSITIVE_INFINITY, "\"Infinity\"" );
    }
}
