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

package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.google.gwt.core.client.GWT;

/**
 * Reads a JSON input and return an object
 * <p>To generate an implementation, use {@link GWT#create(Class)}.</p>
 * <p>Example : </p>
 * <pre>
 * public class Person {
 *     public String firstName, lastName;
 * }
 *
 * public interface PersonReader extends ObjectReader&lt;Person&gt; {}
 *
 * PersonReader reader = GWT.create(PersonReader.class);
 * Person person = reader.read("{\"firstName\":\"Nicolas\",\"lastName\":\"Morel\"}");
 *
 * person.firstName ==> "Nicolas"
 * person.lastName  ==> "Morel"
 * </pre>
 *
 * @param <T> Type of the read object
 *
 * @author Nicolas Morel
 */
public interface ObjectReader<T> {

    /**
     * Reads a JSON input into an object.
     *
     * @param input JSON input to read
     *
     * @return the read object
     * @throws JsonDeserializationException if an exception occurs while reading the input
     */
    T read( String input ) throws JsonDeserializationException;

    /**
     * Reads a JSON input into an object.
     *
     * @param input JSON input to read
     * @param ctx Context for the full reading process
     *
     * @return the read object
     * @throws JsonDeserializationException if an exception occurs while reading the input
     */
    T read( String input, JsonDeserializationContext ctx ) throws JsonDeserializationException;
}
