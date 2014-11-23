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

import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.google.gwt.core.client.GWT;

/**
 * Writes an object to JSON.
 * <p>To generate an implementation, use {@link GWT#create(Class)}.</p>
 * <p>Example : </p>
 * <pre>
 * public class Person {
 *     public String firstName, lastName;
 *     public Person(String firstName, String lastName){
 *         this.firstName = firstName;
 *         this.lastName = lastName;
 *     }
 * }
 *
 * public interface PersonWriter extends ObjectWriter&lt;Person&gt; {}
 *
 * PersonWriter writer = GWT.create(PersonWriter.class);
 * String json = writer.write(new Person("Nicolas", "Morel"));
 *
 * json ==&gt; {"firstName":"Nicolas","lastName":"Morel"}
 * </pre>
 *
 * @param <T> Type of the object to write
 *
 * @author Nicolas Morel
 */
public interface ObjectWriter<T> {

    /**
     * Writes an object to JSON.
     *
     * @param value Object to write
     *
     * @return the JSON output
     * @throws JsonSerializationException if an exception occurs while writing the output
     */
    String write( T value ) throws JsonSerializationException;

    /**
     * Writes an object to JSON.
     *
     * @param value Object to write
     * @param ctx Context for the full writing process
     *
     * @throws JsonSerializationException if an exception occurs while writing the output
     */
    String write( T value, JsonSerializationContext ctx ) throws JsonSerializationException;
}
