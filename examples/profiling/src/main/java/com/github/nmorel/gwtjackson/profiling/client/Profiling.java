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

package com.github.nmorel.gwtjackson.profiling.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class Profiling implements EntryPoint {

    public static interface PersonMapper extends ObjectMapper<Person> {}

    public static class Person {

        private String firstName;

        private String lastName;

        private List<Person> childs;

        public Person() {
        }

        public Person( String firstName, String lastName, Person... childs ) {
            this.firstName = firstName;
            this.lastName = lastName;
            if ( null == childs || childs.length == 0 ) {
                this.childs = Collections.emptyList();
            } else {
                this.childs = Arrays.asList( childs );
            }
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName( String firstName ) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName( String lastName ) {
            this.lastName = lastName;
        }

        public List<Person> getChilds() {
            return childs;
        }

        public void setChilds( List<Person> childs ) {
            this.childs = childs;
        }
    }

    @Override
    public void onModuleLoad() {
        PersonMapper mapper = GWT.create( PersonMapper.class );

        String json = mapper
                .write( new Person( "John", "Doe", new Person( "Jane", "Doe" ), new Person( "Billy", "Doe", new Person( "Lily",
                        "Doe" ) ) ) );
        GWT.log( json ); // > {"firstName":"John","lastName":"Doe"}

        Person person = mapper.read( json );
        GWT.log( person.getFirstName() + " " + person.getLastName() ); // > John Doe
    }
}
