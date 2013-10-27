package com.github.nmorel.gwtjackson.client.deser.bean;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public interface InstanceBuilder<T> {

    Instance<T> newInstance( JsonReader reader, JsonDeserializationContext ctx ) throws IOException;

}
