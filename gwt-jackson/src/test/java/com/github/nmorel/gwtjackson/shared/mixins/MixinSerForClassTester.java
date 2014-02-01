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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

public final class MixinSerForClassTester extends AbstractTester {
    /*
    /**********************************************************
    /* Helper bean classes
    /**********************************************************
     */

    @JsonSerialize( include = JsonSerialize.Inclusion.ALWAYS )
    public static class BaseClass {

        protected String _a, _b;

        protected String _c = "c";

        private BaseClass() { }

        public BaseClass( String a ) {
            _a = a;
        }

        // will be auto-detectable unless disabled:
        public String getA() { return _a; }

        @JsonProperty
        public String getB() { return _b; }

        @JsonProperty
        public String getC() { return _c; }
    }

    @JsonSerialize( include = JsonSerialize.Inclusion.NON_DEFAULT )
    public static class LeafClass extends BaseClass {

        public LeafClass() { super( null ); }

        public LeafClass( String a ) {
            super( a );
        }
    }

    @JsonSerialize( include = JsonSerialize.Inclusion.NON_DEFAULT )
    public static class LeafClassToMixin extends BaseClass {

        public LeafClassToMixin() { super( null ); }

        public LeafClassToMixin( String a ) {
            super( a );
        }
    }

    /**
     * This interface only exists to add "mix-in annotations": that is, any
     * annotations it has can be virtually added to mask annotations
     * of other classes
     */
    @JsonSerialize( include = JsonSerialize.Inclusion.NON_NULL )
    public interface MixIn {}

    // test disabling of autodetect...
    @JsonAutoDetect( getterVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE )
    public interface MixInAutoDetect {}

    public static final MixinSerForClassTester INSTANCE = new MixinSerForClassTester();

    private MixinSerForClassTester() {
    }

    /*
    /**********************************************************
    /( Unit tests
    /**********************************************************
     */

    public void testClassMixInsTopLevel( ObjectWriterTester<LeafClassToMixin> writerLeafMix, ObjectWriterTester<LeafClass> writerLeaf ) {
        // With top-level override
        String json = writerLeafMix.write( new LeafClassToMixin( "abc" ) );
        assertEquals( "{\"a\":\"abc\",\"c\":\"c\"}", json );

        // mid-level override; should not have any effect, c is not included because it's the default value
        json = writerLeaf.write( new LeafClass( "abc" ) );
        assertEquals( "{\"a\":\"abc\"}", json );
    }

    public void testClassMixInsMidLevel( ObjectWriterTester<LeafClass> writer ) {
        LeafClass bean = new LeafClass( "xyz" );
        bean._c = "c2";

        // with working mid-level override, which effectively suppresses 'a'
        String json = writer.write( bean );
        assertEquals( "{\"c\":\"c2\"}", json );
    }
}
