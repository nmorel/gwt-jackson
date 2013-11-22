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

import java.util.AbstractSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.JsonDeserializer;

/**
 * Default {@link JsonDeserializer} implementation for {@link Set}. The deserialization process returns a {@link LinkedHashSet}.
 *
 * @param <T> Type of the elements inside the {@link AbstractSet}
 *
 * @author Nicolas Morel
 */
public final class AbstractSetJsonDeserializer<T> extends BaseSetJsonDeserializer<AbstractSet<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractSet}.
     * @param <T> Type of the elements inside the {@link AbstractSet}
     *
     * @return a new instance of {@link AbstractSetJsonDeserializer}
     */
    public static <T> AbstractSetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new AbstractSetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link AbstractSet}.
     */
    private AbstractSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected AbstractSet<T> newCollection() {
        return new LinkedHashSet<T>();
    }
}
