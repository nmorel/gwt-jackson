package com.github.nmorel.gwtjackson.client.deser.bean;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;

/**
 * @author Nicolas Morel
 */

public interface InstanceBuilder<T> {

    T build( JsonDecodingContext ctx );

    void addCallback( InstanceBuilderCallback<T> callback );
}
