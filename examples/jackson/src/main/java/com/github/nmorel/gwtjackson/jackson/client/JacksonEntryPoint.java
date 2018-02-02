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

package com.github.nmorel.gwtjackson.jackson.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.jackson.shared.Person;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class JacksonEntryPoint implements EntryPoint {

    public static interface PersonMapper extends ObjectMapper<Person> {
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
