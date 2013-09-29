package com.github.nmorel.gwtjackson.client.deser.bean;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public abstract class BeanPropertyDeserializer<T, B extends InstanceBuilder<T>, V> extends HasDeserializer<V, JsonDeserializer<V>> {

    public abstract V deserialize( JsonReader reader, B builder, JsonDeserializationContext ctx );
}

