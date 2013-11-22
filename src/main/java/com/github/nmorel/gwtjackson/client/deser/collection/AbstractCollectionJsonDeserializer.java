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

import java.util.AbstractCollection;
import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link AbstractCollection}. The deserialization process returns an {@link ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link AbstractCollection}
 *
 * @author Nicolas Morel
 */
public class AbstractCollectionJsonDeserializer<T> extends BaseCollectionJsonDeserializer<AbstractCollection<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractCollection}.
     * @param <T> Type of the elements inside the {@link AbstractCollection}
     *
     * @return a new instance of {@link AbstractCollectionJsonDeserializer}
     */
    public static <T> AbstractCollectionJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new AbstractCollectionJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractCollection}.
     */
    private AbstractCollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected AbstractCollection<T> newCollection() {
        return new ArrayList<T>();
    }
}
