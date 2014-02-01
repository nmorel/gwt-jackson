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
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;

public final class MixinDeserForCreatorsDelegationTester extends AbstractTester {
    /*
    /**********************************************************
    /* Helper bean classes
    /**********************************************************
     */

    public static class BaseClass {

        protected String _a;

        public BaseClass( String a ) {
            _a = a + "...";
        }

        private BaseClass( String value, boolean dummy ) {
            _a = value;
        }

        public static BaseClass myFactory( String a ) {
            return new BaseClass( a + "X", true );
        }
    }

    public static class BaseClassWithPrivateCtor {

        protected String _a;

        private BaseClassWithPrivateCtor( String a ) {
            _a = a + "...";
        }

    }

    /**
     * Mix-in class that will effectively suppresses String constructor,
     * and marks a non-auto-detectable static method as factory method
     * as a creator.
     * <p/>
     * Note that method implementations are not used for anything; but
     * we have to a class: interface won't do, as they can't have
     * constructors or static methods.
     */
    public static class MixIn {

        @JsonIgnore
        protected MixIn( String s ) { }

        @JsonCreator
        static BaseClass myFactory( String a ) { return null; }
    }

    public static class MixInForPrivate {

        @JsonCreator
        MixInForPrivate( String s ) { }
    }

    public static class StringWrapper {

        String _value;

        private StringWrapper( String s, boolean foo ) { _value = s; }

        @SuppressWarnings( "unused" )
        private static StringWrapper create( String str ) {
            return new StringWrapper( str, false );
        }
    }

    public abstract static class StringWrapperMixIn {

        @JsonCreator
        static StringWrapper create( String str ) { return null; }
    }

    public static final MixinDeserForCreatorsDelegationTester INSTANCE = new MixinDeserForCreatorsDelegationTester();

    private MixinDeserForCreatorsDelegationTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testForConstructor( ObjectReaderTester<BaseClassWithPrivateCtor> reader ) {
        BaseClassWithPrivateCtor result = reader.read( "\"?\"" );
        assertEquals( "?...", result._a );
    }

    public void testForFactoryAndCtor( ObjectReaderTester<BaseClass> reader ) {
        // simple mix-in: should change to use the factory method
        BaseClass result = reader.read( "\"string\"" );
        assertEquals( "stringX", result._a );
    }

    public void testFactoryMixIn( ObjectReaderTester<StringWrapper> reader ) {
        StringWrapper result = reader.read( "\"a\"" );
        assertEquals( "a", result._value );
    }
}
