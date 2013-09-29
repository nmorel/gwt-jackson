package com.github.nmorel.gwtjackson.client.deser.bean;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public abstract class IdentityDeserializationInfo<T> extends HasDeserializer<T, JsonDeserializer<T>> {

    /**
     * Name of the property holding the identity
     */
    private final String propertyName;

    /**
     * Type of {@link ObjectIdGenerator} used for generating Object Id
     */
    private final Class<?> type;

    /**
     * Scope of the Object Id (may be null, to denote global)
     */
    private final Class<?> scope;

    protected IdentityDeserializationInfo( String propertyName, Class<?> type, Class<?> scope ) {
        this.propertyName = propertyName;
        this.type = type;
        this.scope = scope;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public IdKey getIdKey( JsonReader reader, JsonDeserializationContext ctx ) {
        return newIdKey( getDeserializer( ctx ).deserialize( reader, ctx ) );
    }

    public IdKey newIdKey( Object id ) {
        return new IdKey( type, scope, id );
    }
}
