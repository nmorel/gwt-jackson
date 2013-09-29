package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
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
 * json ==> {"firstName":"Nicolas","lastName":"Morel"}
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
     * @param writer {@link JsonWriter} used to write the JSON output
     * @param value Object to write
     * @param ctx Context for the full writing process
     *
     * @throws JsonSerializationException if an exception occurs while writing the output
     */
    void write( JsonWriter writer, T value, JsonSerializationContext ctx ) throws JsonSerializationException;
}
