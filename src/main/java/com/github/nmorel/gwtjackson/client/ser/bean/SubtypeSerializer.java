package com.github.nmorel.gwtjackson.client.ser.bean;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Delegate the serialization of a subtype to a corresponding {@link AbstractBeanJsonSerializer}
 *
 * @author Nicolas Morel
 */
public abstract class SubtypeSerializer<T> extends HasSerializer<T, AbstractBeanJsonSerializer<T>> {

    public void serializeObject( JsonWriter writer, T value, JsonSerializationContext ctx ) throws IOException {
        getSerializer( ctx ).serializeObject( writer, value, ctx );
    }

}
