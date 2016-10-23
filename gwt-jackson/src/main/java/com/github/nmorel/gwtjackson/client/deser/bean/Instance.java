/*
 * Copyright 2013 Nicolas Morel
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

import java.util.Map;

/**
 * <p>Instance class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class Instance<T> {

    private final T instance;

    private final Map<String, String> bufferedProperties;

    /**
     * <p>Constructor for Instance.</p>
     *
     * @param instance a T object.
     * @param bufferedProperties a {@link java.util.Map} object.
     */
    public Instance( T instance, Map<String, String> bufferedProperties ) {
        this.instance = instance;
        this.bufferedProperties = bufferedProperties;
    }

    /**
     * <p>Getter for the field <code>instance</code>.</p>
     *
     * @return a T object.
     */
    public T getInstance() {
        return instance;
    }

    /**
     * <p>Getter for the field <code>bufferedProperties</code>.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    public Map<String, String> getBufferedProperties() {
        return bufferedProperties;
    }
}
