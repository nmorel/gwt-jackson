package com.github.nmorel.gwtjackson.client.deser.bean;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Deserializes a bean's property
 *
 * @author Nicolas Morel
 */
public abstract class BeanPropertyDeserializer<T, B extends InstanceBuilder<T>, V> extends HasDeserializer<V, JsonDeserializer<V>> {

    /**
     * Deserializes the property defined for this instance.
     *
     * @param reader reader
     * @param builder instance builder
     * @param ctx context of the deserialization process
     */
    public abstract V deserialize( JsonReader reader, B builder, JsonDeserializationContext ctx );
}

