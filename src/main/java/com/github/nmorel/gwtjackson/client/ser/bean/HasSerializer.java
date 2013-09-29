package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;

/**
 * @author Nicolas Morel
 */
public abstract class HasSerializer<V, S extends JsonSerializer<V>> {

    private S serializer;

    protected S getSerializer( JsonEncodingContext ctx ) {
        if ( null == serializer ) {
            serializer = newSerializer( ctx );
        }
        return serializer;
    }

    protected abstract S newSerializer( JsonEncodingContext ctx );
}
