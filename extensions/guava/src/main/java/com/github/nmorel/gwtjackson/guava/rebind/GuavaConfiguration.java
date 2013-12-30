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
import com.github.nmorel.gwtjackson.guava.client.deser.BiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.EnumBiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.EnumHashBiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.HashBiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableBiMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableCollectionJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableListJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableSetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableSortedMapJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.ImmutableSortedSetJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.deser.OptionalJsonDeserializer;
import com.github.nmorel.gwtjackson.guava.client.ser.OptionalJsonSerializer;
import com.google.common.base.Optional;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;

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

        //        // Multiset
        //        type( Multiset.class );
        //        type( SortedMultiset.class );
        //        type( ImmutableMultiset.class );
        //        type( ImmutableSortedMultiset.class );
        //        type( HashMultiset.class );
        //        type( LinkedHashMultiset.class );
        //        type( TreeMultiset.class );
        //        type( EnumMultiset.class );
        //
        //        // Multimap
        //        type( Multimap.class );
        //        type( ImmutableMultimap.class );
        //
        //        type( SetMultimap.class );
        //        type( HashMultimap.class );
        //        type( LinkedHashMultimap.class );
        //        type( SortedSetMultimap.class );
        //        type( TreeMultimap.class );
        //        type( ImmutableSetMultimap.class );
        //
        //        type( ListMultimap.class );
        //        type( ArrayListMultimap.class );
        //        type( LinkedListMultimap.class );
        //        type( ImmutableListMultimap.class );
    }
}
