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

package com.github.nmorel.gwtjackson.guava.client;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.guava.shared.ImmutablesTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ImmutablesGwtTest extends GwtJacksonGuavaTestCase {

    public interface ImmutableListIntegerMapper extends ObjectMapper<ImmutableList<Integer>>, ObjectMapperTester<ImmutableList<Integer>> {

        static ImmutableListIntegerMapper INSTANCE = GWT.create( ImmutableListIntegerMapper.class );
    }

    public interface ImmutableSetIntegerMapper extends ObjectMapper<ImmutableSet<Integer>>, ObjectMapperTester<ImmutableSet<Integer>> {

        static ImmutableSetIntegerMapper INSTANCE = GWT.create( ImmutableSetIntegerMapper.class );
    }

    public interface ImmutableSetStringMapper extends ObjectMapper<ImmutableSet<String>> {

        static ImmutableSetStringMapper INSTANCE = GWT.create( ImmutableSetStringMapper.class );
    }

    public interface ImmutableSortedSetIntegerMapper extends ObjectMapper<ImmutableSortedSet<Integer>>,
            ObjectMapperTester<ImmutableSortedSet<Integer>> {

        static ImmutableSortedSetIntegerMapper INSTANCE = GWT.create( ImmutableSortedSetIntegerMapper.class );
    }

    public interface ImmutableMapIntegerBooleanMapper extends ObjectMapper<ImmutableMap<Integer, Boolean>>,
            ObjectMapperTester<ImmutableMap<Integer, Boolean>> {

        static ImmutableMapIntegerBooleanMapper INSTANCE = GWT.create( ImmutableMapIntegerBooleanMapper.class );
    }

    public interface ImmutableSortedMapIntegerBooleanMapper extends ObjectMapper<ImmutableSortedMap<Integer, Boolean>>,
            ObjectMapperTester<ImmutableSortedMap<Integer, Boolean>> {

        static ImmutableSortedMapIntegerBooleanMapper INSTANCE = GWT.create( ImmutableSortedMapIntegerBooleanMapper.class );
    }

    public interface ImmutableBiMapIntegerBooleanMapper extends ObjectMapper<ImmutableBiMap<Integer, Boolean>>,
            ObjectMapperTester<ImmutableBiMap<Integer, Boolean>> {

        static ImmutableBiMapIntegerBooleanMapper INSTANCE = GWT.create( ImmutableBiMapIntegerBooleanMapper.class );
    }

    private ImmutablesTester tester = ImmutablesTester.INSTANCE;

    public void testImmutableList() {
        tester.testImmutableList( ImmutableListIntegerMapper.INSTANCE );
    }

    public void testImmutableSet() {
        tester.testImmutableSet( ImmutableSetIntegerMapper.INSTANCE );
    }

    public void testImmutableSetFromSingle() {
        tester.testImmutableSetFromSingle( createMapper( ImmutableSetStringMapper.INSTANCE, new JsonDeserializationContext.Builder()
                .acceptSingleValueAsArray( true ).build(), new JsonSerializationContext.Builder().writeSingleElemArraysUnwrapped( true )
                .build() ) );
    }

    public void testImmutableSortedSet() {
        tester.testImmutableSortedSet( ImmutableSortedSetIntegerMapper.INSTANCE );
    }

    public void testImmutableMap() {
        tester.testImmutableMap( ImmutableMapIntegerBooleanMapper.INSTANCE );
    }

    public void testImmutableSortedMap() {
        tester.testImmutableSortedMap( ImmutableSortedMapIntegerBooleanMapper.INSTANCE );
    }

    public void testImmutableBiMap() {
        tester.testImmutableBiMap( ImmutableBiMapIntegerBooleanMapper.INSTANCE );
    }
}
