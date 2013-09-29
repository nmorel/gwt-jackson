package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.exception.JsonDecodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Read a JSON input and return an object
 *
 * @param <T> Type of the mapped object
 *
 * @author Nicolas Morel
 */
public interface ObjectReader<T> {

    /**
     * Decodes a JSON input into an object.
     *
     * @param input JSON input to decode
     *
     * @return the decoded object
     * @throws com.github.nmorel.gwtjackson.client.exception.JsonDecodingException if an exception occurs while decoding the input
     */
    T decode( String input ) throws JsonDecodingException;

    /**
     * Decodes a JSON input into an object.
     *
     * @param reader {@link com.github.nmorel.gwtjackson.client.stream.JsonReader} used to read the JSON input
     * @param ctx Context for the full decoding process
     *
     * @return the decoded object
     * @throws JsonDecodingException if an exception occurs while decoding the input
     */
    T decode( JsonReader reader, JsonDecodingContext ctx ) throws JsonDecodingException;
}
