package com.github.nmorel.gwtjackson.client.deser.map.key;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;

/**
 * Base class for all the key deserializer. It handles null values and exceptions. The rest is delegated to implementations.
 *
 * @author Nicolas Morel
 */
public abstract class KeyDeserializer<T> {

    /**
     * Deserializes a key into an object.
     *
     * @param key key to deserialize
     * @param ctx Context for the full deserialization process
     *
     * @return the deserialized object
     * @throws JsonDeserializationException if an error occurs during the deserialization
     */
    public T deserialize( String key, JsonDeserializationContext ctx ) throws JsonDeserializationException {
        try {
            if ( null == key ) {
                return null;
            }
            return doDeserialize( key, ctx );
        } catch ( JsonDeserializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( e );
        }
    }

    /**
     * Deserializes a non-null key into an object.
     *
     * @param key key to deserialize
     * @param ctx Context for the full deserialization process
     *
     * @return the deserialized object
     */
    protected abstract T doDeserialize( String key, JsonDeserializationContext ctx );
}
