/*
 * Copyright 2014 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.deser.bean;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @param <V> the type of values stored in the Map
 */
public class SimpleStringMap<V> extends JavaScriptObject {

    protected SimpleStringMap() {
    }

    /**
     * Get a value indexed by a key.
     *
     * @param key index to use for retrieval
     *
     * @return value associated to the key or {@code null} otherwise
     */
    public final native V get( String key )  /*-{
        return this[key];
    }-*/;

    /**
     * Put the value in the map at the given key. {@code key} must be a value
     * accepted by the underlying adapter; that is, a call to {@code
     * adapt(element)} produces a non-null result.
     *
     * @param key index to the value
     * @param value value to be stored
     */
    public final native void put( String key, V value )  /*-{
        this[key] = value;
    }-*/;

}
