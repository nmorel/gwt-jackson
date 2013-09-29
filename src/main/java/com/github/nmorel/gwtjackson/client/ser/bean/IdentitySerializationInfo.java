package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;

/**
 * Contains identity informations for serialization process.
 *
 * @author Nicolas Morel
 */
public abstract class IdentitySerializationInfo<T, I> extends HasSerializer<I, JsonSerializer<I>> {

    /**
     * if we always serialize the bean as an id even for the first encounter.
     */
    private final boolean alwaysAsId;

    /**
     * Name of the property holding the identity
     */
    private final String propertyName;

    protected IdentitySerializationInfo( boolean alwaysAsId, String propertyName ) {
        this.alwaysAsId = alwaysAsId;
        this.propertyName = propertyName;
    }

    public boolean isAlwaysAsId() {
        return alwaysAsId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public abstract ObjectIdSerializer<I> getObjectId( T bean, JsonSerializationContext ctx );
}
