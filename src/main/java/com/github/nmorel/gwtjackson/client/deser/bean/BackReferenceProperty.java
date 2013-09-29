package com.github.nmorel.gwtjackson.client.deser.bean;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;

/**
 * @author Nicolas Morel
 */
public interface BackReferenceProperty<T, R> {

    void setBackReference( T value, R reference, JsonDeserializationContext ctx );
}
