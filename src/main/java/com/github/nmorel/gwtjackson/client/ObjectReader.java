package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
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
     * @param reader {@link JsonReader} used to read the JSON input
     * @param ctx Context for the full reading process
     *
     * @return the read object
     * @throws JsonDeserializationException if an exception occurs while reading the input
     */
    T read( JsonReader reader, JsonDeserializationContext ctx ) throws JsonDeserializationException;
}
