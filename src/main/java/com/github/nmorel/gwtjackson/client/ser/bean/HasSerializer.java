package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;

/**
 * Lazy initialize a {@link JsonSerializer}
 *
 * @author Nicolas Morel
 */
public abstract class HasSerializer<V, S extends JsonSerializer<V>> {

    private S serializer;

    protected S getSerializer( JsonSerializationContext ctx ) {
        if ( null == serializer ) {
            serializer = newSerializer( ctx );
        }
        return serializer;
    }

    protected abstract S newSerializer( JsonSerializationContext ctx );
}
