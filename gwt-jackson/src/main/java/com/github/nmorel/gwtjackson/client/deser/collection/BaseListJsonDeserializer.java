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

import java.util.List;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Base {@link JsonDeserializer} implementation for {@link List}.
 *
 * @param <L> {@link List} type
 * @param <T> Type of the elements inside the {@link List}
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseListJsonDeserializer<L extends List<T>, T> extends BaseCollectionJsonDeserializer<L, T> {

    /**
     * <p>Constructor for BaseListJsonDeserializer.</p>
     *
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link List}.
     */
    public BaseListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }
}
