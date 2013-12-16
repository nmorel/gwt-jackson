package com.github.nmorel.gwtjackson.client.deser.bean;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public abstract class AbstractIdentityDeserializationInfo<T, V> extends HasDeserializer<V,
    JsonDeserializer<V>> implements IdentityDeserializationInfo<T> {

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

    protected AbstractIdentityDeserializationInfo( String propertyName, Class<?> type, Class<?> scope ) {
        this.propertyName = propertyName;
        this.type = type;
        this.scope = scope;
    }

    @Override
    public final String getPropertyName() {
        return propertyName;
    }

    @Override
    public final boolean isProperty() {
        return false;
    }

    @Override
    public IdKey newIdKey( Object id ) {
        return new IdKey( type, scope, id );
    }

    @Override
    public final Object readId( JsonReader reader, JsonDeserializationContext ctx ) {
        return getDeserializer( ctx ).deserialize( reader, ctx );
    }

}
