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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

public final class MixinInheritanceTester extends AbstractTester {

    // [Issue-14]
    public static class Beano {

        public int ido = 42;

        public String nameo = "Bob";
    }

    public static class BeanoMixinSuper {

        @JsonProperty( "name" )
        public String nameo;
    }

    public static class BeanoMixinSub extends BeanoMixinSuper {

        @JsonProperty( "id" )
        public int ido;
    }

    public static class Beano2 {

        private int ido;

        private String nameo;

        public int getIdo() { return 13; }

        public String getNameo() { return "Bill"; }

        public void setIdo( int ido ) {
            this.ido = ido;
        }

        public void setNameo( String nameo ) {
            this.nameo = nameo;
        }
    }

    public static abstract class BeanoMixinSuper2 extends Beano2 {

        @Override
        @JsonProperty( "name" )
        public abstract String getNameo();
    }

    public static abstract class BeanoMixinSub2 extends BeanoMixinSuper2 {

        @Override
        @JsonProperty( "id" )
        public abstract int getIdo();
    }

    public static final MixinInheritanceTester INSTANCE = new MixinInheritanceTester();

    private MixinInheritanceTester() {
    }
    
    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testMixinFieldInheritance( ObjectMapperTester<Beano> mapper ) {
        String json = mapper.write( new Beano() );
        assertEquals( "{\"id\":42,\"name\":\"Bob\"}", json );

        Beano result = mapper.read( "{\"id\":35,\"name\":\"Alice\"}" );
        assertEquals( "Alice", result.nameo );
        assertEquals( 35, result.ido );
    }

    public void testMixinMethodInheritance( ObjectMapperTester<Beano2> mapper ) {
        String json = mapper.write( new Beano2() );
        assertEquals( "{\"id\":13,\"name\":\"Bill\"}", json );

        Beano2 result = mapper.read( "{\"id\":35,\"name\":\"Alice\"}" );
        assertEquals( "Alice", result.nameo );
        assertEquals( 35, result.ido );
    }
}
