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

package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * Unit tests dealing with handling of "root element wrapping",
 * including configuration of root name to use.
 */
public final class JsonRawValueTester extends AbstractTester {

    /*
    /*********************************************************
    /* Helper bean classes
    /*********************************************************
     */

    /// Class for testing {@link JsonRawValue} annotations with getters returning String
    @JsonPropertyOrder( alphabetic = true )
    public final static class ClassGetter<T> {

        private final T _value;

        private ClassGetter( T value ) { _value = value;}

        public T getNonRaw() { return _value; }

        @JsonProperty( "raw" )
        @JsonRawValue
        public T foobar() { return _value; }

        @JsonProperty
        @JsonRawValue
        protected T value() { return _value; }
    }

    public static final JsonRawValueTester INSTANCE = new JsonRawValueTester();

    private JsonRawValueTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */
    public void testSimpleStringGetter( ObjectWriterTester<ClassGetter<String>> writer ) {
        String result = writer.write( new ClassGetter<String>( "abc" ) );
        assertEquals( "{\"nonRaw\":\"abc\",\"raw\":abc,\"value\":abc}", result );
    }

    public void testSimpleNonStringGetter( ObjectWriterTester<ClassGetter<Integer>> writer ) {
        String result = writer.write( new ClassGetter<Integer>( 123 ) );
        assertEquals( "{\"nonRaw\":123,\"raw\":123,\"value\":123}", result );
    }

    public void testNullStringGetter( ObjectWriterTester<ClassGetter<String>> writer ) {
        String result = writer.write( new ClassGetter<String>( null ) );
        assertEquals( "{\"nonRaw\":null,\"raw\":null,\"value\":null}", result );
    }
}
