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

package com.github.nmorel.gwtjackson.client.ser.bean;

import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ser.map.MapJsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Serializes a bean's property
 *
 * @author Nicolas Morel
 */
public abstract class AnyGetterPropertySerializer<T> extends BeanPropertySerializer<T, Map> {

    protected abstract MapJsonSerializer newSerializer();

    public AnyGetterPropertySerializer() {
        super( null );
    }

    public void serializePropertyName( JsonWriter writer, T bean, JsonSerializationContext ctx ) {
        // no-op
    }

    /**
     * Serializes the property defined for this instance.
     *
     * @param writer writer
     * @param bean bean containing the property to serialize
     * @param ctx context of the serialization process
     */
    public void serialize( JsonWriter writer, T bean, JsonSerializationContext ctx ) {
        Map map = getValue( bean, ctx );
        if ( null != map ) {
            ((MapJsonSerializer) getSerializer()).serializeValues( writer, map, ctx, getParameters() );
        }
    }
}
