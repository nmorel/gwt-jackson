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

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public final class WildcardTester extends AbstractTester {

    @JsonTypeInfo( use = Id.NAME, include = As.PROPERTY, property = "type" )
    @JsonSubTypes( {@Type( value = Dog.class, name = "doggy" ), @Type( value = Cat.class, name = "cat" )} )
    public static abstract class Animal {

        public String name;
    }

    public static class Dog extends Animal {

        public int boneCount;

        @JsonCreator
        public Dog( @JsonProperty( "name" ) String name, @JsonProperty( "boneCount" ) int b ) {
            super();
            this.name = name;
            boneCount = b;
        }
    }

    public static class Cat extends Animal {

        @JsonCreator
        public Cat( @JsonProperty( "name" ) String name ) {
            super();
            this.name = name;
        }
    }

    public static class SimpleWildcard {

        private List<? extends Animal> animals;

        private SimpleWildcard() {}

        public List<? extends Animal> getAnimals() {
            return animals;
        }

        public void setAnimals( List<? extends Animal> animals ) {
            this.animals = animals;
        }
    }

    public static class AnimalWildcard<T extends Animal> {

        private List<T> animals;

        private AnimalWildcard() {}

        public List<T> getAnimals() {
            return animals;
        }

        public void setAnimals( List<T> animals ) {
            this.animals = animals;
        }
    }

    public static class GenericWildcard<T> {

        private List<? extends T> generics;

        private GenericWildcard() {}

        public List<? extends T> getGenerics() {
            return generics;
        }

        public void setGenerics( List<? extends T> generics ) {
            this.generics = generics;
        }
    }

    @JsonTypeInfo( use = Id.NAME, include = As.PROPERTY, property = "type" )
    @JsonSubTypes( {@Type( value = EnumString.class, name = "string" )} )
    public static abstract class AbstractEnum<T extends Comparable<T>> {

        private TreeMap<T, String> map = new TreeMap<T, String>();

        public TreeMap<T, String> getMap() {
            return map;
        }

        public void setMap( TreeMap<T, String> map ) {
            this.map = map;
        }
    }

    public static class EnumString extends AbstractEnum<String> {
    }

    public static class BeanWithCustomEnumMap<T extends Comparable<T>> {

        private AbstractEnum<T> enumMap;

        public AbstractEnum<T> getEnumMap() {
            return enumMap;
        }

        public void setEnumMap( AbstractEnum<T> enumMap ) {
            this.enumMap = enumMap;
        }
    }

    public static final WildcardTester INSTANCE = new WildcardTester();

    private WildcardTester() {
    }

    public void testSerializeSimpleWildcard( ObjectWriterTester<SimpleWildcard> writer ) {
        SimpleWildcard bean = new SimpleWildcard();
        bean.animals = Arrays.asList( new Dog( "Bully", 120 ), new Cat( "Felix" ) );

        String expected = "{\"animals\":[" +
                "{" +
                "\"type\":\"doggy\"," +
                "\"name\":\"Bully\"," +
                "\"boneCount\":120" +
                "}," +
                "{" +
                "\"type\":\"cat\"," +
                "\"name\":\"Felix\"" +
                "}" +
                "]}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserializeSimpleWildcard( ObjectReaderTester<SimpleWildcard> reader ) {
        String input = "{\"animals\":[" +
                "{" +
                "\"type\":\"doggy\"," +
                "\"name\":\"Bully\"," +
                "\"boneCount\":120" +
                "}," +
                "{" +
                "\"type\":\"cat\"," +
                "\"name\":\"Felix\"" +
                "}" +
                "]}";

        SimpleWildcard bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( 2, bean.animals.size() );

        assertTrue( bean.animals.get( 0 ) instanceof Dog );
        Dog dog = (Dog) bean.animals.get( 0 );
        assertEquals( "Bully", dog.name );
        assertEquals( 120, dog.boneCount );

        assertTrue( bean.animals.get( 1 ) instanceof Cat );
        Cat cat = (Cat) bean.animals.get( 1 );
        assertEquals( "Felix", cat.name );
    }

    public void testSerializeAnimalWildcard( ObjectWriterTester<AnimalWildcard> writer ) {
        AnimalWildcard bean = new AnimalWildcard();
        bean.animals = Arrays.asList( new Dog( "Bully", 120 ), new Cat( "Felix" ) );

        String expected = "{\"animals\":[" +
                "{" +
                "\"type\":\"doggy\"," +
                "\"name\":\"Bully\"," +
                "\"boneCount\":120" +
                "}," +
                "{" +
                "\"type\":\"cat\"," +
                "\"name\":\"Felix\"" +
                "}" +
                "]}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserializeAnimalWildcard( ObjectReaderTester<AnimalWildcard> reader ) {
        String input = "{\"animals\":[" +
                "{" +
                "\"type\":\"doggy\"," +
                "\"name\":\"Bully\"," +
                "\"boneCount\":120" +
                "}," +
                "{" +
                "\"type\":\"cat\"," +
                "\"name\":\"Felix\"" +
                "}" +
                "]}";

        AnimalWildcard bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( 2, bean.animals.size() );

        assertTrue( bean.animals.get( 0 ) instanceof Dog );
        Dog dog = (Dog) bean.animals.get( 0 );
        assertEquals( "Bully", dog.name );
        assertEquals( 120, dog.boneCount );

        assertTrue( bean.animals.get( 1 ) instanceof Cat );
        Cat cat = (Cat) bean.animals.get( 1 );
        assertEquals( "Felix", cat.name );
    }

    public void testSerializeGenericWildcard( ObjectWriterTester<GenericWildcard<Animal>> writer ) {
        GenericWildcard<Animal> bean = new GenericWildcard<Animal>();
        bean.generics = Arrays.asList( new Dog( "Bully", 120 ), new Cat( "Felix" ) );

        String expected;
        if ( GWT.isClient() ) {
            expected = "{\"generics\":[" +
                    "{" +
                    "\"type\":\"doggy\"," +
                    "\"name\":\"Bully\"," +
                    "\"boneCount\":120" +
                    "}," +
                    "{" +
                    "\"type\":\"cat\"," +
                    "\"name\":\"Felix\"" +
                    "}" +
                    "]}";
        } else {
            expected = "{\"generics\":[" +
                    "{" +
                    "\"name\":\"Bully\"," +
                    "\"boneCount\":120" +
                    "}," +
                    "{" +
                    "\"name\":\"Felix\"" +
                    "}" +
                    "]}";
        }

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserializeGenericWildcard( ObjectReaderTester<GenericWildcard<Animal>> reader ) {
        String input = "{\"generics\":[" +
                "{" +
                "\"type\":\"doggy\"," +
                "\"name\":\"Bully\"," +
                "\"boneCount\":120" +
                "}," +
                "{" +
                "\"type\":\"cat\"," +
                "\"name\":\"Felix\"" +
                "}" +
                "]}";

        GenericWildcard<Animal> bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( 2, bean.generics.size() );

        assertTrue( bean.generics.get( 0 ) instanceof Dog );
        Dog dog = (Dog) bean.generics.get( 0 );
        assertEquals( "Bully", dog.name );
        assertEquals( 120, dog.boneCount );

        assertTrue( bean.generics.get( 1 ) instanceof Cat );
        Cat cat = (Cat) bean.generics.get( 1 );
        assertEquals( "Felix", cat.name );
    }

    public void testSerializeBeanWithCustomEnumMap( ObjectWriterTester<BeanWithCustomEnumMap<String>> writer ) {
        BeanWithCustomEnumMap<String> bean = new BeanWithCustomEnumMap<String>();
        EnumString enums = new EnumString();
        enums.getMap().put( "Hello", "World" );
        bean.setEnumMap( enums );

        String expected = "{\"enumMap\":{\"type\":\"string\",\"map\":{\"Hello\":\"World\"}}}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserializeBeanWithCustomEnumMap( ObjectReaderTester<BeanWithCustomEnumMap<String>> reader ) {
        String input = "{\"enumMap\":{\"type\":\"string\",\"map\":{\"Hello\":\"World\"}}}";

        BeanWithCustomEnumMap bean = reader.read( input );
        assertNotNull( bean );
        assertEquals( 1, bean.getEnumMap().getMap().size() );

        assertEquals( "World", bean.getEnumMap().getMap().get( "Hello" ) );
    }

}
