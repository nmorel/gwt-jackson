package com.github.nmorel.gwtjackson.client.deser.bean;

/**
 * @author Nicolas Morel
 */
public interface InstanceBuilderCallback<T> {

    void onInstanceCreated( T instance );
}
