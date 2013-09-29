package com.github.nmorel.gwtjackson.client.deser.bean;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * @author Nicolas Morel
 */
public abstract class HasDeserializer<V, S extends JsonDeserializer<V>> {

    private S deserializer;

    public S getDeserializer( JsonDeserializationContext ctx ) {
        if ( null == deserializer ) {
            deserializer = newDeserializer( ctx );
        }
        return deserializer;
    }

    protected abstract S newDeserializer( JsonDeserializationContext ctx );
}
