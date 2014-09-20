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

package com.github.nmorel.gwtjackson.shared.advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * Tester for serializing and deserializing {@link Object}.
 *
 * @author Nicolas Morel
 */
public final class ObjectTester extends AbstractTester {

    public static class ObjectWrapper {

        public Object array;

        public Object string;

        public Object decimal;

        public Object integer;

        public Object longNumber;

        public Object bool;

        public Object map;

        public Object object;

        @JsonTypeInfo( include = As.PROPERTY, use = Id.CLASS, property = "@type" )
        public Object objectWithTypeInfo;

        public Map<Object, String> mapWithObjectKey;
    }

    // will be ignored
    @JsonTypeInfo( include = As.PROPERTY, use = Id.CLASS, property = "@class" )
    public static class InnerObject {

        public String myString;

        public int myInt;
    }

    public static class Person {

        private final String firstName;

        private final String lastName;

        public Person( String firstName, String lastName ) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }

            Person person = (Person) o;

            if ( !firstName.equals( person.firstName ) ) {
                return false;
            }
            if ( !lastName.equals( person.lastName ) ) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = firstName.hashCode();
            result = 31 * result + lastName.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }

    public static final ObjectTester INSTANCE = new ObjectTester();

    private ObjectTester() {
    }

    public void testSerializeObject( ObjectWriterTester<ObjectWrapper> writer ) {
        ObjectWrapper wrapper = new ObjectWrapper();
        wrapper.array = new ArrayList<String>( Arrays.asList( "Hello", "World", "!" ) );
        wrapper.string = "Dumb";
        wrapper.decimal = 0.0148787d;
        wrapper.integer = 154514;
        wrapper.longNumber = 1545147777777777777l;
        wrapper.bool = true;

        Map<String, Object> objectMap = new LinkedHashMap<String, Object>();
        objectMap.put( "first", "Dumb" );
        objectMap.put( "second", "and" );
        objectMap.put( "third", "Dumber" );
        wrapper.map = objectMap;

        InnerObject inner = new InnerObject();
        inner.myString = "a super string";
        inner.myInt = 168555;
        wrapper.object = inner;
        wrapper.objectWithTypeInfo = inner;

        Map<Object, String> mapWithObjectKey = new LinkedHashMap<Object, String>();
        mapWithObjectKey.put( new Person( "Steve", "Rogers" ), "Captain America" );
        mapWithObjectKey.put( new Person( "Anthony", "Stark" ), "Iron Man" );
        mapWithObjectKey.put( "Thor", "Thor" );
        mapWithObjectKey.put( new Person( "Peter", "Parker" ), "Spider-Man" );
        wrapper.mapWithObjectKey = mapWithObjectKey;

        String expected = "{" +
                "\"array\":[\"Hello\",\"World\",\"!\"]," +
                "\"string\":\"Dumb\"," +
                "\"decimal\":0.0148787," +
                "\"integer\":154514," +
                "\"longNumber\":1545147777777777777," +
                "\"bool\":true," +
                "\"map\":{\"first\":\"Dumb\",\"second\":\"and\",\"third\":\"Dumber\"}," +
                "\"object\":{\"myString\":\"a super string\",\"myInt\":168555}," +
                "\"objectWithTypeInfo\":{" +
                "\"@type\":\"com.github.nmorel.gwtjackson.shared.advanced.ObjectTester$InnerObject\"," +
                "\"myString\":\"a super string\"," +
                "\"myInt\":168555" +
                "}," +
                "\"mapWithObjectKey\":{" +
                "\"Steve Rogers\":\"Captain America\"," +
                "\"Anthony Stark\":\"Iron Man\"," +
                "\"Thor\":\"Thor\"," +
                "\"Peter Parker\":\"Spider-Man\"" +
                "}" +
                "}";

        assertEquals( expected, writer.write( wrapper ) );
    }

    public void testDeserializeObject( ObjectReaderTester<ObjectWrapper> reader ) {
        String input = "{" +
                "\"array\":[\"Hello\",\"World\",\"!\"]," +
                "\"string\":\"Dumb\"," +
                "\"decimal\":0.0148787," +
                "\"integer\":154514," +
                "\"longNumber\":1545147777777777777," +
                "\"bool\":true," +
                "\"map\":{\"first\":\"Dumb\",\"second\":\"and\",\"third\":\"Dumber\"}," +
                "\"object\":{\"myString\":\"a super string\",\"myInt\":168555}," +
                "\"objectWithTypeInfo\":{" +
                "\"@type\":\"com.github.nmorel.gwtjackson.shared.advanced.ObjectTester$InnerObject\"," +
                "\"myString\":\"a super string\"," +
                "\"myInt\":168555" +
                "}," +
                "\"mapWithObjectKey\":{" +
                "\"Steve Rogers\":\"Captain America\"," +
                "\"Anthony Stark\":\"Iron Man\"," +
                "\"Thor\":\"Thor\"," +
                "\"Peter Parker\":\"Spider-Man\"" +
                "}" +
                "}";

        ObjectWrapper bean = reader.read( input );
        assertNotNull( bean );

        assertEquals( Arrays.asList( "Hello", "World", "!" ), bean.array );
        assertEquals( "Dumb", bean.string );
        assertEquals( new Double( 0.0148787d ), bean.decimal );
        assertEquals( new Integer( 154514 ), bean.integer );
        assertEquals( new Long( 1545147777777777777l ), bean.longNumber );
        assertTrue( (Boolean) bean.bool );

        Map<String, Object> map = (Map<String, Object>) bean.map;
        assertEquals( 3, map.size() );
        assertEquals( "Dumb", map.get( "first" ) );
        assertEquals( "and", map.get( "second" ) );
        assertEquals( "Dumber", map.get( "third" ) );

        Map<String, Object> object = (Map<String, Object>) bean.object;
        assertEquals( 2, object.size() );
        assertEquals( "a super string", object.get( "myString" ) );
        assertEquals( 168555, object.get( "myInt" ) );

        InnerObject objectWithTypeInfo = (InnerObject) bean.objectWithTypeInfo;
        assertEquals( "a super string", objectWithTypeInfo.myString );
        assertEquals( 168555, objectWithTypeInfo.myInt );

        Map<Object, String> mapWithObjectKey = bean.mapWithObjectKey;
        assertEquals( 4, mapWithObjectKey.size() );
        assertEquals( "Captain America", mapWithObjectKey.get( "Steve Rogers" ) );
        assertEquals( "Iron Man", mapWithObjectKey.get( "Anthony Stark" ) );
        assertEquals( "Thor", mapWithObjectKey.get( "Thor" ) );
        assertEquals( "Spider-Man", mapWithObjectKey.get( "Peter Parker" ) );
    }

}
