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

package com.github.nmorel.gwtjackson.client.deser.collection;

import java.util.AbstractSequentialList;
import java.util.LinkedList;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link AbstractSequentialList}. The deserialization process returns a {@link
 * LinkedList}.
 *
 * @param <T> Type of the elements inside the {@link AbstractSequentialList}
 *
 * @author Nicolas Morel
 */
public class AbstractSequentialListJsonDeserializer<T> extends BaseListJsonDeserializer<AbstractSequentialList<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractSequentialList}.
     * @param <T> Type of the elements inside the {@link AbstractSequentialList}
     *
     * @return a new instance of {@link AbstractSequentialListJsonDeserializer}
     */
    public static <T> AbstractSequentialListJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new AbstractSequentialListJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractSequentialList}.
     */
    private AbstractSequentialListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected AbstractSequentialList<T> newCollection() {
        return new LinkedList<T>();
    }
}
