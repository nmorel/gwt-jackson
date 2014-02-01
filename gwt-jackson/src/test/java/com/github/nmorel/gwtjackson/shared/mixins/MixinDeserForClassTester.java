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
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;

public final class MixinDeserForClassTester extends AbstractTester {

    /*
    /**********************************************************
    /* Helper bean classes
    /**********************************************************
     */
    public static class BaseClass {

        /* property that is always found; but has lower priority than
         * setter method if both found
         */
        @JsonProperty
        public String a;

        // setter that may or may not be auto-detected
        public void setA( String v ) { a = "XXX" + v; }
    }

    @JsonAutoDetect(setterVisibility = Visibility.ANY, fieldVisibility = Visibility.ANY)
    public static class LeafClass extends BaseClass {}

    @JsonAutoDetect(setterVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE)
    public interface MixIn {}

    public static final MixinDeserForClassTester INSTANCE = new MixinDeserForClassTester();

    private MixinDeserForClassTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testClassMixInsTopLevel( ObjectReaderTester<LeafClass> reader ) {
        LeafClass result = reader.read( "{\"a\":\"value\"}" );
        assertEquals( "value", result.a );
    }

    /* and then a test for mid-level mixin; should have no effect
     * when deserializing leaf (but will if deserializing base class)
     */
    public void testClassMixInsMidLevel( ObjectReaderTester<BaseClass> readerBase, ObjectReaderTester<LeafClass> readerLeaf ) {
        {
            BaseClass result = readerBase.read( "{\"a\":\"value\"}" );
            assertEquals( "value", result.a );
        }

        // whereas with leaf class, reverts to default
        {
            LeafClass result = readerLeaf.read( "{\"a\":\"value\"}" );
            assertEquals( "XXXvalue", result.a );
        }
    }

    /* Also: when mix-in attached to Object.class, will work, if
     * visible (similar to mid-level, basically)
     */
    public void testClassMixInsForObjectClass( ObjectReaderTester<BaseClass> readerBase, ObjectReaderTester<LeafClass> readerLeaf ) {
        // will be seen for BaseClass
        {
            BaseClass result = readerBase.read( "{\"a\":\"\"}" );
            assertEquals( "", result.a );
        }

        // but LeafClass still overrides
        {
            LeafClass result = readerLeaf.read( "{\"a\":\"\"}" );
            assertEquals( "XXX", result.a );
        }
    }
}
