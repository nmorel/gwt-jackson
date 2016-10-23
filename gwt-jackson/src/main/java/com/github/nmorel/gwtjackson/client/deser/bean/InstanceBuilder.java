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

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;

/**
 * <p>InstanceBuilder interface.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public interface InstanceBuilder<T> {

    /**
     * <p>newInstance</p>
     *
     * @param reader a {@link com.github.nmorel.gwtjackson.client.stream.JsonReader} object.
     * @param ctx a {@link com.github.nmorel.gwtjackson.client.JsonDeserializationContext} object.
     * @param params a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     * @param bufferedProperties a {@link java.util.Map} object.
     * @param bufferedPropertiesValues a {@link java.util.Map} object.
     * @param bufferedPropertiesValues a {@link java.util.Map} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.deser.bean.Instance} object.
     */
    Instance<T> newInstance( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params, Map<String, String> bufferedProperties,
                             Map<String, Object> bufferedPropertiesValues );

    /**
     * <p>getParametersDeserializer</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.deser.bean.SimpleStringMap} object.
     */
    SimpleStringMap<HasDeserializerAndParameters> getParametersDeserializer();

}
