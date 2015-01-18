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

package com.github.nmorel.gwtjackson.client.stream;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

@SuppressWarnings( "resource" )
public abstract class AbstractJsonWriterTest extends GwtJacksonTestCase {

    public abstract JsonWriter newJsonWriter();

    public void testWrongTopLevelType() {
        JsonWriter jsonWriter = newJsonWriter();
        try {
            jsonWriter.value( "a" );
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testTwoNames() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name( "a" );
        try {
            jsonWriter.name( "a" );
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testNameWithoutValue() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name( "a" );
        try {
            jsonWriter.endObject();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testValueWithoutName() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        try {
            jsonWriter.value( true );
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testMultipleTopLevelValues() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray().endArray();
        try {
            jsonWriter.beginArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testBadNestingObject() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.beginObject();
        try {
            jsonWriter.endArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testBadNestingArray() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.beginArray();
        try {
            jsonWriter.endObject();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testNullName() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        try {
            jsonWriter.name( null );
            fail();
        } catch ( NullPointerException expected ) {
        }
    }

    public void testNullStringValue() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name( "a" );
        jsonWriter.value( (String) null );
        jsonWriter.endObject();
        assertEquals( "{\"a\":null}", jsonWriter.getOutput() );
    }

    public void testNonFiniteDoubles() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        try {
            jsonWriter.value( Double.NaN );
            fail();
        } catch ( IllegalArgumentException expected ) {
        }
        try {
            jsonWriter.value( Double.NEGATIVE_INFINITY );
            fail();
        } catch ( IllegalArgumentException expected ) {
        }
        try {
            jsonWriter.value( Double.POSITIVE_INFINITY );
            fail();
        } catch ( IllegalArgumentException expected ) {
        }
    }

    public void testNonFiniteBoxedDoubles() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        try {
            jsonWriter.value( new Double( Double.NaN ) );
            fail();
        } catch ( IllegalArgumentException expected ) {
        }
        try {
            jsonWriter.value( new Double( Double.NEGATIVE_INFINITY ) );
            fail();
        } catch ( IllegalArgumentException expected ) {
        }
        try {
            jsonWriter.value( new Double( Double.POSITIVE_INFINITY ) );
            fail();
        } catch ( IllegalArgumentException expected ) {
        }
    }

    public void testDoubles() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value( -0.0 );
        jsonWriter.value( 1.0 );
        jsonWriter.value( Double.MAX_VALUE );
        jsonWriter.value( Double.MIN_VALUE );
        jsonWriter.value( 0.0 );
        jsonWriter.value( -0.5 );
        jsonWriter.value( 2.2250738585072014E-308 );
        jsonWriter.value( Math.PI );
        jsonWriter.value( Math.E );
        jsonWriter.endArray();
        jsonWriter.close();
        if ( GWT.isProdMode() ) {
            // in compiled mode, the .0 are removed, the power is written with 'e+' instead of 'E' and 'e-' instead of 'E-' and the Double
            // .MIN_VALUE is 5e-324
            assertEquals( "[0," + "1," + "1.7976931348623157e+308," + "5e-324," + "0," + "-0.5," + "2.2250738585072014e-308," +
                    "" + "3.141592653589793," + "2.718281828459045]", jsonWriter.getOutput() );
        } else {
            assertEquals( "[-0.0," + "1.0," + "1.7976931348623157E308," + "4.9E-324," + "0.0," + "-0.5," + "2.2250738585072014E-308," +
                    "" + "3.141592653589793," + "2.718281828459045]", jsonWriter.getOutput() );
        }
    }

    public void testLongs() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value( 0 );
        jsonWriter.value( 1 );
        jsonWriter.value( -1 );
        jsonWriter.value( Long.MIN_VALUE );
        jsonWriter.value( Long.MAX_VALUE );
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals( "[0," + "1," + "-1," + "-9223372036854775808," + "9223372036854775807]", jsonWriter.getOutput() );
    }

    public void testNumbers() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value( new BigInteger( "0" ) );
        jsonWriter.value( new BigInteger( "9223372036854775808" ) );
        jsonWriter.value( new BigInteger( "-9223372036854775809" ) );
        jsonWriter.value( new BigDecimal( "3.141592653589793238462643383" ) );
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals( "[0," + "9223372036854775808," + "-9223372036854775809," + "3.141592653589793238462643383]", jsonWriter.getOutput() );
    }

    public void testBooleans() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value( true );
        jsonWriter.value( false );
        jsonWriter.endArray();
        assertEquals( "[true,false]", jsonWriter.getOutput() );
    }

    public void testNulls() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.nullValue();
        jsonWriter.endArray();
        assertEquals( "[null]", jsonWriter.getOutput() );
    }

    public void testStrings() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value( "a" );
        jsonWriter.value( "a\"" );
        jsonWriter.value( "\"" );
        jsonWriter.value( ":" );
        jsonWriter.value( "," );
        jsonWriter.value( "\b" );
        jsonWriter.value( "\f" );
        jsonWriter.value( "\n" );
        jsonWriter.value( "\r" );
        jsonWriter.value( "\t" );
        jsonWriter.value( " " );
        jsonWriter.value( "\\" );
        jsonWriter.value( "{" );
        jsonWriter.value( "}" );
        jsonWriter.value( "[" );
        jsonWriter.value( "]" );
        jsonWriter.value( "\0" );
        jsonWriter.value( "\u0019" );
        jsonWriter.endArray();
        assertEquals( "[\"a\"," + "\"a\\\"\"," + "\"\\\"\"," + "\":\"," + "\",\"," + "\"\\b\"," + "\"\\f\"," + "\"\\n\"," + "\"\\r\"," +
                "" + "\"\\t\"," + "\" \"," + "\"\\\\\"," + "\"{\"," + "\"}\"," + "\"[\"," + "\"]\"," + "\"\\u0000\"," + "\"\\u0019\"]",
                jsonWriter
                .getOutput() );
    }

    public void testUnicodeLineBreaksEscaped() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.value( "\u2028\u2029" );
        jsonWriter.endArray();
        assertEquals( "[\"\\u2028\\u2029\"]", jsonWriter.getOutput() );
    }

    public void testEmptyArray() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        assertEquals( "[]", jsonWriter.getOutput() );
    }

    public void testEmptyObject() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.endObject();
        assertEquals( "{}", jsonWriter.getOutput() );
    }

    public void testObjectsInArrays() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.beginObject();
        jsonWriter.name( "a" ).value( 5 );
        jsonWriter.name( "b" ).value( false );
        jsonWriter.endObject();
        jsonWriter.beginObject();
        jsonWriter.name( "c" ).value( 6 );
        jsonWriter.name( "d" ).value( true );
        jsonWriter.endObject();
        jsonWriter.endArray();
        assertEquals( "[{\"a\":5,\"b\":false}," + "{\"c\":6,\"d\":true}]", jsonWriter.getOutput() );
    }

    public void testArraysInObjects() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name( "a" );
        jsonWriter.beginArray();
        jsonWriter.value( 5 );
        jsonWriter.value( false );
        jsonWriter.endArray();
        jsonWriter.name( "b" );
        jsonWriter.beginArray();
        jsonWriter.value( 6 );
        jsonWriter.value( true );
        jsonWriter.endArray();
        jsonWriter.endObject();
        assertEquals( "{\"a\":[5,false]," + "\"b\":[6,true]}", jsonWriter.getOutput() );
    }

    public void testDeepNestingArrays() {
        JsonWriter jsonWriter = newJsonWriter();
        for ( int i = 0; i < 20; i++ ) {
            jsonWriter.beginArray();
        }
        for ( int i = 0; i < 20; i++ ) {
            jsonWriter.endArray();
        }
        assertEquals( "[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]", jsonWriter.getOutput() );
    }

    public void testDeepNestingObjects() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        for ( int i = 0; i < 20; i++ ) {
            jsonWriter.name( "a" );
            jsonWriter.beginObject();
        }
        for ( int i = 0; i < 20; i++ ) {
            jsonWriter.endObject();
        }
        jsonWriter.endObject();
        assertEquals( "{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":" +
                "{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{" + "}}}}}}}}}}}}}}}}}}}}}", jsonWriter
                .getOutput() );
    }

    public void testRepeatedName() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name( "a" ).value( true );
        jsonWriter.name( "a" ).value( false );
        jsonWriter.endObject();
        // JsonWriter doesn't attempt to detect duplicate names
        assertEquals( "{\"a\":true,\"a\":false}", jsonWriter.getOutput() );
    }

    public void testPrettyPrintObject() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.setIndent( "   " );

        jsonWriter.beginObject();
        jsonWriter.name( "a" ).value( true );
        jsonWriter.name( "b" ).value( false );
        jsonWriter.name( "c" ).value( 5 );
        jsonWriter.name( "e" ).nullValue();
        jsonWriter.name( "f" ).beginArray();
        jsonWriter.value( 6 );
        jsonWriter.value( 7 );
        jsonWriter.endArray();
        jsonWriter.name( "g" ).beginObject();
        jsonWriter.name( "h" ).value( 8 );
        jsonWriter.name( "i" ).value( 9 );
        jsonWriter.endObject();
        jsonWriter.endObject();

        String expected = "{\n" + "   \"a\": true,\n" + "   \"b\": false,\n" + "   \"c\": 5,\n" + "   \"e\": null," +
                "\n" + "   \"f\": [\n" + "      6,\n" + "      7\n" + "   ],\n" + "   \"g\": {\n" + "      \"h\": 8," +
                "\n" + "      \"i\": 9\n" + "   }\n" + "}";
        assertEquals( expected, jsonWriter.getOutput() );
    }

    public void testPrettyPrintArray() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.setIndent( "   " );

        jsonWriter.beginArray();
        jsonWriter.value( true );
        jsonWriter.value( false );
        jsonWriter.value( 5 );
        jsonWriter.nullValue();
        jsonWriter.beginObject();
        jsonWriter.name( "a" ).value( 6 );
        jsonWriter.name( "b" ).value( 7 );
        jsonWriter.endObject();
        jsonWriter.beginArray();
        jsonWriter.value( 8 );
        jsonWriter.value( 9 );
        jsonWriter.endArray();
        jsonWriter.endArray();

        String expected = "[\n" + "   true,\n" + "   false,\n" + "   5,\n" + "   null,\n" + "   {\n" + "      \"a\": 6," +
                "\n" + "      \"b\": 7\n" + "   },\n" + "   [\n" + "      8,\n" + "      9\n" + "   ]\n" + "]";
        assertEquals( expected, jsonWriter.getOutput() );
    }

    public void testLenientWriterPermitsMultipleTopLevelValues() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.setLenient( true );
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals( "[][]", jsonWriter.getOutput() );
    }

    public void testStrictWriterDoesNotPermitMultipleTopLevelValues() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        try {
            jsonWriter.beginArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testClosedWriterThrowsOnStructure() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.beginArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            jsonWriter.endArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            jsonWriter.beginObject();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            jsonWriter.endObject();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testClosedWriterThrowsOnName() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.name( "a" );
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testClosedWriterThrowsOnValue() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.value( "a" );
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testClosedWriterThrowsOnFlush() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.flush();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testWriterCloseIsIdempotent() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginArray();
        jsonWriter.endArray();
        jsonWriter.close();
        jsonWriter.close();
    }

    public void testEscaping() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name( "\"json\"" );
        jsonWriter.value( "{\"key\":\"value\"}" );
        jsonWriter.endObject();
        jsonWriter.close();

        assertEquals( "{\"\\\"json\\\"\":\"{\\\"key\\\":\\\"value\\\"}\"}", jsonWriter.getOutput() );
    }

    public void testNoEscaping() {
        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.unescapeName( "\"json\"" );
        jsonWriter.unescapeValue( "{\"key\":\"value\"}" );
        jsonWriter.endObject();
        jsonWriter.close();

        assertEquals( "{\"\"json\"\":\"{\"key\":\"value\"}\"}", jsonWriter.getOutput() );
    }

    public void testRootJavaScriptObject() {
        Person person = JavaScriptObject.createObject().cast();
        person.setFirstName( "Bob" );
        person.setLastName( "Morane" );

        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.setLenient( true );

        jsonWriter.value( person );
        jsonWriter.close();

        assertEquals( "{\"firstName\":\"Bob\",\"lastName\":\"Morane\"}", jsonWriter.getOutput() );
    }

    public void testNoRootJavaScriptObject() {
        Person person = JavaScriptObject.createObject().cast();
        person.setFirstName( "Bob" );
        person.setLastName( "Morane" );

        JsonWriter jsonWriter = newJsonWriter();
        jsonWriter.beginObject();
        jsonWriter.name( "jso" );
        jsonWriter.value( person );
        jsonWriter.endObject();
        jsonWriter.close();

        assertEquals( "{\"jso\":{\"firstName\":\"Bob\",\"lastName\":\"Morane\"}}", jsonWriter.getOutput() );
    }
}
