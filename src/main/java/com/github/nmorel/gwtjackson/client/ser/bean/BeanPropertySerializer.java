package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Serializes a bean's property
 *
 * @author Nicolas Morel
 */
public abstract class BeanPropertySerializer<T, V> extends HasSerializer<V, JsonSerializer<V>> {

    /**
     * @param bean bean containing the property to serialize
     * @param ctx context of the serialization process
     *
     * @return the property's value
     */
    protected abstract V getValue( T bean, JsonSerializationContext ctx );

    /**
     * Serializes the property defined for this instance.
     *
     * @param writer writer
     * @param bean bean containing the property to serialize
     * @param ctx context of the serialization process
     */
    public void serialize( JsonWriter writer, T bean, JsonSerializationContext ctx ) {
        getSerializer( ctx ).serialize( writer, getValue( bean, ctx ), ctx );
    }
}
