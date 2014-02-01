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

package com.github.nmorel.gwtjackson.shared.mixins;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;

public final class MixinDeserForCreatorsTester extends AbstractTester {
    /*
    /**********************************************************
    /* Helper bean classes
    /**********************************************************
     */

    public static class BaseClass {

        private String a;

        public BaseClass() {
        }

        public BaseClass( String a ) {
            this.a = a + "constructor";
        }

        public static BaseClass myFactory( String a ) {
            BaseClass baseClass = new BaseClass();
            baseClass.a = a + "myFactory";
            return baseClass;
        }

        public String getA() {
            return a;
        }

        public void setA( String a ) {
            this.a = a + "set";
        }
    }

    public static class BaseClass2 {

        private String a;

        public BaseClass2( int a ) {
            this.a = "num" + a;
        }

        public BaseClass2( @JsonProperty( "string" ) String a ) {
            this.a = "string" + a;
        }
    }

    public static class MixIn {

        @JsonIgnore
        protected MixIn() { }

        @JsonCreator
        protected MixIn( @JsonProperty( "b" ) String a ) { }
    }

    public static class MixInFactory {

        @JsonCreator
        public static BaseClass myFactory( @JsonProperty( "c" ) String a ) {
            return null;
        }
    }

    public static class MixInIgnore {

        protected MixInIgnore( @JsonProperty( "num" ) int a ) { }

        @JsonIgnore
        protected MixInIgnore( String a ) { }
    }

    public static final MixinDeserForCreatorsTester INSTANCE = new MixinDeserForCreatorsTester();

    private MixinDeserForCreatorsTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */
    public void testForDefaultConstructor( ObjectReaderTester<BaseClass> reader ) {
        BaseClass result = reader.read( "{\"a\":\"...\"}" );
        assertEquals( "...set", result.a );
    }

    public void testForConstructor( ObjectReaderTester<BaseClass> reader ) {
        BaseClass result = reader.read( "{\"b\":\"...\"}" );
        assertEquals( "...constructor", result.a );
    }

    public void testForFactory( ObjectReaderTester<BaseClass> reader ) {
        BaseClass result = reader.read( "{\"c\":\"...\"}" );
        assertEquals( "...myFactory", result.a );
    }

    public void testForIgnoreCreator( ObjectReaderTester<BaseClass2> reader ) {
        BaseClass2 result = reader.read( "{\"num\":10}" );
        assertEquals( "num10", result.a );
    }
}
