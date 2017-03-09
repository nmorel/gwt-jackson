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

import java.math.BigInteger;
import java.util.Arrays;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.stream.impl.MalformedJsonException;
import com.github.nmorel.gwtjackson.client.stream.impl.StringReader;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsArrayString;

import static com.github.nmorel.gwtjackson.client.stream.JsonToken.BEGIN_ARRAY;
import static com.github.nmorel.gwtjackson.client.stream.JsonToken.BEGIN_OBJECT;
import static com.github.nmorel.gwtjackson.client.stream.JsonToken.BOOLEAN;
import static com.github.nmorel.gwtjackson.client.stream.JsonToken.END_ARRAY;
import static com.github.nmorel.gwtjackson.client.stream.JsonToken.END_OBJECT;
import static com.github.nmorel.gwtjackson.client.stream.JsonToken.NAME;
import static com.github.nmorel.gwtjackson.client.stream.JsonToken.NULL;
import static com.github.nmorel.gwtjackson.client.stream.JsonToken.NUMBER;
import static com.github.nmorel.gwtjackson.client.stream.JsonToken.STRING;

@SuppressWarnings("resource")
public abstract class AbstractJsonReaderTest extends GwtJacksonTestCase {

    public abstract JsonReader newJsonReader( String input );

    public void testReadArray() {
        JsonReader reader = newJsonReader( "[true, true]" );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        assertEquals( true, reader.nextBoolean() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testReadEmptyArray() {
        JsonReader reader = newJsonReader( "[]" );
        reader.beginArray();
        assertFalse( reader.hasNext() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testReadObject() {
        JsonReader reader = newJsonReader( "{\"a\": \"android\", \"b\": \"banana\"}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( "android", reader.nextString() );
        assertEquals( "b", reader.nextName() );
        assertEquals( "banana", reader.nextString() );
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testReadEmptyObject() {
        JsonReader reader = newJsonReader( "{}" );
        reader.beginObject();
        assertFalse( reader.hasNext() );
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testSkipArray() {
        JsonReader reader = newJsonReader( "{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        reader.skipValue();
        assertEquals( "b", reader.nextName() );
        assertEquals( 123, reader.nextInt() );
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testSkipArrayAfterPeek() throws Exception {
        JsonReader reader = newJsonReader( "{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( BEGIN_ARRAY, reader.peek() );
        reader.skipValue();
        assertEquals( "b", reader.nextName() );
        assertEquals( 123, reader.nextInt() );
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testSkipTopLevelObject() throws Exception {
        JsonReader reader = newJsonReader( "{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}" );
        reader.skipValue();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testSkipObject() {
        JsonReader reader = newJsonReader( "{\"a\": { \"c\": [], \"d\": [true, true, {}] }, \"b\": \"banana\"}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        reader.skipValue();
        assertEquals( "b", reader.nextName() );
        reader.skipValue();
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testSkipObjectAfterPeek() throws Exception {
        String json = "{" + "  \"one\": { \"num\": 1 }" + ", \"two\": { \"num\": 2 }" + ", \"three\": { \"num\": 3 }" + "}";
        JsonReader reader = newJsonReader( json );
        reader.beginObject();
        assertEquals( "one", reader.nextName() );
        assertEquals( BEGIN_OBJECT, reader.peek() );
        reader.skipValue();
        assertEquals( "two", reader.nextName() );
        assertEquals( BEGIN_OBJECT, reader.peek() );
        reader.skipValue();
        assertEquals( "three", reader.nextName() );
        reader.skipValue();
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testSkipInteger() {
        JsonReader reader = newJsonReader( "{\"a\":123456789,\"b\":-123456789}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        reader.skipValue();
        assertEquals( "b", reader.nextName() );
        reader.skipValue();
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testSkipDouble() {
        JsonReader reader = newJsonReader( "{\"a\":-123.456e-789,\"b\":123456789.0}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        reader.skipValue();
        assertEquals( "b", reader.nextName() );
        reader.skipValue();
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testHelloWorld() {
        String json = "{\n" +
                "   \"hello\": true,\n" +
                "   \"foo\": [\"world\"]\n" +
                "}";
        JsonReader reader = newJsonReader( json );
        reader.beginObject();
        assertEquals( "hello", reader.nextName() );
        assertEquals( true, reader.nextBoolean() );
        assertEquals( "foo", reader.nextName() );
        reader.beginArray();
        assertEquals( "world", reader.nextString() );
        reader.endArray();
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testNulls() {
        try {
            newJsonReader( null );
            fail();
        } catch ( NullPointerException expected ) {
        }
    }

    public void testEmptyString() {
        try {
            newJsonReader( "" ).beginArray();
        } catch ( JsonDeserializationException expected ) {
        }
        try {
            newJsonReader( "" ).beginObject();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testNoTopLevelObject() {
        try {
            newJsonReader( "true" ).nextBoolean();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testCharacterUnescaping() {
        String json = "[\"a\"," + "\"a\\\"\"," + "\"\\\"\"," + "\":\"," + "\",\"," + "\"\\b\"," + "\"\\f\"," + "\"\\n\"," + "\"\\r\"," +
                "" + "\"\\t\"," + "\" \"," + "\"\\\\\"," + "\"{\"," + "\"}\"," + "\"[\"," + "\"]\"," + "\"\\u0000\"," + "\"\\u0019\"," +
                "" + "\"\\u20AC\"" + "]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        assertEquals( "a", reader.nextString() );
        assertEquals( "a\"", reader.nextString() );
        assertEquals( "\"", reader.nextString() );
        assertEquals( ":", reader.nextString() );
        assertEquals( ",", reader.nextString() );
        assertEquals( "\b", reader.nextString() );
        assertEquals( "\f", reader.nextString() );
        assertEquals( "\n", reader.nextString() );
        assertEquals( "\r", reader.nextString() );
        assertEquals( "\t", reader.nextString() );
        assertEquals( " ", reader.nextString() );
        assertEquals( "\\", reader.nextString() );
        assertEquals( "{", reader.nextString() );
        assertEquals( "}", reader.nextString() );
        assertEquals( "[", reader.nextString() );
        assertEquals( "]", reader.nextString() );
        assertEquals( "\0", reader.nextString() );
        assertEquals( "\u0019", reader.nextString() );
        assertEquals( "\u20AC", reader.nextString() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testUnescapingInvalidCharacters() {
        String json = "[\"\\u000g\"]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        try {
            reader.nextString();
            fail();
        } catch ( NumberFormatException expected ) {
        }
    }

    public void testUnescapingTruncatedCharacters() {
        String json = "[\"\\u000";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        try {
            reader.nextString();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testUnescapingTruncatedSequence() {
        String json = "[\"\\";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        try {
            reader.nextString();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testIntegersWithFractionalPartSpecified() {
        JsonReader reader = newJsonReader( "[1.0,1.0,1.0]" );
        reader.beginArray();
        assertEquals( 1.0, reader.nextDouble() );
        assertEquals( 1, reader.nextInt() );
        assertEquals( 1L, reader.nextLong() );
    }

    public void testDoubles() {
        String json = "[-0.0," + "1.0," + "1.7976931348623157E308," + "4.9E-324," + "0.0," + "-0.5," + "2.2250738585072014E-308," +
                "" + "3.141592653589793," + "2.718281828459045]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        assertEquals( -0.0, reader.nextDouble() );
        assertEquals( 1.0, reader.nextDouble() );
        assertEquals( 1.7976931348623157E308, reader.nextDouble() );
        assertEquals( 4.9E-324, reader.nextDouble() );
        assertEquals( 0.0, reader.nextDouble() );
        assertEquals( -0.5, reader.nextDouble() );
        assertEquals( 2.2250738585072014E-308, reader.nextDouble() );
        assertEquals( 3.141592653589793, reader.nextDouble() );
        assertEquals( 2.718281828459045, reader.nextDouble() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testStrictNonFiniteDoubles() {
        String json = "[NaN]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        try {
            reader.nextDouble();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testStrictQuotedNonFiniteDoubles() {
        String json = "[\"NaN\"]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        try {
            reader.nextDouble();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testLenientNonFiniteDoubles() {
        String json = "[NaN, -Infinity, Infinity]";
        JsonReader reader = newJsonReader( json );
        reader.setLenient( true );
        reader.beginArray();
        assertTrue( Double.isNaN( reader.nextDouble() ) );
        assertEquals( Double.NEGATIVE_INFINITY, reader.nextDouble() );
        assertEquals( Double.POSITIVE_INFINITY, reader.nextDouble() );
        reader.endArray();
    }

    public void testLenientQuotedNonFiniteDoubles() {
        String json = "[\"NaN\", \"-Infinity\", \"Infinity\"]";
        JsonReader reader = newJsonReader( json );
        reader.setLenient( true );
        reader.beginArray();
        assertTrue( Double.isNaN( reader.nextDouble() ) );
        assertEquals( Double.NEGATIVE_INFINITY, reader.nextDouble() );
        assertEquals( Double.POSITIVE_INFINITY, reader.nextDouble() );
        reader.endArray();
    }

    public void testStrictNonFiniteDoublesWithSkipValue() {
        String json = "[NaN]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        try {
            reader.skipValue();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testLongs() {
        String json = "[0,0,0," + "1,1,1," + "-1,-1,-1," + "-9223372036854775808," + "9223372036854775807]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        assertEquals( 0L, reader.nextLong() );
        assertEquals( 0, reader.nextInt() );
        assertEquals( 0.0, reader.nextDouble() );
        assertEquals( 1L, reader.nextLong() );
        assertEquals( 1, reader.nextInt() );
        assertEquals( 1.0, reader.nextDouble() );
        assertEquals( -1L, reader.nextLong() );
        assertEquals( -1, reader.nextInt() );
        assertEquals( -1.0, reader.nextDouble() );
        try {
            reader.nextInt();
            fail();
        } catch ( NumberFormatException expected ) {
        }
        assertEquals( Long.MIN_VALUE, reader.nextLong() );
        try {
            reader.nextInt();
            fail();
        } catch ( NumberFormatException expected ) {
        }
        assertEquals( Long.MAX_VALUE, reader.nextLong() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void disabled_testNumberWithOctalPrefix() {
        String json = "[01]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        try {
            reader.peek();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
        try {
            reader.nextInt();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
        try {
            reader.nextLong();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
        try {
            reader.nextDouble();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
        assertEquals( "01", reader.nextString() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testBooleans() {
        JsonReader reader = newJsonReader( "[true,false]" );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        assertEquals( false, reader.nextBoolean() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testPeekingUnquotedStringsPrefixedWithBooleans() {
        JsonReader reader = newJsonReader( "[truey]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( STRING, reader.peek() );
        try {
            reader.nextBoolean();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        assertEquals( "truey", reader.nextString() );
        reader.endArray();
    }

    public void testMalformedNumbers() {
        assertNotANumber( "-" );
        assertNotANumber( "." );

        // exponent lacks digit
        assertNotANumber( "e" );
        assertNotANumber( "0e" );
        assertNotANumber( ".e" );
        assertNotANumber( "0.e" );
        assertNotANumber( "-.0e" );

        // no integer
        assertNotANumber( "e1" );
        assertNotANumber( ".e1" );
        assertNotANumber( "-e1" );

        // trailing characters
        assertNotANumber( "1x" );
        assertNotANumber( "1.1x" );
        assertNotANumber( "1e1x" );
        assertNotANumber( "1ex" );
        assertNotANumber( "1.1ex" );
        assertNotANumber( "1.1e1x" );

        // fraction has no digit
        assertNotANumber( "0." );
        assertNotANumber( "-0." );
        assertNotANumber( "0.e1" );
        assertNotANumber( "-0.e1" );

        // no leading digit
        assertNotANumber( ".0" );
        assertNotANumber( "-.0" );
        assertNotANumber( ".0e1" );
        assertNotANumber( "-.0e1" );
    }

    private void assertNotANumber( String s ) {
        JsonReader reader = newJsonReader( "[" + s + "]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( JsonToken.STRING, reader.peek() );
        assertEquals( s, reader.nextString() );
        reader.endArray();
    }

    public void testPeekingUnquotedStringsPrefixedWithIntegers() {
        JsonReader reader = newJsonReader( "[12.34e5x]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( STRING, reader.peek() );
        try {
            reader.nextInt();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        assertEquals( "12.34e5x", reader.nextString() );
    }

    public void testPeekLongMinValue() {
        JsonReader reader = newJsonReader( "[-9223372036854775808]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( NUMBER, reader.peek() );
        assertEquals( -9223372036854775808L, reader.nextLong() );
    }

    public void testPeekLongMaxValue() {
        JsonReader reader = newJsonReader( "[9223372036854775807]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( NUMBER, reader.peek() );
        assertEquals( 9223372036854775807L, reader.nextLong() );
    }

    public void testLongLargerThanMaxLongThatWrapsAround() {
        JsonReader reader = newJsonReader( "[22233720368547758070]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( NUMBER, reader.peek() );
        try {
            reader.nextLong();
            fail();
        } catch ( NumberFormatException expected ) {
        }
    }

    public void testLongLargerThanMinLongThatWrapsAround() {
        JsonReader reader = newJsonReader( "[-22233720368547758070]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( NUMBER, reader.peek() );
        try {
            reader.nextLong();
            fail();
        } catch ( NumberFormatException expected ) {
        }
    }

    /**
     * This test fails because there's no double for 9223372036854775808, and our
     * long parsing uses Double.parseDouble() for fractional values.
     */
    public void disabled_testPeekLargerThanLongMaxValue() {
        JsonReader reader = newJsonReader( "[9223372036854775808]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( NUMBER, reader.peek() );
        try {
            reader.nextLong();
            fail();
        } catch ( NumberFormatException e ) {
        }
    }

    /**
     * This test fails because there's no double for -9223372036854775809, and our
     * long parsing uses Double.parseDouble() for fractional values.
     */
    public void disabled_testPeekLargerThanLongMinValue() {
        JsonReader reader = newJsonReader( "[-9223372036854775809]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( NUMBER, reader.peek() );
        try {
            reader.nextLong();
            fail();
        } catch ( NumberFormatException expected ) {
        }
        assertEquals( -9223372036854775809d, reader.nextDouble() );
    }

    /**
     * This test fails because there's no double for 9223372036854775806, and
     * our long parsing uses Double.parseDouble() for fractional values.
     */
    public void disabled_testHighPrecisionLong() {
        String json = "[9223372036854775806.000]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        assertEquals( 9223372036854775806L, reader.nextLong() );
        reader.endArray();
    }

    public void testPeekMuchLargerThanLongMinValue() {
        JsonReader reader = newJsonReader( "[-92233720368547758080]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( NUMBER, reader.peek() );
        try {
            reader.nextLong();
            fail();
        } catch ( NumberFormatException expected ) {
        }
        assertEquals( -92233720368547758080d, reader.nextDouble() );
    }

    public void testQuotedNumberWithEscape() {
        JsonReader reader = newJsonReader( "[\"12\u00334\"]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( STRING, reader.peek() );
        assertEquals( 1234, reader.nextInt() );
    }

    public void testMixedCaseLiterals() {
        JsonReader reader = newJsonReader( "[True,TruE,False,FALSE,NULL,nulL]" );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        assertEquals( true, reader.nextBoolean() );
        assertEquals( false, reader.nextBoolean() );
        assertEquals( false, reader.nextBoolean() );
        reader.nextNull();
        reader.nextNull();
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testMissingValue() {
        JsonReader reader = newJsonReader( "{\"a\":}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        try {
            reader.nextString();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testPrematureEndOfInput() {
        JsonReader reader = newJsonReader( "{\"a\":true," );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( true, reader.nextBoolean() );
        try {
            reader.nextName();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testPrematurelyClosed() {
        try {
            JsonReader reader = newJsonReader( "{\"a\":[]}" );
            reader.beginObject();
            reader.close();
            reader.nextName();
            fail();
        } catch ( IllegalStateException expected ) {
        }

        try {
            JsonReader reader = newJsonReader( "{\"a\":[]}" );
            reader.close();
            reader.beginObject();
            fail();
        } catch ( IllegalStateException expected ) {
        }

        try {
            JsonReader reader = newJsonReader( "{\"a\":true}" );
            reader.beginObject();
            reader.nextName();
            reader.peek();
            reader.close();
            reader.nextBoolean();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testNextFailuresDoNotAdvance() {
        JsonReader reader = newJsonReader( "{\"a\":true}" );
        reader.beginObject();
        try {
            reader.nextString();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        assertEquals( "a", reader.nextName() );
        try {
            reader.nextName();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            reader.beginArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            reader.endArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            reader.beginObject();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            reader.endObject();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        assertEquals( true, reader.nextBoolean() );
        try {
            reader.nextString();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            reader.nextName();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            reader.beginArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        try {
            reader.endArray();
            fail();
        } catch ( IllegalStateException expected ) {
        }
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
        reader.close();
    }

    public void testIntegerMismatchFailuresDoNotAdvance() {
        JsonReader reader = newJsonReader( "[1.5]" );
        reader.beginArray();
        try {
            reader.nextInt();
            fail();
        } catch ( NumberFormatException expected ) {
        }
        assertEquals( 1.5d, reader.nextDouble() );
        reader.endArray();
    }

    public void testStringNullIsNotNull() {
        JsonReader reader = newJsonReader( "[\"null\"]" );
        reader.beginArray();
        try {
            reader.nextNull();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testNullLiteralIsNotAString() {
        JsonReader reader = newJsonReader( "[null]" );
        reader.beginArray();
        try {
            reader.nextString();
            fail();
        } catch ( IllegalStateException expected ) {
        }
    }

    public void testStrictNameValueSeparator() {
        JsonReader reader = newJsonReader( "{\"a\"=true}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        try {
            reader.nextBoolean();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "{\"a\"=>true}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        try {
            reader.nextBoolean();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientNameValueSeparator() {
        JsonReader reader = newJsonReader( "{\"a\"=true}" );
        reader.setLenient( true );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( true, reader.nextBoolean() );

        reader = newJsonReader( "{\"a\"=>true}" );
        reader.setLenient( true );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( true, reader.nextBoolean() );
    }

    public void testStrictNameValueSeparatorWithSkipValue() {
        JsonReader reader = newJsonReader( "{\"a\"=true}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "{\"a\"=>true}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testCommentsInStringValue() throws Exception {
        JsonReader reader = newJsonReader( "[\"// comment\"]" );
        reader.beginArray();
        assertEquals( "// comment", reader.nextString() );
        reader.endArray();

        reader = newJsonReader( "{\"a\":\"#someComment\"}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( "#someComment", reader.nextString() );
        reader.endObject();

        reader = newJsonReader( "{\"#//a\":\"#some //Comment\"}" );
        reader.beginObject();
        assertEquals( "#//a", reader.nextName() );
        assertEquals( "#some //Comment", reader.nextString() );
        reader.endObject();
    }

    public void testStrictComments() {
        JsonReader reader = newJsonReader( "[// comment \n true]" );
        reader.beginArray();
        try {
            reader.nextBoolean();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[# comment \n true]" );
        reader.beginArray();
        try {
            reader.nextBoolean();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[/* comment */ true]" );
        reader.beginArray();
        try {
            reader.nextBoolean();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientComments() {
        JsonReader reader = newJsonReader( "[// comment \n true]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );

        reader = newJsonReader( "[# comment \n true]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );

        reader = newJsonReader( "[/* comment */ true]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
    }

    public void testStrictCommentsWithSkipValue() {
        JsonReader reader = newJsonReader( "[// comment \n true]" );
        reader.beginArray();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[# comment \n true]" );
        reader.beginArray();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[/* comment */ true]" );
        reader.beginArray();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictUnquotedNames() {
        JsonReader reader = newJsonReader( "{a:true}" );
        reader.beginObject();
        try {
            reader.nextName();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientUnquotedNames() {
        JsonReader reader = newJsonReader( "{a:true}" );
        reader.setLenient( true );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
    }

    public void testStrictUnquotedNamesWithSkipValue() {
        JsonReader reader = newJsonReader( "{a:true}" );
        reader.beginObject();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictSingleQuotedNames() {
        JsonReader reader = newJsonReader( "{'a':true}" );
        reader.beginObject();
        try {
            reader.nextName();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientSingleQuotedNames() {
        JsonReader reader = newJsonReader( "{'a':true}" );
        reader.setLenient( true );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
    }

    public void testStrictSingleQuotedNamesWithSkipValue() {
        JsonReader reader = newJsonReader( "{'a':true}" );
        reader.beginObject();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictUnquotedStrings() {
        JsonReader reader = newJsonReader( "[a]" );
        reader.beginArray();
        try {
            reader.nextString();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testStrictUnquotedStringsWithSkipValue() {
        JsonReader reader = newJsonReader( "[a]" );
        reader.beginArray();
        try {
            reader.skipValue();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testLenientUnquotedStrings() {
        JsonReader reader = newJsonReader( "[a]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( "a", reader.nextString() );
    }

    public void testStrictSingleQuotedStrings() {
        JsonReader reader = newJsonReader( "['a']" );
        reader.beginArray();
        try {
            reader.nextString();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientSingleQuotedStrings() {
        JsonReader reader = newJsonReader( "['a']" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( "a", reader.nextString() );
    }

    public void testStrictSingleQuotedStringsWithSkipValue() {
        JsonReader reader = newJsonReader( "['a']" );
        reader.beginArray();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictSemicolonDelimitedArray() {
        JsonReader reader = newJsonReader( "[true;true]" );
        reader.beginArray();
        try {
            reader.nextBoolean();
            reader.nextBoolean();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientSemicolonDelimitedArray() {
        JsonReader reader = newJsonReader( "[true;true]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        assertEquals( true, reader.nextBoolean() );
    }

    public void testStrictSemicolonDelimitedArrayWithSkipValue() {
        JsonReader reader = newJsonReader( "[true;true]" );
        reader.beginArray();
        try {
            reader.skipValue();
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictSemicolonDelimitedNameValuePair() {
        JsonReader reader = newJsonReader( "{\"a\":true;\"b\":true}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        try {
            reader.nextBoolean();
            reader.nextName();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientSemicolonDelimitedNameValuePair() {
        JsonReader reader = newJsonReader( "{\"a\":true;\"b\":true}" );
        reader.setLenient( true );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( true, reader.nextBoolean() );
        assertEquals( "b", reader.nextName() );
    }

    public void testStrictSemicolonDelimitedNameValuePairWithSkipValue() {
        JsonReader reader = newJsonReader( "{\"a\":true;\"b\":true}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        try {
            reader.skipValue();
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictUnnecessaryArraySeparators() {
        JsonReader reader = newJsonReader( "[true,,true]" );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        try {
            reader.nextNull();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[,true]" );
        reader.beginArray();
        try {
            reader.nextNull();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[true,]" );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        try {
            reader.nextNull();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[,]" );
        reader.beginArray();
        try {
            reader.nextNull();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientUnnecessaryArraySeparators() {
        JsonReader reader = newJsonReader( "[true,,true]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        reader.nextNull();
        assertEquals( true, reader.nextBoolean() );
        reader.endArray();

        reader = newJsonReader( "[,true]" );
        reader.setLenient( true );
        reader.beginArray();
        reader.nextNull();
        assertEquals( true, reader.nextBoolean() );
        reader.endArray();

        reader = newJsonReader( "[true,]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        reader.nextNull();
        reader.endArray();

        reader = newJsonReader( "[,]" );
        reader.setLenient( true );
        reader.beginArray();
        reader.nextNull();
        reader.nextNull();
        reader.endArray();
    }

    public void testStrictUnnecessaryArraySeparatorsWithSkipValue() {
        JsonReader reader = newJsonReader( "[true,,true]" );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[,true]" );
        reader.beginArray();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[true,]" );
        reader.beginArray();
        assertEquals( true, reader.nextBoolean() );
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }

        reader = newJsonReader( "[,]" );
        reader.beginArray();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictMultipleTopLevelValues() {
        JsonReader reader = newJsonReader( "[] []" );
        reader.beginArray();
        reader.endArray();
        try {
            reader.peek();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientMultipleTopLevelValues() {
        JsonReader reader = newJsonReader( "[] true {}" );
        reader.setLenient( true );
        reader.beginArray();
        reader.endArray();
        assertEquals( true, reader.nextBoolean() );
        reader.beginObject();
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testStrictMultipleTopLevelValuesWithSkipValue() {
        JsonReader reader = newJsonReader( "[] []" );
        reader.beginArray();
        reader.endArray();
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictTopLevelString() {
        JsonReader reader = newJsonReader( "\"a\"" );
        try {
            reader.nextString();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientTopLevelString() {
        JsonReader reader = newJsonReader( "\"a\"" );
        reader.setLenient( true );
        assertEquals( "a", reader.nextString() );
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testStrictTopLevelValueType() {
        JsonReader reader = newJsonReader( "true" );
        try {
            reader.nextBoolean();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientTopLevelValueType() {
        JsonReader reader = newJsonReader( "true" );
        reader.setLenient( true );
        assertEquals( true, reader.nextBoolean() );
    }

    public void testStrictTopLevelValueTypeWithSkipValue() {
        JsonReader reader = newJsonReader( "true" );
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictNonExecutePrefix() {
        JsonReader reader = newJsonReader( ")]}'\n []" );
        try {
            reader.beginArray();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testStrictNonExecutePrefixWithSkipValue() {
        JsonReader reader = newJsonReader( ")]}'\n []" );
        try {
            reader.skipValue();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientNonExecutePrefix() {
        JsonReader reader = newJsonReader( ")]}'\n []" );
        reader.setLenient( true );
        reader.beginArray();
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testLenientNonExecutePrefixWithLeadingWhitespace() {
        JsonReader reader = newJsonReader( "\r\n \t)]}'\n []" );
        reader.setLenient( true );
        reader.beginArray();
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testLenientPartialNonExecutePrefix() {
        JsonReader reader = newJsonReader( ")]}' []" );
        reader.setLenient( true );
        try {
            assertEquals( ")", reader.nextString() );
            reader.nextString();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testBomIgnoredAsFirstCharacterOfDocument() {
        JsonReader reader = newJsonReader( "\ufeff[]" );
        reader.beginArray();
        reader.endArray();
    }

    public void testBomForbiddenAsOtherCharacterInDocument() {
        JsonReader reader = newJsonReader( "[\ufeff]" );
        reader.beginArray();
        try {
            reader.endArray();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testFailWithPosition() {
        testFailWithPosition( "Expected value at line 6 column 5", "[\n\n\n\n\n\"a\",}]" );
    }

    public void testFailWithPositionGreaterThanBufferSize() {
        String spaces = repeat( ' ', 8192 );
        testFailWithPosition( "Expected value at line 6 column 5", "[\n\n" + spaces + "\n\n\n\"a\",}]" );
    }

    public void testFailWithPositionOverSlashSlashEndOfLineComment() {
        testFailWithPosition( "Expected value at line 5 column 6", "\n// foo\n\n//bar\r\n[\"a\",}" );
    }

    public void testFailWithPositionOverHashEndOfLineComment() {
        testFailWithPosition( "Expected value at line 5 column 6", "\n# foo\n\n#bar\r\n[\"a\",}" );
    }

    public void testFailWithPositionOverCStyleComment() {
        testFailWithPosition( "Expected value at line 6 column 12", "\n\n/* foo\n*\n*\r\nbar */[\"a\",}" );
    }

    public void testFailWithPositionOverQuotedString() {
        testFailWithPosition( "Expected value at line 5 column 3", "[\"foo\nbar\r\nbaz\n\",\n  }" );
    }

    public void testFailWithPositionOverUnquotedString() {
        testFailWithPosition( "Expected value at line 5 column 2", "[\n\nabcd\n\n,}" );
    }

    public void testFailWithEscapedNewlineCharacter() {
        testFailWithPosition( "Expected value at line 5 column 3", "[\n\n\"\\\n\n\",}" );
    }

    public void testFailWithPositionIsOffsetByBom() {
        testFailWithPosition( "Expected value at line 1 column 6", "\ufeff[\"a\",}]" );
    }

    private void testFailWithPosition( String message, String json ) {
        // Validate that it works reading the string normally.
        JsonReader reader1 = newJsonReader( json );
        reader1.setLenient( true );
        reader1.beginArray();
        reader1.nextString();
        try {
            reader1.peek();
            fail();
        } catch ( JsonDeserializationException expected ) {
            assertEquals( message, expected.getMessage() );
        }

        // Also validate that it works when skipping.
        JsonReader reader2 = newJsonReader( json );
        reader2.setLenient( true );
        reader2.beginArray();
        reader2.skipValue();
        try {
            reader2.peek();
            fail();
        } catch ( JsonDeserializationException expected ) {
            assertEquals( message, expected.getMessage() );
        }
    }

    public void testVeryLongUnquotedLiteral() {
        String literal = "a" + repeat( 'b', 8192 ) + "c";
        JsonReader reader = newJsonReader( "[" + literal + "]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( literal, reader.nextString() );
        reader.endArray();
    }

    public void testDeeplyNestedArrays() {
        // this is nested 40 levels deep; Gson is tuned for nesting is 30 levels deep or fewer
        JsonReader reader = newJsonReader( "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]" );
        for ( int i = 0; i < 40; i++ ) {
            reader.beginArray();
        }
        for ( int i = 0; i < 40; i++ ) {
            reader.endArray();
        }
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testDeeplyNestedObjects() {
        // Build a JSON document structured like {"a":{"a":{"a":{"a":true}}}}, but 40 levels deep
        String array = "{\"a\":%s}";
        String json = "true";
        for ( int i = 0; i < 40; i++ ) {
            //      json = String.format( array, json );
            json = array.replace( "%s", json );
        }

        JsonReader reader = newJsonReader( json );
        for ( int i = 0; i < 40; i++ ) {
            reader.beginObject();
            assertEquals( "a", reader.nextName() );
        }
        assertEquals( true, reader.nextBoolean() );
        for ( int i = 0; i < 40; i++ ) {
            reader.endObject();
        }
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    // http://code.google.com/p/google-gson/issues/detail?id=409
    public void testStringEndingInSlash() {
        JsonReader reader = newJsonReader( "/" );
        reader.setLenient( true );
        try {
            reader.peek();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testDocumentWithCommentEndingInSlash() {
        JsonReader reader = newJsonReader( "/* foo *//" );
        reader.setLenient( true );
        try {
            reader.peek();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testStringWithLeadingSlash() {
        JsonReader reader = newJsonReader( "/x" );
        reader.setLenient( true );
        try {
            reader.peek();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testUnterminatedObject() {
        JsonReader reader = newJsonReader( "{\"a\":\"android\"x" );
        reader.setLenient( true );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( "android", reader.nextString() );
        try {
            reader.peek();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testVeryLongQuotedString() {
        char[] stringChars = new char[1024 * 16];
        Arrays.fill( stringChars, 'x' );
        String string = new String( stringChars );
        String json = "[\"" + string + "\"]";
        JsonReader reader = newJsonReader( json );
        reader.beginArray();
        assertEquals( string, reader.nextString() );
        reader.endArray();
    }

    public void testVeryLongUnquotedString() {
        char[] stringChars = new char[1024 * 16];
        Arrays.fill( stringChars, 'x' );
        String string = new String( stringChars );
        String json = "[" + string + "]";
        JsonReader reader = newJsonReader( json );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( string, reader.nextString() );
        reader.endArray();
    }

    public void testVeryLongUnterminatedString() {
        char[] stringChars = new char[1024 * 16];
        Arrays.fill( stringChars, 'x' );
        String string = new String( stringChars );
        String json = "[" + string;
        JsonReader reader = newJsonReader( json );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( string, reader.nextString() );
        try {
            reader.peek();
            fail();
        } catch ( JsonDeserializationException expected ) {
            assertTrue( expected.getMessage().startsWith( "End of input at line" ) );
        }
    }

    public void testSkipVeryLongUnquotedString() {
        JsonReader reader = newJsonReader( "[" + repeat( 'x', 8192 ) + "]" );
        reader.setLenient( true );
        reader.beginArray();
        reader.skipValue();
        reader.endArray();
    }

    public void testSkipTopLevelUnquotedString() {
        JsonReader reader = newJsonReader( repeat( 'x', 8192 ) );
        reader.setLenient( true );
        reader.skipValue();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testSkipVeryLongQuotedString() {
        JsonReader reader = newJsonReader( "[\"" + repeat( 'x', 8192 ) + "\"]" );
        reader.beginArray();
        reader.skipValue();
        reader.endArray();
    }

    public void testSkipTopLevelQuotedString() {
        JsonReader reader = newJsonReader( "\"" + repeat( 'x', 8192 ) + "\"" );
        reader.setLenient( true );
        reader.skipValue();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testStringAsNumberWithTruncatedExponent() {
        JsonReader reader = newJsonReader( "[123e]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( STRING, reader.peek() );
    }

    public void testStringAsNumberWithDigitAndNonDigitExponent() {
        JsonReader reader = newJsonReader( "[123e4b]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( STRING, reader.peek() );
    }

    public void testStringAsNumberWithNonDigitExponent() {
        JsonReader reader = newJsonReader( "[123eb]" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( STRING, reader.peek() );
    }

    public void testEmptyStringName() {
        JsonReader reader = newJsonReader( "{\"\":true}" );
        reader.setLenient( true );
        assertEquals( BEGIN_OBJECT, reader.peek() );
        reader.beginObject();
        assertEquals( NAME, reader.peek() );
        assertEquals( "", reader.nextName() );
        assertEquals( JsonToken.BOOLEAN, reader.peek() );
        assertEquals( true, reader.nextBoolean() );
        assertEquals( JsonToken.END_OBJECT, reader.peek() );
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testStrictExtraCommasInMaps() {
        JsonReader reader = newJsonReader( "{\"a\":\"b\",}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( "b", reader.nextString() );
        try {
            reader.peek();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    public void testLenientExtraCommasInMaps() {
        JsonReader reader = newJsonReader( "{\"a\":\"b\",}" );
        reader.setLenient( true );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( "b", reader.nextString() );
        try {
            reader.peek();
            fail();
        } catch ( JsonDeserializationException expected ) {
        }
    }

    protected String repeat( char c, int count ) {
        char[] array = new char[count];
        Arrays.fill( array, c );
        return new String( array );
    }

    public void testMalformedDocuments() {
        assertDocument( "{]", BEGIN_OBJECT, JsonDeserializationException.class );
        assertDocument( "{,", BEGIN_OBJECT, JsonDeserializationException.class );
        assertDocument( "{{", BEGIN_OBJECT, JsonDeserializationException.class );
        assertDocument( "{[", BEGIN_OBJECT, JsonDeserializationException.class );
        assertDocument( "{:", BEGIN_OBJECT, JsonDeserializationException.class );
        assertDocument( "{\"name\",", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{\"name\",", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{\"name\":}", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{\"name\"::", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{\"name\":,", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{\"name\"=}", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{\"name\"=>}", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{\"name\"=>\"string\":", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class );
        assertDocument( "{\"name\"=>\"string\"=", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class );
        assertDocument( "{\"name\"=>\"string\"=>", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class );
        assertDocument( "{\"name\"=>\"string\",", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class );
        assertDocument( "{\"name\"=>\"string\",\"name\"", BEGIN_OBJECT, NAME, STRING, NAME );
        assertDocument( "[}", BEGIN_ARRAY, JsonDeserializationException.class );
        assertDocument( "[,]", BEGIN_ARRAY, NULL, NULL, END_ARRAY );
        assertDocument( "{", BEGIN_OBJECT, JsonDeserializationException.class );
        assertDocument( "{\"name\"", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{\"name\",", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{'name'", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{'name',", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "{name", BEGIN_OBJECT, NAME, JsonDeserializationException.class );
        assertDocument( "[", BEGIN_ARRAY, JsonDeserializationException.class );
        assertDocument( "[string", BEGIN_ARRAY, STRING, JsonDeserializationException.class );
        assertDocument( "[\"string\"", BEGIN_ARRAY, STRING, JsonDeserializationException.class );
        assertDocument( "['string'", BEGIN_ARRAY, STRING, JsonDeserializationException.class );
        assertDocument( "[123", BEGIN_ARRAY, NUMBER, JsonDeserializationException.class );
        assertDocument( "[123,", BEGIN_ARRAY, NUMBER, JsonDeserializationException.class );
        assertDocument( "{\"name\":123", BEGIN_OBJECT, NAME, NUMBER, JsonDeserializationException.class );
        assertDocument( "{\"name\":123,", BEGIN_OBJECT, NAME, NUMBER, JsonDeserializationException.class );
        assertDocument( "{\"name\":\"string\"", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class );
        assertDocument( "{\"name\":\"string\",", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class );
        assertDocument( "{\"name\":'string'", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class );
        assertDocument( "{\"name\":'string',", BEGIN_OBJECT, NAME, STRING, JsonDeserializationException.class );
        assertDocument( "{\"name\":false", BEGIN_OBJECT, NAME, BOOLEAN, JsonDeserializationException.class );
        assertDocument( "{\"name\":false,,", BEGIN_OBJECT, NAME, BOOLEAN, JsonDeserializationException.class );
    }

    /**
     * This test behave slightly differently in Gson 2.2 and earlier. It fails
     * during peek rather than during nextString().
     */
    public void testUnterminatedStringFailure() {
        JsonReader reader = newJsonReader( "[\"string" );
        reader.setLenient( true );
        reader.beginArray();
        assertEquals( JsonToken.STRING, reader.peek() );
        try {
            reader.nextString();
            fail();
        } catch ( MalformedJsonException expected ) {
        }
    }

    public void testNextValueNull() {
        JsonReader reader = newJsonReader( "{\"value\":null}" );
        reader.beginObject();
        assertEquals( "value", reader.nextName() );
        assertEquals( "null", reader.nextValue() );
        reader.endObject();
    }

    public void testNextNumber() {
        JsonReader reader = newJsonReader( "[" +
                "123," +
                "12345678999," +
                "54878.45," +
                "\"literal\"," +
                "\"-1545.78\"," +
                "\"2147483647\"," +
                "\"2147483648\"," +
                Integer.MIN_VALUE + "," +
                (Long.valueOf( "" + Integer.MIN_VALUE ) - 1l) + "," +
                Integer.MAX_VALUE + "," +
                (Long.valueOf( "" + Integer.MAX_VALUE ) + 1l) + "," +
                Long.MIN_VALUE + ", " +
                "\"" + new BigInteger( Long.MIN_VALUE + "" ).subtract( BigInteger.ONE ) + "\"," +
                Long.MAX_VALUE + ", " +
                "\"" + new BigInteger( Long.MAX_VALUE + "" ).add( BigInteger.ONE ) + "\"" +
                "]" );
        reader.beginArray();
        assertEquals( new Integer( 123 ), reader.nextNumber() );
        assertEquals( new Long( 12345678999l ), reader.nextNumber() );
        assertEquals( new Double( 54878.45d ), reader.nextNumber() );
        try {
            reader.nextNumber();
            fail();
        } catch ( NumberFormatException e ) {
        }
        assertEquals( "literal", reader.nextString() );
        assertEquals( new Double( -1545.78d ), reader.nextNumber() );
        assertEquals( new Integer( 2147483647 ), reader.nextNumber() );
        assertEquals( new Long( 2147483648l ), reader.nextNumber() );
        assertEquals( Integer.MIN_VALUE, reader.nextNumber() );
        assertEquals( Long.valueOf( "" + Integer.MIN_VALUE ) - 1l, reader.nextNumber() );
        assertEquals( Integer.MAX_VALUE, reader.nextNumber() );
        assertEquals( Long.valueOf( "" + Integer.MAX_VALUE ) + 1l, reader.nextNumber() );
        assertEquals( Long.MIN_VALUE, reader.nextNumber() );
        assertEquals( new BigInteger( Long.MIN_VALUE + "" ).subtract( BigInteger.ONE ), reader.nextNumber() );
        assertEquals( Long.MAX_VALUE, reader.nextNumber() );
        assertEquals( new BigInteger( Long.MAX_VALUE + "" ).add( BigInteger.ONE ), reader.nextNumber() );
        reader.endArray();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testNextJavaScriptObjectRootNoObjectOrArray() {
        JsonReader reader = newJsonReader( "true" );
        reader.setLenient( true );
        try {
            reader.nextJavaScriptObject( true );
            fail();
        } catch ( IllegalStateException e ) {
            // expected exception
        }
    }

    public void testNextJavaScriptObjectNoRootNoObjectOrArray() {
        JsonReader reader = newJsonReader( "{\"name\":\"wrapper\",\"jso\":true}" );
        reader.setLenient( true );

        reader.beginObject();
        assertEquals( "name", reader.nextName() );
        assertEquals( "wrapper", reader.nextString() );

        assertEquals( "jso", reader.nextName() );
        try {
            reader.nextJavaScriptObject( true );
            fail();
        } catch ( IllegalStateException e ) {
            // expected exception
        }
    }

    public void testNextJavaScriptObjectRootObject() {
        // safeEval
        JsonReader reader = newJsonReader( "{\"firstName\":\"Bob\",\"lastName\":\"Morane\"}" );
        Person person = reader.nextJavaScriptObject( true ).cast();
        assertEquals( "Bob", person.getFirstName() );
        assertEquals( "Morane", person.getLastName() );
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );

        // unsafeEval
        reader = newJsonReader( "  \n {\"firstName\":\"Bob\",\"lastName\":\"Morane\"}" );
        person = reader.nextJavaScriptObject( false ).cast();
        assertEquals( "Bob", person.getFirstName() );
        assertEquals( "Morane", person.getLastName() );
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testNextJavaScriptObjectRootArray() {
        // safeEval
        JsonReader reader = newJsonReader( "       [\"Bob\",\"Morane\"]  " );
        JsArrayString array = reader.nextJavaScriptObject( true ).cast();
        assertEquals( 2, array.length() );
        assertEquals( "Bob", array.get( 0 ) );
        assertEquals( "Morane", array.get( 1 ) );
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );

        // unsafeEval
        reader = newJsonReader( "[\"Bob\",\"Morane\"]" );
        array = reader.nextJavaScriptObject( false ).cast();
        assertEquals( 2, array.length() );
        assertEquals( "Bob", array.get( 0 ) );
        assertEquals( "Morane", array.get( 1 ) );
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testNextJavaScriptObjectNoRootObject() {
        // safeEval
        JsonReader reader = newJsonReader( "{\"name\":\"wrapper\",\"jso\":{\"firstName\":\"Bob\",\"lastName\":\"Mor\\\"ane\"}}" );
        reader.beginObject();
        assertEquals( "name", reader.nextName() );
        assertEquals( "wrapper", reader.nextString() );

        assertEquals( "jso", reader.nextName() );
        Person person = reader.nextJavaScriptObject( true ).cast();
        assertEquals( "Bob", person.getFirstName() );
        assertEquals( "Mor\"ane", person.getLastName() );

        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );

        // unsafeEval
        reader = newJsonReader( "{\"jso\":{\"firstName\":\"Bob\",\"lastName\":\"Morane\"},\"name\":\"wrapper\"}" );
        reader.beginObject();

        assertEquals( "jso", reader.nextName() );
        person = reader.nextJavaScriptObject( false ).cast();
        assertEquals( "Bob", person.getFirstName() );
        assertEquals( "Morane", person.getLastName() );

        assertEquals( "name", reader.nextName() );
        assertEquals( "wrapper", reader.nextString() );
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testNextJavaScriptObjectNoRootArray() {
        // safeEval
        JsonReader reader = newJsonReader( "   {\"name\":\"wrapper\",\"jso\":    [\"Bob\",\"Morane\", true, 145] } " );
        reader.beginObject();
        assertEquals( "name", reader.nextName() );
        assertEquals( "wrapper", reader.nextString() );

        assertEquals( "jso", reader.nextName() );
        JsArrayMixed array = reader.nextJavaScriptObject( true ).cast();
        assertEquals( 4, array.length() );
        assertEquals( "Bob", array.getString( 0 ) );
        assertEquals( "Morane", array.getString( 1 ) );
        assertTrue( array.getBoolean( 2 ) );
        assertEquals( 145d, array.getNumber( 3 ) );

        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );

        // unsafeEval
        reader = newJsonReader( "   {\"jso\":    [\"Bob\",\"Morane\", true, 145] ,\"name\":\"wrapper\" } " );
        reader.beginObject();

        assertEquals( "jso", reader.nextName() );
        array = reader.nextJavaScriptObject( false ).cast();
        assertEquals( 4, array.length() );
        assertEquals( "Bob", array.getString( 0 ) );
        assertEquals( "Morane", array.getString( 1 ) );
        assertTrue( array.getBoolean( 2 ) );
        assertEquals( 145d, array.getNumber( 3 ) );

        assertEquals( "name", reader.nextName() );
        assertEquals( "wrapper", reader.nextString() );

        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    public void testObjectWithInnerObjectWithNullValue() {
        JsonReader reader = newJsonReader( "{\"a\": \"android\", \"b\": \"banana\", \"c\": {\"a\": 1, \"b\": null, \"c\": \"carrot\"}}" );
        reader.beginObject();
        assertEquals( "a", reader.nextName() );
        assertEquals( "android", reader.nextString() );
        assertEquals( "b", reader.nextName() );
        assertEquals( "banana", reader.nextString() );
        assertEquals( "c", reader.nextName() );
        JavaScriptObject innerObj = reader.nextJavaScriptObject(true);
        reader.endObject();
        assertEquals( JsonToken.END_DOCUMENT, reader.peek() );
    }

    private void assertDocument( String document, Object... expectations ) {
        JsonReader reader = newJsonReader( document );
        reader.setLenient( true );
        for ( Object expectation : expectations ) {
            if ( expectation == BEGIN_OBJECT ) {
                reader.beginObject();
            } else if ( expectation == BEGIN_ARRAY ) {
                reader.beginArray();
            } else if ( expectation == END_OBJECT ) {
                reader.endObject();
            } else if ( expectation == END_ARRAY ) {
                reader.endArray();
            } else if ( expectation == NAME ) {
                assertEquals( "name", reader.nextName() );
            } else if ( expectation == BOOLEAN ) {
                assertEquals( false, reader.nextBoolean() );
            } else if ( expectation == STRING ) {
                assertEquals( "string", reader.nextString() );
            } else if ( expectation == NUMBER ) {
                assertEquals( 123, reader.nextInt() );
            } else if ( expectation == NULL ) {
                reader.nextNull();
            } else if ( expectation == JsonDeserializationException.class ) {
                try {
                    reader.peek();
                    fail();
                } catch ( JsonDeserializationException expected ) {
                }
            } else {
                throw new AssertionError();
            }
        }
    }

    /**
     * Returns a reader that returns one character at a time.
     */
    private StringReader reader( final String s ) {
        return new StringReader( s );
    }
}
