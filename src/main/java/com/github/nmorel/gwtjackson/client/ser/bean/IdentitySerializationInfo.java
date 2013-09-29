package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;

/**
 * @author Nicolas Morel
 */
public abstract class IdentitySerializationInfo<T, I> extends HasSerializer<I, JsonSerializer<I>> {

    private final boolean alwaysAsId;

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

    public abstract ObjectIdSerializer<I> getObjectId( T bean, JsonEncodingContext ctx );
}
