package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * @author Nicolas Morel
 */
public abstract class EncoderProperty<T, V> extends HasSerializer<V, JsonSerializer<V>> {

    protected abstract V getValue( T bean, JsonEncodingContext ctx );

    public void encode( JsonWriter writer, T bean, JsonEncodingContext ctx ) {
        getSerializer( ctx ).encode( writer, getValue( bean, ctx ), ctx );
    }
}
