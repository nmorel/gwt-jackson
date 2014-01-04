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

package com.github.nmorel.gwtjackson.shared.advanced.jsontype;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * Separate tests for verifying that "type name" type id mechanism
 * works.
 *
 * @author tatu
 */
public final class TypeNamesTester extends AbstractTester {

    /*
    /**********************************************************
    /* Helper types
    /**********************************************************
     */

    @JsonTypeInfo( use = Id.NAME, include = As.WRAPPER_OBJECT )
    @JsonSubTypes( {@Type( value = Dog.class, name = "doggy" ), @Type( Cat.class ) /* defaults to "TypeNamesTester$Cat" then */} )
    @JsonPropertyOrder( alphabetic = true )
    public static class Animal {

        public String name;

        @Override
        public boolean equals( Object o ) {
            if ( o == this ) {
                return true;
            }
            if ( o == null ) {
                return false;
            }
            if ( o.getClass() != getClass() ) {
                return false;
            }
            return name.equals( ((Animal) o).name );
        }

        @Override
        public String toString() {
            return getClass().toString() + "('" + name + "')";
        }
    }

    public static class Dog extends Animal {

        public int ageInYears;

        public Dog() { }

        public Dog( String n, int y ) {
            name = n;
            ageInYears = y;
        }

        @Override
        public boolean equals( Object o ) {
            return super.equals( o ) && ((Dog) o).ageInYears == ageInYears;
        }
    }

    @JsonSubTypes( {@Type( MaineCoon.class ), @Type( Persian.class )} )
    public static abstract class Cat extends Animal {

        public boolean purrs;

        public Cat() { }

        public Cat( String n, boolean p ) {
            name = n;
            purrs = p;
        }

        @Override
        public boolean equals( Object o ) {
            return super.equals( o ) && ((Cat) o).purrs == purrs;
        }

        @Override
        public String toString() {
            return super.toString() + "(purrs: " + purrs + ")";
        }
    }

    /* uses default name ("MaineCoon") since there's no @JsonTypeName,
     * nor did supertype specify name
     */
    public static class MaineCoon extends Cat {

        public MaineCoon() { super(); }

        public MaineCoon( String n, boolean p ) {
            super( n, p );
        }
    }

    @JsonTypeName( "persialaisKissa" )
    public static class Persian extends Cat {

        public Persian() { super(); }

        public Persian( String n, boolean p ) {
            super( n, p );
        }
    }

    public static final TypeNamesTester INSTANCE = new TypeNamesTester();

    private TypeNamesTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testSerialization( ObjectWriterTester<Animal[]> writer ) {

        // Note: need to use wrapper array just so that we can define
        // static type on serialization. If we had root static types,
        // could use those; but at the moment root type is dynamic

        assertEquals( "[{\"doggy\":{\"ageInYears\":3,\"name\":\"Spot\"}}]", writer.write( new Animal[]{new Dog( "Spot", 3 )} ) );
        assertEquals( "[{\"TypeNamesTester$MaineCoon\":{\"name\":\"Belzebub\",\"purrs\":true}}]", writer
                .write( new Animal[]{new MaineCoon( "Belzebub", true )} ) );
    }

    public void testRoundTrip( ObjectMapperTester<Animal[]> mapper ) {
        Animal[] input = new Animal[]{new Dog( "Odie", 7 ), null, new MaineCoon( "Piru", false ), new Persian( "Khomeini", true )};

        String json = mapper.write( input );
        assertEquals( "[{\"doggy\":{\"ageInYears\":7,\"name\":\"Odie\"}},null,{\"TypeNamesTester$MaineCoon\":{\"name\":\"Piru\"," +
                "" + "\"purrs\":false}},{\"persialaisKissa\":{\"name\":\"Khomeini\",\"purrs\":true}}]", json );

        Animal[] output = mapper.read( json );
        assertEquals( input.length, output.length );
        for ( int i = 0, len = input.length; i < len; ++i ) {
            assertEquals( "Entry #" + i + " differs, input = '" + json + "'", input[i], output[i] );
        }
    }

    public void testRoundTripMap( ObjectMapperTester<LinkedHashMap<String, Animal>> mapper ) {
        LinkedHashMap<String, Animal> input = new LinkedHashMap<String, Animal>();
        input.put( "venla", new MaineCoon( "Venla", true ) );
        input.put( "ama", new Dog( "Amadeus", 13 ) );

        String json = mapper.write( input );
        assertEquals( "{\"venla\":{\"TypeNamesTester$MaineCoon\":{\"name\":\"Venla\",\"purrs\":true}}," +
                "" + "\"ama\":{\"doggy\":{\"ageInYears\":13,\"name\":\"Amadeus\"}}}", json );

        LinkedHashMap<String, Animal> output = mapper.read( json );
        assertNotNull( output );
        assertEquals( input.size(), output.size() );

        // for some reason, straight comparison won't work...
        for ( String name : input.keySet() ) {
            Animal in = input.get( name );
            Animal out = output.get( name );
            if ( !in.equals( out ) ) {
                fail( "Animal in input was [" + in + "]; output not matching: [" + out + "]" );
            }
        }
    }
}

