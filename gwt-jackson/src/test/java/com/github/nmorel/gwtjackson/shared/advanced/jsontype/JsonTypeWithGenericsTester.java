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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

public final class JsonTypeWithGenericsTester extends AbstractTester {

    @JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "object-type")
    @JsonSubTypes({@Type(value = Dog.class, name = "doggy")})
    public static abstract class Animal {

        public String name;
    }

    public static class Dog extends Animal {

        public int boneCount;

        @JsonCreator
        public Dog( @JsonProperty("name") String name, @JsonProperty("boneCount") int b ) {
            super();
            this.name = name;
            boneCount = b;
        }
    }

    public static class ContainerWithGetter<T extends Animal> {

        private T animal;

        private ContainerWithGetter() {}

        public ContainerWithGetter( T a ) { animal = a; }

        public T getAnimal() { return animal; }

        public void setAnimal( T animal ) { this.animal = animal; }
    }

    public static class ContainerWithField<T extends Animal> {

        public T animal;

        public ContainerWithField( T a ) { animal = a; }
    }

    public static final JsonTypeWithGenericsTester INSTANCE = new JsonTypeWithGenericsTester();

    private JsonTypeWithGenericsTester() {
    }


    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testWrapperWithGetter( ObjectMapperTester<ContainerWithGetter<Animal>> mapper ) {
        Dog dog = new Dog( "Fluffy", 3 );
        String json = mapper.write( new ContainerWithGetter<Animal>( dog ) );
        if ( json.indexOf( "\"object-type\":\"doggy\"" ) < 0 ) {
            fail( "polymorphic type not kept, result == " + json + "; should contain 'object-type':'...'" );
        }

        ContainerWithGetter<Animal> bean = mapper.read( json );
        assertEquals( "Fluffy", bean.getAnimal().name );
        assertEquals( 3, ((Dog) bean.getAnimal()).boneCount );
    }

    public void testWrapperWithField( ObjectWriterTester<ContainerWithField<Animal>> writer ) {
        Dog dog = new Dog( "Fluffy", 3 );
        String json = writer.write( new ContainerWithField<Animal>( dog ) );
        if ( json.indexOf( "\"object-type\":\"doggy\"" ) < 0 ) {
            fail( "polymorphic type not kept, result == " + json + "; should contain 'object-type':'...'" );
        }
    }
}
