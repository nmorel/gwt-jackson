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
import com.github.nmorel.gwtjackson.client.deser.array.dd.PrimitiveByteArray2dJsonDeserializer;

/**
 * Test byte array deserialization.
 */
public class ByteArray2dJsonDeserializerTest extends AbstractJsonDeserializerTest<byte[][]> {

    @Override
    protected JsonDeserializer<byte[][]> createDeserializer() {
        return PrimitiveByteArray2dJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization( new byte[][]{{0, 11, 22, 33}, {0, -11, -22, -33}, {0, 100, -100, 0}, {}}, "[\"AAsWIQ==\",\"APXq3w==\"," +
                "" + "\"AGScAA==\",\"\"]" );
    }

    protected void assertDeserialization( byte[][] expected, String value ) {
        assertEquals( expected, deserialize( value ) );
    }

    // GwtTestCase has not assert method for arrays of arrays
    private void assertEquals( byte[][] expected, byte[][] deserialized ) {
        if ( !arraysEquals( expected, deserialized ) ) {
            fail( "expected: " + format( expected ) + ", actual: " + format( deserialized ) );
        }
    }

    /**
     * Returns <code>true</code> if the two specified arrays of arrays of bytes are <em>equal</em> to one another
     *
     * @param array0 one array to be tested for equality
     * @param array1 the other array to be tested for equality
     *
     * @return <code>true</code> if the two arrays are equal
     */
    private static boolean arraysEquals( byte[][] array0, byte[][] array1 ) {
        if ( array0 != null && array1 != null ) {
            if ( array0.length != array1.length ) {
                return false;
            } else {
                for ( int index = 0; index < array0.length; index += 1 ) {
                    if ( !Arrays.equals( array0[index], array1[index] ) ) {
                        return false;
                    }
                }

                return true;
            }
        } else if ( array0 == array1 ) {
            return true;
        } else {
            return false;
        }
    }

    private static String format( byte[][] value ) {
        if ( value == null ) {
            return "null";
        }

        StringBuilder builder = new StringBuilder( "<" );
        String sep1 = "";
        for ( byte[] bytes : value ) {
            builder.append( sep1 ).append( "[" );
            if ( bytes != null ) {
                String sep2 = "";
                for ( byte b : bytes ) {
                    builder.append( sep2 ).append( b );
                    sep2 = ",";
                }
            } else {
                builder.append( "null" );
            }
            builder.append( "]" );
            sep1 = ",";
        }
        builder.append( ">" );

        return builder.toString();
    }

}
