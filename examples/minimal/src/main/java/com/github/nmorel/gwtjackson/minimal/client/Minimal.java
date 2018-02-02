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

package com.github.nmorel.gwtjackson.minimal.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class Minimal implements EntryPoint {

    public static interface PersonMapper extends ObjectMapper<Person> {
    }

    public static class Person {

        private final String firstName;

        private final String lastName;

        @JsonCreator
        public Person(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    @Override
    public void onModuleLoad() {
        PersonMapper mapper = GWT.create(PersonMapper.class);

        String json = mapper.write(new Person("John", "Doe"));
        GWT.log(json); // > {"firstName":"John","lastName":"Doe"}

        Person person = mapper.read(json);
        GWT.log(person.getFirstName() + " " + person.getLastName()); // > John Doe
    }
}
