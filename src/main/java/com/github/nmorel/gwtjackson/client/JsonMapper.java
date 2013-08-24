package com.github.nmorel.gwtjackson.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Interface that gives access to encoding an object into JSON output or decoding a JSON input to an object.
 *
 * @param <T> Type of the mapped object
 * @author Nicolas Morel
 */
public interface JsonMapper<T>
{
    /**
     * Decodes a JSON input into an object.
     *
     * @param input JSON input to decode
     * @return the decoded object
     * @throws IOException if an exception occurs while parsing the input
     */
    T decode( String input ) throws IOException;

    /**
     * Decodes a JSON input into an object.
     *
     * @param reader {@link JsonReader} used to read the JSON input
     * @param ctx Context for the full decoding process
     * @return the decoded object
     * @throws IOException if an exception occurs while parsing the input
     */
    T decode( JsonReader reader, JsonDecodingContext ctx ) throws IOException;

    /**
     * Encodes an object into JSON output.
     *
     * @param value Object to encode
     * @return the JSON output
     * @throws IOException if an exception occurs while writing the output
     */
    String encode( T value ) throws IOException;

    /**
     * Encodes an object into JSON output.
     *
     * @param writer {@link JsonWriter} used to write the JSON output
     * @param value Object to encode
     * @param ctx Context for the full encoding process
     * @throws IOException if an exception occurs while writing the output
     */
    void encode( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException;
}
