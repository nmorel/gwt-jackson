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

import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.DoubleKeyDeserializer;

/**
 * @author Nicolas Morel
 */
public class DoubleKeyDeserializerTest extends AbstractKeyDeserializerTest<Double> {

    @Override
    protected DoubleKeyDeserializer createDeserializer() {
        return DoubleKeyDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( 34.100247d, "34.100247" );
        assertDeserialization( -487.15487d, "-487.15487" );
        assertDeserialization( -784.15454d, "-784.15454" );
        assertDeserialization( Double.MIN_VALUE, "4.9E-324" );
        assertDeserialization( Double.MAX_VALUE, "1.7976931348623157e+308" );
        // We cannot use assertDeserialization for \"NaN\" for a simple reason: `Double.NaN` is not equal to `Double.NaN` see following test
        assertNotSame( Double.NaN, Double.NaN );
        // So use Double#isNaN
        assertTrue( Double.isNaN( deserialize( "NaN" ) ) );
        assertDeserialization( Double.NEGATIVE_INFINITY, "-Infinity" );
        assertDeserialization( Double.POSITIVE_INFINITY, "Infinity" );
    }
}
