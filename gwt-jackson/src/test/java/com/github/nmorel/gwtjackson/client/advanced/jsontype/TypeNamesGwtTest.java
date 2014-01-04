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

package com.github.nmorel.gwtjackson.client.advanced.jsontype;

import java.util.LinkedHashMap;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.TypeNamesTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.TypeNamesTester.Animal;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class TypeNamesGwtTest extends GwtJacksonTestCase {

    public interface AnimalArrayMapper extends ObjectMapper<Animal[]>, ObjectMapperTester<Animal[]> {

        static AnimalArrayMapper INSTANCE = GWT.create( AnimalArrayMapper.class );
    }

    public interface AnimalMapMapper extends ObjectMapper<LinkedHashMap<String, Animal>>, ObjectMapperTester<LinkedHashMap<String,
            Animal>> {

        static AnimalMapMapper INSTANCE = GWT.create( AnimalMapMapper.class );
    }

    private TypeNamesTester tester = TypeNamesTester.INSTANCE;

    public void testSerialization() {
        tester.testSerialization( AnimalArrayMapper.INSTANCE );
    }

    public void testRoundTrip() {
        tester.testRoundTrip( AnimalArrayMapper.INSTANCE );
    }

    public void testRoundTripMap() {
        tester.testRoundTripMap( AnimalMapMapper.INSTANCE );
    }
}
