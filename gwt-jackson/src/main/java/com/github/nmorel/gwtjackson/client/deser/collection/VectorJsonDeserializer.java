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

import java.util.Vector;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link Vector}.
 *
 * @param <T> Type of the elements inside the {@link Vector}
 *
 * @author Nicolas Morel
 */
public class VectorJsonDeserializer<T> extends BaseListJsonDeserializer<Vector<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Vector}.
     * @param <T> Type of the elements inside the {@link Vector}
     *
     * @return a new instance of {@link VectorJsonDeserializer}
     */
    public static <T> VectorJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new VectorJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Vector}.
     */
    private VectorJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected Vector<T> newCollection() {
        return new Vector<T>();
    }
}
