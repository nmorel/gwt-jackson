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

package com.github.nmorel.gwtjackson.guava.rebind;

import com.github.nmorel.gwtjackson.client.AbstractConfiguration;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.MapJsonSerializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ArrayListMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.BiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.EnumBiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.EnumHashBiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.EnumMultisetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.HashBiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.HashMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.HashMultisetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableBiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableCollectionJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableListJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableListMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableMultisetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableSetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableSetMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableSortedMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableSortedSetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.LinkedHashMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.LinkedHashMultisetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.LinkedListMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ListMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.MultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.MultisetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.OptionalJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.SetMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.SortedMultisetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.SortedSetMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.TreeMultimapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.TreeMultisetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.ser.MultimapJsonSerializer;
import com.github.nmorel.gwtjackson.guava.client.ser.OptionalJsonSerializer;
import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.EnumMultiset;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.TreeMultiset;

/**
 * @author Nicolas Morel
 */
public class GuavaConfiguration extends AbstractConfiguration {

    @Override
    protected void configure() {
        type( Optional.class ).serializer( OptionalJsonSerializer.class ).deserializer( OptionalJsonDeserializer.class );
        type( FluentIterable.class ).serializer( IterableJsonSerializer.class );

        // Immutable Collections
        type( ImmutableCollection.class ).serializer( IterableJsonSerializer.class ).deserializer( ImmutableCollectionJsonDeserializer
            .class );
        type( ImmutableList.class ).serializer( IterableJsonSerializer.class ).deserializer( ImmutableListJsonDeserializer.class );
        type( ImmutableSet.class ).serializer( IterableJsonSerializer.class ).deserializer( ImmutableSetJsonDeserializer.class );
        type( ImmutableSortedSet.class ).serializer( IterableJsonSerializer.class )
            .deserializer( ImmutableSortedSetJsonDeserializer.class );

        // Immutable Map
        type( ImmutableMap.class ).serializer( MapJsonSerializer.class ).deserializer( ImmutableMapJsonDeserializer.class );
        type( ImmutableSortedMap.class ).serializer( MapJsonSerializer.class ).deserializer( ImmutableSortedMapJsonDeserializer.class );

        // BiMap
        type( BiMap.class ).serializer( MapJsonSerializer.class ).deserializer( BiMapJsonDeserializer.class );
        type( ImmutableBiMap.class ).serializer( MapJsonSerializer.class ).deserializer( ImmutableBiMapJsonDeserializer.class );
        type( HashBiMap.class ).serializer( MapJsonSerializer.class ).deserializer( HashBiMapJsonDeserializer.class );
        type( EnumBiMap.class ).serializer( MapJsonSerializer.class ).deserializer( EnumBiMapJsonDeserializer.class );
        type( EnumHashBiMap.class ).serializer( MapJsonSerializer.class ).deserializer( EnumHashBiMapJsonDeserializer.class );

        // Multiset
        type( Multiset.class ).serializer( IterableJsonSerializer.class ).deserializer( MultisetJsonDeserializer.class );
        type( HashMultiset.class ).serializer( IterableJsonSerializer.class ).deserializer( HashMultisetJsonDeserializer.class );
        type( LinkedHashMultiset.class ).serializer( IterableJsonSerializer.class )
            .deserializer( LinkedHashMultisetJsonDeserializer.class );
        type( SortedMultiset.class ).serializer( IterableJsonSerializer.class ).deserializer( SortedMultisetJsonDeserializer.class );
        type( TreeMultiset.class ).serializer( IterableJsonSerializer.class ).deserializer( TreeMultisetJsonDeserializer.class );
        type( ImmutableMultiset.class ).serializer( IterableJsonSerializer.class ).deserializer( ImmutableMultisetJsonDeserializer.class );
        type( EnumMultiset.class ).serializer( IterableJsonSerializer.class ).deserializer( EnumMultisetJsonDeserializer.class );

        // Multimap
        type( Multimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( MultimapJsonDeserializer.class );

        type( ImmutableMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( ImmutableMultimapJsonDeserializer.class );
        type( ImmutableSetMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( ImmutableSetMultimapJsonDeserializer
            .class );
        type( ImmutableListMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( ImmutableListMultimapJsonDeserializer
            .class );

        type( SetMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( SetMultimapJsonDeserializer.class );
        type( HashMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( HashMultimapJsonDeserializer.class );
        type( LinkedHashMultimap.class ).serializer( MultimapJsonSerializer.class )
            .deserializer( LinkedHashMultimapJsonDeserializer.class );
        type( SortedSetMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( SortedSetMultimapJsonDeserializer.class );
        type( TreeMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( TreeMultimapJsonDeserializer.class );

        type( ListMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( ListMultimapJsonDeserializer.class );
        type( ArrayListMultimap.class ).serializer( MultimapJsonSerializer.class ).deserializer( ArrayListMultimapJsonDeserializer.class );
        type( LinkedListMultimap.class ).serializer( MultimapJsonSerializer.class )
            .deserializer( LinkedListMultimapJsonDeserializer.class );
    }
}
