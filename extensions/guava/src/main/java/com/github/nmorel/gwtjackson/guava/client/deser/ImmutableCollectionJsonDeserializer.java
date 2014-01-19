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

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

/**
 * Default {@link JsonDeserializer} implementation for {@link ImmutableCollection}. The deserialization process returns an {@link
 * ImmutableList}.
 *
 * @param <T> Type of the elements inside the {@link ImmutableCollection}
 *
 * @author Nicolas Morel
 */
public final class ImmutableCollectionJsonDeserializer<T> extends BaseImmutableCollectionJsonDeserializer<ImmutableCollection<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link ImmutableCollection}.
     * @param <T> Type of the elements inside the {@link ImmutableCollection}
     *
     * @return a new instance of {@link ImmutableCollectionJsonDeserializer}
     */
    public static <T> ImmutableCollectionJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new ImmutableCollectionJsonDeserializer<T>( deserializer );
    }

    private ImmutableList.Builder<T> currentBuilder;

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link ImmutableCollection}.
     */
    private ImmutableCollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected ImmutableCollection<T> doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        try {
            currentBuilder = ImmutableList.builder();
            buildCollection( reader, ctx, params );
            return currentBuilder.build();
        } finally {
            currentBuilder = null;
        }
    }

    @Override
    protected void addToCollection( T element ) {
        currentBuilder.add( element );
    }
}
