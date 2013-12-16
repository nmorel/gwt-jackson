package com.github.nmorel.gwtjackson.client.deser.bean;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * @author Nicolas Morel
 */
public class PropertyIdentityDeserializationInfo<T> implements IdentityDeserializationInfo<T> {

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

    public PropertyIdentityDeserializationInfo( String propertyName, Class<?> type, Class<?> scope ) {
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
        return true;
    }

    @Override
    public IdKey newIdKey( Object id ) {
        return new IdKey( type, scope, id );
    }

    @Override
    public final Object readId( JsonReader reader, JsonDeserializationContext ctx ) {
        throw ctx.traceError( "readId() is not supported by PropertyIdentitySerializationInfo" );
    }
}
