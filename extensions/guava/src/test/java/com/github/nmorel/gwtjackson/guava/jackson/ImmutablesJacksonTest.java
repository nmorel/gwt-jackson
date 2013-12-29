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

package com.github.nmorel.gwtjackson.guava.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.guava.shared.ImmutablesTester;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ImmutablesJacksonTest extends AbstractJacksonGuavaTest {

    @Test
    public void testImmutableList() {
        ImmutablesTester.INSTANCE.testImmutableList( createMapper( new TypeReference<ImmutableList<Integer>>() {} ) );
    }

    @Test
    public void testImmutableSet() {
        ImmutablesTester.INSTANCE.testImmutableSet( createMapper( new TypeReference<ImmutableSet<Integer>>() {} ) );
    }

    @Test
    public void testImmutableSetFromSingle() {
        objectMapper.enable( DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY );
        objectMapper.enable( SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED );
        ImmutablesTester.INSTANCE.testImmutableSetFromSingle( createMapper( new TypeReference<ImmutableSet<String>>() {} ) );
    }

    @Test
    public void testImmutableSortedSet() {
        ImmutablesTester.INSTANCE.testImmutableSortedSet( createMapper( new TypeReference<ImmutableSortedSet<Integer>>() {} ) );
    }

    @Test
    public void testImmutableMap() {
        ImmutablesTester.INSTANCE.testImmutableMap( createMapper( new TypeReference<ImmutableMap<Integer, Boolean>>() {} ) );
    }

    @Test
    public void testImmutableSortedMap() {
        ImmutablesTester.INSTANCE.testImmutableSortedMap( createMapper( new TypeReference<ImmutableSortedMap<Integer, Boolean>>() {} ) );
    }

    @Test
    public void testImmutableBiMap() {
        ImmutablesTester.INSTANCE.testImmutableBiMap( createMapper( new TypeReference<ImmutableBiMap<Integer, Boolean>>() {} ) );
    }
}
