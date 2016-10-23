package com.github.nmorel.gwtjackson.client.deser.bean;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * <p>Abstract AbstractIdentityDeserializationInfo class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
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

    /**
     * <p>Constructor for AbstractIdentityDeserializationInfo.</p>
     *
     * @param propertyName a {@link java.lang.String} object.
     * @param type a {@link java.lang.Class} object.
     * @param scope a {@link java.lang.Class} object.
     */
    protected AbstractIdentityDeserializationInfo( String propertyName, Class<?> type, Class<?> scope ) {
        this.propertyName = propertyName;
        this.type = type;
        this.scope = scope;
    }

    /** {@inheritDoc} */
    @Override
    public final String getPropertyName() {
        return propertyName;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean isProperty() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public IdKey newIdKey( Object id ) {
        return new IdKey( type, scope, id );
    }

    /** {@inheritDoc} */
    @Override
    public final Object readId( JsonReader reader, JsonDeserializationContext ctx ) {
        return getDeserializer().deserialize( reader, ctx );
    }

}
