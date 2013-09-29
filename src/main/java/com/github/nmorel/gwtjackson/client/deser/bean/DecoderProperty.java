package com.github.nmorel.gwtjackson.client.deser.bean;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public interface DecoderProperty<T, B extends InstanceBuilder<T>> {

    Object decode( JsonReader reader, B builder, JsonDecodingContext ctx );
}

