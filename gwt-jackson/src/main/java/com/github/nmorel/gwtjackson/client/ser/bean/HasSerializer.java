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

package com.github.nmorel.gwtjackson.client.ser.bean;

import com.github.nmorel.gwtjackson.client.JsonSerializer;

/**
 * Lazy initialize a {@link JsonSerializer}
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class HasSerializer<V, S extends JsonSerializer<V>> {

    private S serializer;

    /**
     * <p>Getter for the field <code>serializer</code>.</p>
     *
     * @return a S object.
     */
    protected S getSerializer() {
        if ( null == serializer ) {
            serializer = (S) newSerializer();
        }
        return serializer;
    }

    /**
     * <p>newSerializer</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializer} object.
     */
    protected abstract JsonSerializer<?> newSerializer();
}
