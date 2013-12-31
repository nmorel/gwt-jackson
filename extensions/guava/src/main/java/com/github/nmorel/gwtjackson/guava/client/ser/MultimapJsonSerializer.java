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

package com.github.nmorel.gwtjackson.guava.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.common.collect.Multimap;

/**
 * Default {@link JsonSerializer} implementation for {@link Multimap}.
 *
 * @param <M> Type of the {@link Multimap}
 * @param <K> Type of the keys inside the {@link Multimap}
 * @param <V> Type of the values inside the {@link Multimap}
 *
 * @author Nicolas Morel
 */
public class MultimapJsonSerializer<M extends Multimap<K, V>, K, V> extends JsonSerializer<M> {

    /**
     * @param keySerializer {@link KeySerializer} used to serialize the keys.
     * @param valueSerializer {@link JsonSerializer} used to serialize the values.
     * @param <M> Type of the {@link Multimap}
     *
     * @return a new instance of {@link MultimapJsonSerializer}
     */
    public static <M extends Multimap<?, ?>> MultimapJsonSerializer<M, ?, ?> newInstance( KeySerializer<?> keySerializer,
                                                                                          JsonSerializer<?> valueSerializer ) {
        return new MultimapJsonSerializer( keySerializer, valueSerializer );
    }

    protected final KeySerializer<K> keySerializer;

    protected final JsonSerializer<V> valueSerializer;

    /**
     * @param keySerializer {@link KeySerializer} used to serialize the keys.
     * @param valueSerializer {@link JsonSerializer} used to serialize the values.
     */
    protected MultimapJsonSerializer( KeySerializer<K> keySerializer, JsonSerializer<V> valueSerializer ) {
        if ( null == keySerializer ) {
            throw new IllegalArgumentException( "keySerializer cannot be null" );
        }
        if ( null == valueSerializer ) {
            throw new IllegalArgumentException( "valueSerializer cannot be null" );
        }
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    @Override
    public void doSerialize( JsonWriter writer, @Nonnull M multimap, JsonSerializationContext ctx, JsonSerializerParameters params ) throws
        IOException {
        writer.beginObject();

        if ( !multimap.isEmpty() ) {

            for ( Entry<K, Collection<V>> entry : multimap.asMap().entrySet() ) {
                writer.name( keySerializer.serialize( entry.getKey(), ctx ) );
                writer.beginArray();
                for ( V value : entry.getValue() ) {
                    valueSerializer.serialize( writer, value, ctx, params );
                }
                writer.endArray();
            }
        }
        writer.endObject();
    }
}
