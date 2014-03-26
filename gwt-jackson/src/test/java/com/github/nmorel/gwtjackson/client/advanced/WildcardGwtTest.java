/*
 * Copyright 2014 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.Animal;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.AnimalWildcard;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.GenericWildcard;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.SimpleWildcard;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class WildcardGwtTest extends GwtJacksonTestCase {

    public interface SimpleWildcardMapper extends ObjectMapper<SimpleWildcard>, ObjectMapperTester<SimpleWildcard> {

        static SimpleWildcardMapper INSTANCE = GWT.create( SimpleWildcardMapper.class );
    }

    public interface AnimalWildcardMapper extends ObjectMapper<AnimalWildcard>, ObjectMapperTester<AnimalWildcard> {

        static AnimalWildcardMapper INSTANCE = GWT.create( AnimalWildcardMapper.class );
    }

    public interface GenericWildcardMapper extends ObjectMapper<GenericWildcard<Animal>>, ObjectMapperTester<GenericWildcard<Animal>> {

        static GenericWildcardMapper INSTANCE = GWT.create( GenericWildcardMapper.class );
    }

    private WildcardTester tester = WildcardTester.INSTANCE;

    public void testSerializeSimpleWildcard() {
        tester.testSerializeSimpleWildcard( SimpleWildcardMapper.INSTANCE );
    }

    public void testDeserializeSimpleWildcard() {
        tester.testDeserializeSimpleWildcard( SimpleWildcardMapper.INSTANCE );
    }

    // TODO not working currently
    //    public void testSerializeAnimalWildcard() {
    //        tester.testSerializeAnimalWildcard( AnimalWildcardMapper.INSTANCE );
    //    }
    //
    //    public void testDeserializeAnimalWildcard() {
    //        tester.testDeserializeAnimalWildcard( AnimalWildcardMapper.INSTANCE );
    //    }

    public void testSerializeGenericWildcard() {
        tester.testSerializeGenericWildcard( GenericWildcardMapper.INSTANCE );
    }

    public void testDeserializeGenericWildcard() {
        tester.testDeserializeGenericWildcard( GenericWildcardMapper.INSTANCE );
    }
}
