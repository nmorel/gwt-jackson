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

package com.github.nmorel.gwtjackson.guava.client.deser;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.google.common.collect.HashMultiset;

/**
 * Default {@link JsonDeserializer} implementation for {@link HashMultiset}.
 *
 * @param <T> Type of the elements inside the {@link HashMultiset}
 *
 * @author Nicolas Morel
 */
public final class HashMultisetJsonDeserializer<T> extends BaseMultisetJsonDeserializer<HashMultiset<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link HashMultiset}.
     * @param <T> Type of the elements inside the {@link HashMultiset}
     *
     * @return a new instance of {@link HashMultisetJsonDeserializer}
     */
    public static <T> HashMultisetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new HashMultisetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link HashMultiset}.
     */
    private HashMultisetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected HashMultiset<T> newCollection() {
        return HashMultiset.create();
    }
}
