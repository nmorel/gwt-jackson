package com.github.nmorel.gwtjackson.client.deser.bean;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Delegate the deserialization of a subtype to a corresponding {@link AbstractBeanJsonDeserializer}
 *
 * @author Nicolas Morel
 */
public abstract class SubtypeDeserializer<T> extends HasDeserializer<T, AbstractBeanJsonDeserializer<T>> {

    public T deserializeObject( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        return getDeserializer( ctx ).deserializeObject( reader, ctx );
    }
}
