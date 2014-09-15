/*
 * Copyright 2014 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.deser.array;

import java.util.Arrays;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.AbstractJsonDeserializerTest;

/**
 * Test byte array deserialization.
 */
public class ByteArrayJsonDeserializerTest extends AbstractJsonDeserializerTest<byte[]> {

    @Override
    protected JsonDeserializer<byte[]> createDeserializer() {
        return PrimitiveByteArrayJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new byte[]{0, 11, 22, 33}, "\"AAsWIQ==\"" );
        assertDeserialization( new byte[]{0, -11, -22, -33}, "\"APXq3w==\"" );
        assertDeserialization( new byte[]{0, 100, -100, 0}, "\"AGScAA==\"" );
        assertDeserialization( new byte[0], "\"\"" );
    }

    protected void assertDeserialization( byte[] expected, String value ) {
        assertEquals( expected, deserialize( value ) );
    }

    // GwtTestCase has not assert method for arrays
    private void assertEquals( byte[] expected, byte[] deserialized ) {
        if ( !Arrays.equals( expected, deserialized ) ) {
            fail( "expected: " + format( expected ) + ", actual: " + format( deserialized ) );
        }
    }

    private static String format( byte[] value ) {
        if ( value == null ) {
            return "null";
        }

        StringBuilder builder = new StringBuilder( "<" );
        String sep = "";
        for ( byte b : value ) {
            builder.append( sep ).append( b );
            sep = ",";
        }
        builder.append( ">" );

        return builder.toString();
    }

}
