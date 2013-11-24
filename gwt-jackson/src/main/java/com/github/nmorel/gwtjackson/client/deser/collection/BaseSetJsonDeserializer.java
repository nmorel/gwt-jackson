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

import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link JsonDeserializer} implementation for {@link Set}.
 *
 * @param <S> {@link Set} type
 * @param <T> Type of the elements inside the {@link Set}
 *
 * @author Nicolas Morel
 */
public abstract class BaseSetJsonDeserializer<S extends Set<T>, T> extends BaseCollectionJsonDeserializer<S, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link Set}.
     */
    public BaseSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }
}
