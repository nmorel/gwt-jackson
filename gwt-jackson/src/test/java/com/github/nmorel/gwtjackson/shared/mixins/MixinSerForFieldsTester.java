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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

public final class MixinSerForFieldsTester extends AbstractTester {
    /*
    /**********************************************************
    /* Helper bean classes
    /**********************************************************
     */

    @JsonPropertyOrder(alphabetic = true)
    public static class BaseClass {

        public String a;

        protected String b;

        public BaseClass( String a, String b ) {
            this.a = a;
            this.b = b;
        }
    }

    public static class SubClass extends BaseClass {

        public SubClass( String a, String b ) {
            super( a, b );
        }
    }

    public abstract class MixIn {

        // Let's add 'b' as "banana"
        @JsonProperty("banana")
        public String b;
    }

    public abstract class MixIn2 {

        // Let's remove 'a'
        @JsonIgnore
        public String a;

        // also: add a dummy field that is NOT to match anything
        @JsonProperty
        public String xyz;
    }

    public static final MixinSerForFieldsTester INSTANCE = new MixinSerForFieldsTester();

    private MixinSerForFieldsTester() {
    }
    
    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testFieldMixInsTopLevel( ObjectWriterTester<BaseClass> writer ) {
        // with simple mix-in
        String json = writer.write( new BaseClass( "1", "2" ) );
        assertEquals( "{\"a\":\"1\",\"banana\":\"2\"}", json );
    }

    public void testMultipleFieldMixIns( ObjectWriterTester<SubClass> writer ) {
        String json = writer.write( new SubClass( "1", "2" ) );
        // 'a' should be suppressed; 'b' mapped to 'banana'
        assertEquals( "{\"banana\":\"2\"}", json );
    }
}
