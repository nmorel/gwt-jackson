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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nandor Elod Fekete
 *
 */
public final class JsonCreatorDelegationTester extends AbstractTester {

    @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
    @JsonSerialize
    public interface Person {
        String getName();
        ImmutablePerson withName(String name);
    }
    
    public static class ImmutablePerson implements Person {

        private final String name;
        
        private ImmutablePerson(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
        
        @Override
        public ImmutablePerson withName(String name) {
            return new ImmutablePerson(name);
        }
        
        @JsonCreator(mode = Mode.DELEGATING)
        static ImmutablePerson fromJson(MutablePerson delegate) {
            return new ImmutablePerson(delegate.name);
        }
        
        @JsonTypeInfo(use=Id.NONE)
        @JsonDeserialize
        @JsonAutoDetect(fieldVisibility = Visibility.NONE)
        static class MutablePerson implements Person {
            
            String name;
            
            @Override
            public String getName() {
                throw new UnsupportedOperationException();
            }
            public void setName(String name) {
                this.name = name;
            }
            @Override
            public ImmutablePerson withName(String name) {
                throw new UnsupportedOperationException();
            }
        } 

    }

    public static final JsonCreatorDelegationTester INSTANCE = new JsonCreatorDelegationTester();

    private JsonCreatorDelegationTester() {
    }

    public void testSerialize( ObjectWriterTester<Person[]> writer ) {
        Person[] persons = new Person[1];

        ImmutablePerson person = new ImmutablePerson("Thomas");
        persons[0] = person;

        String result = writer.write( persons );

        String expected = "[" +
                "{" +
                "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonCreatorDelegationTester$ImmutablePerson\"," +
                "\"name\":\"Thomas\"" +
                "}" +
                "]";

        assertEquals( expected, result );
    }

    public void testDeserialize( ObjectReaderTester<Person[]> reader ) {
        String input = "[" +
                "{" +
                "\"@class\":\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonCreatorDelegationTester$ImmutablePerson\"," +
                "\"name\":\"Thomas\"" +
                "}" +
                "]";

        Person[] result = reader.read( input );
        assertEquals( "Thomas", result[0].getName() );
    }

}
