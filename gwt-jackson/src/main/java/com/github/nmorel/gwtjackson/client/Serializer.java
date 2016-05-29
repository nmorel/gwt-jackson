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
 */
public abstract class Serializer<T> {

    private KeySerializer<T> key;

    private JsonSerializer<T> json;

    public KeySerializer<T> key() {
        if ( null == key ) {
            key = createKeySerializer();
        }
        return key;
    }

    protected abstract KeySerializer<T> createKeySerializer();

    public JsonSerializer<T> json() {
        if ( null == json ) {
            json = createJsonSerializer();
        }
        return json;
    }

    protected abstract JsonSerializer<T> createJsonSerializer();

}
