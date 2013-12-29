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

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.common.collect.ImmutableSortedSet;

/**
 * Default {@link JsonDeserializer} implementation for {@link ImmutableSortedSet}.
 *
 * @param <T> Type of the elements inside the {@link ImmutableSortedSet}
 *
 * @author Nicolas Morel
 */
public final class ImmutableSortedSetJsonDeserializer<T> extends BaseImmutableCollectionJsonDeserializer<ImmutableSortedSet<T>, T> {

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link ImmutableSortedSet}.
     * @param <T> Type of the elements inside the {@link ImmutableSortedSet}
     *
     * @return a new instance of {@link ImmutableSortedSetJsonDeserializer}
     */
    public static <T> ImmutableSortedSetJsonDeserializer<T> newInstance( JsonDeserializer<T> deserializer ) {
        return new ImmutableSortedSetJsonDeserializer<T>( deserializer );
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link ImmutableSortedSet}.
     */
    private ImmutableSortedSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        super( deserializer );
    }

    @Override
    protected ImmutableSortedSet<T> doDeserialize( JsonReader reader, JsonDeserializationContext ctx,
                                             JsonDeserializerParameters params ) throws IOException {
        ImmutableSortedSet.Builder<T> builder = ImmutableSortedSet.builder();
        buildCollection( reader, ctx, params, builder );
        return builder.build();
    }
}
