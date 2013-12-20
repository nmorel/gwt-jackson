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

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * Deserializes a bean's property
 *
 * @author Nicolas Morel
 */
public abstract class BeanPropertyDeserializer<T, V> extends HasDeserializer<V, JsonDeserializer<V>> {

    private JsonDeserializerParameters parameters;

    protected JsonDeserializerParameters getParameters( JsonDeserializationContext ctx ) {
        if ( null == parameters ) {
            parameters = newParameters( ctx );
        }
        return parameters;
    }

    protected JsonDeserializerParameters newParameters( JsonDeserializationContext ctx ) {
        return JsonDeserializerParameters.DEFAULT;
    }

    /**
     * Deserializes the property defined for this instance.
     *
     * @param reader reader
     * @param bean bean to set the deserialized property to
     * @param ctx context of the deserialization process
     */
    public void deserialize( JsonReader reader, T bean, JsonDeserializationContext ctx ) {
        setValue( bean, getDeserializer( ctx ).deserialize( reader, ctx, getParameters( ctx ) ), ctx );
    }

    public abstract void setValue( T bean, V value, JsonDeserializationContext ctx );
}

