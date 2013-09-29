package com.github.nmorel.gwtjackson.client.ser.bean;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public abstract class SubtypeSerializer<T> extends HasSerializer<T, AbstractBeanJsonSerializer<T>> {

    public void serializeObject( JsonWriter writer, T value, JsonEncodingContext ctx ) throws IOException {
        getSerializer( ctx ).serializeObject( writer, value, ctx );
    }

}
