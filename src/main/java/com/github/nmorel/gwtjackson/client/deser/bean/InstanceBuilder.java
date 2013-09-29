package com.github.nmorel.gwtjackson.client.deser.bean;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;

/**
 * @author Nicolas Morel
 */

public interface InstanceBuilder<T> {

    T build( JsonDeserializationContext ctx );

    void addCallback( InstanceBuilderCallback<T> callback );
}
