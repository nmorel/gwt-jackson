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

public final class MixinSerForMethodsTester extends AbstractTester {
    /*
    /**********************************************************
    /* Helper bean classes
    /**********************************************************
     */

    // base class: just one visible property ('b')
    public static class BaseClass {

        @SuppressWarnings("unused")
        private String a;

        private String b;

        protected BaseClass() { }

        public BaseClass( String a, String b ) {
            this.a = a;
            this.b = b;
        }

        @JsonProperty("b")
        public String takeB() { return b; }
    }

    /* extends, just for fun; and to show possible benefit of being
     * able to declare that a method is overridden (compile-time check
     * that our intended mix-in override will match a method)
     */
    public abstract static class MixIn extends BaseClass {

        // let's make 'a' visible
        @JsonProperty
        String a;

        @Override
        @JsonProperty("b2")
        public abstract String takeB();

        // also: just for fun; add a "red herring"... unmatched method
        @JsonProperty
        abstract String getFoobar();
    }

    public static class LeafClass extends BaseClass {

        public LeafClass( String a, String b ) { super( a, b ); }

        @Override
        @JsonIgnore
        public String takeB() { return null; }
    }

    @JsonPropertyOrder( alphabetic = true )
    public interface ObjectMixIn {

        // and then ditto for hashCode..
        @Override
        @JsonProperty
        public int hashCode();
    }

    public static class EmptyBean {}

    public static class SimpleBean extends EmptyBean {

        int x() { return 42; }
    }

    /**
     * This mix-in is to be attached to EmptyBean, but really modify
     * methods that its subclass, SimpleBean, has.
     */
    public abstract class MixInForSimple {

        // This should apply to sub-class
        @JsonProperty("x")
        abstract int x();

        // and this matches nothing, should be ignored
        @JsonProperty("notreally")
        public int xxx() { return 3; }

        // nor this
        public abstract int getIt();
    }

    public static final MixinSerForMethodsTester INSTANCE = new MixinSerForMethodsTester();

    private MixinSerForMethodsTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    /**
     * Unit test for verifying that leaf-level mix-ins work ok;
     * that is, any annotations added properly override all annotations
     * that masked methods (fields etc) have.
     */
    public void testLeafMixin( ObjectWriterTester<BaseClass> writer ) {
        // with leaf-level mix-in
        String json = writer.write( new BaseClass( "a1", "b2" ) );
        assertEquals( "{\"a\":\"a1\",\"b2\":\"b2\"}", json );
    }

    /**
     * Unit test for verifying that having a mix-in "between" classes
     * (overriding annotations of a base class, but being overridden
     * further by a sub-class) works as expected
     */
    public void testIntermediateMixin( ObjectWriterTester<LeafClass> writer ) {
        String json = writer.write( new LeafClass( "XXX", "b2" ) );
        assertEquals( "{\"a\":\"XXX\"}", json );
    }

    /**
     * Another intermediate mix-in, to verify that annotations
     * properly "trickle up"
     */
    public void testIntermediateMixin2( ObjectWriterTester<SimpleBean> writer ) {
        String json = writer.write( new SimpleBean() );
        assertEquals( "{\"x\":42}", json );
    }

    /**
     * Unit test for verifying that it is actually possible to attach
     * mix-in annotations to basic <code>Object.class</code>. This
     * will essentially apply to any and all Objects.
     */
    public void testObjectMixin( ObjectWriterTester<BaseClass> writer ) {
        BaseClass bean = new BaseClass( "a", "b" );
        String json = writer.write( bean );
        assertEquals( "{\"b\":\"b\",\"hashCode\":" + bean.hashCode() + "}", json );

        /* 15-Oct-2010, tatu: Actually, we now block serialization (attemps) of plain Objects, by default
         *    (since generally that makes no sense -- may need to revisit). As such, need to comment out
         *    this part of test
         */
        /* Hmmh. For plain Object.class... I suppose getClass() does
         * get serialized (and can't really be blocked either).
         * Fine.
         */
       /*
        result = writeAndMap(mapper, new Object());
        assertEquals(2, result.size());
        ob = result.get("hashCode");
        assertNotNull(ob);
        assertEquals(Integer.class, ob.getClass());
        assertEquals("java.lang.Object", result.get("class"));
        */
    }
}
