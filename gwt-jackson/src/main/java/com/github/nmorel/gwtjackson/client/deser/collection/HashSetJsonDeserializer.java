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

import java.util.HashSet;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link HashSet}.
 *
 * @param <T> Type of the elements inside the {@link HashSet}
 *
 * @author Nicolas Morel
 */
public class HashSetJsonDeserializer<T> extends BaseSetJsonDeserializer<HashSet<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link HashSet}.
     * @param <T> Type of the elements inside the {@link HashSet}
     *
     * @return a new instance of {@link HashSetJsonDeserializer}
     */
    public static <T> HashSetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new HashSetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link HashSet}.
     */
    private HashSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected HashSet<T> newCollection() {
        return new HashSet<T>();
    }
}
