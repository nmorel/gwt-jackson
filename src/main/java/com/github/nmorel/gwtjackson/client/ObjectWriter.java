package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.exception.JsonEncodingException;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Write an object to JSON output
 *
 * @param <T> Type of the mapped object
 *
 * @author Nicolas Morel
 */
public interface ObjectWriter<T> {

    /**
     * Encodes an object into JSON output.
     *
     * @param value Object to encode
     *
     * @return the JSON output
     * @throws com.github.nmorel.gwtjackson.client.exception.JsonEncodingException if an exception occurs while encoding the output
     */
    String encode( T value ) throws JsonEncodingException;

    /**
     * Encodes an object into JSON output.
     *
     * @param writer {@link com.github.nmorel.gwtjackson.client.stream.JsonWriter} used to write the JSON output
     * @param value Object to encode
     * @param ctx Context for the full encoding process
     *
     * @throws JsonEncodingException if an exception occurs while encoding the output
     */
    void encode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws JsonEncodingException;
}
