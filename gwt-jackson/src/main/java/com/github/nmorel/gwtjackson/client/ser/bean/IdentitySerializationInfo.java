package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;

/**
 * Contains identity informations for serialization process.
 *
 * @author Nicolas Morel
 */
public interface IdentitySerializationInfo<T> {

    /**
     * @return true if we should always serialize the bean as an identifier even if it has not been seralized yet
     */
    boolean isAlwaysAsId();

    /**
     * @return true if the identifier is also a property of the bean
     */
    boolean isProperty();

    /**
     * @return name of the identifier property
     */
    String getPropertyName();

    ObjectIdSerializer<?> getObjectId( T bean, JsonSerializationContext ctx );
}
