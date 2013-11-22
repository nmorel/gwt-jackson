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

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Delegate the serialization of a subtype to a corresponding {@link AbstractBeanJsonSerializer}
 *
 * @author Nicolas Morel
 */
public abstract class SubtypeSerializer<T> extends HasSerializer<T, AbstractBeanJsonSerializer<T>> {

    public void serializeObject( JsonWriter writer, T value, JsonSerializationContext ctx ) throws IOException {
        getSerializer( ctx ).serializeObject( writer, value, ctx );
    }

}
