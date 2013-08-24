package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/** @author Nicolas Morel */
public interface JsonMapper<T>
{
    T decode(String in);

    T decode(JsonReader reader);

    String encode(T value);

    void encode(JsonWriter writer, T value);
}
