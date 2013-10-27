package com.github.nmorel.gwtjackson.client.deser.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicolas Morel
 */
public class Instance<T> {

    private final T instance;

    private final Map<String, String> bufferedProperties;

    public Instance( T instance ) {
        this.instance = instance;
        this.bufferedProperties = new HashMap<String, String>();
    }

    public Instance( T instance, Map<String, String> bufferedProperties ) {
        this.instance = instance;
        this.bufferedProperties = bufferedProperties;
    }

    public T getInstance() {
        return instance;
    }

    public Map<String, String> getBufferedProperties() {
        return bufferedProperties;
    }
}
