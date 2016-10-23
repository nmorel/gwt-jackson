/*
 * Copyright 2016 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;

/**
 * Wrapper to access both key and json serializer for a type.
 *
 * @author nicolasmorel
 * @version $Id: $
 */
public abstract class Serializer<T> {

    private KeySerializer<T> key;

    private JsonSerializer<T> json;

    /**
     * <p>key</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer} object.
     */
    public KeySerializer<T> key() {
        if ( null == key ) {
            key = createKeySerializer();
        }
        return key;
    }

    /**
     * <p>createKeySerializer</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer} object.
     */
    protected abstract KeySerializer<T> createKeySerializer();

    /**
     * <p>json</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializer} object.
     */
    public JsonSerializer<T> json() {
        if ( null == json ) {
            json = createJsonSerializer();
        }
        return json;
    }

    /**
     * <p>createJsonSerializer</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializer} object.
     */
    protected abstract JsonSerializer<T> createJsonSerializer();

}
