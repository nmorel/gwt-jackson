package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;

/**
 * Base class for all the {@link Map} key serializer. It handles null values and exceptions. The rest is delegated to implementations.
 *
 * @author Nicolas Morel
 */
public abstract class KeySerializer<T> {

    /**
     * Serializes an object into a {@link String} to use as map's key.
     *
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     *
     * @return the key
     * @throws JsonSerializationException if an error occurs during the serialization
     */
    public String serialize( T value, JsonSerializationContext ctx ) throws JsonSerializationException {
        try {
            if ( null == value ) {
                return null;
            }
            return doSerialize( value, ctx );
        } catch ( JsonSerializationException e ) {
            // already logged, we just throw it
            throw e;
        } catch ( Exception e ) {
            throw ctx.traceError( value, e );
        }
    }

    /**
     * Serializes a non-null object into a {@link String} to use as map's key.
     *
     * @param value Object to serialize
     * @param ctx Context for the full serialization process
     *
     * @return the key
     */
    protected abstract String doSerialize( @Nonnull T value, JsonSerializationContext ctx );
}
