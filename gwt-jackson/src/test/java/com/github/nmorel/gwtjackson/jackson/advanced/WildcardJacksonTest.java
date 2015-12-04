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

package com.github.nmorel.gwtjackson.jackson.advanced;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.Animal;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.AnimalWildcard;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.BeanWithCustomEnumMap;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.GenericWildcard;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.SimpleWildcard;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class WildcardJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializeSimpleWildcard() {
        WildcardTester.INSTANCE.testSerializeSimpleWildcard( createWriter( SimpleWildcard.class ) );
    }

    @Test
    public void testDeserializeSimpleWildcard() {
        WildcardTester.INSTANCE.testDeserializeSimpleWildcard( createReader( SimpleWildcard.class ) );
    }

    @Test
    public void testSerializeAnimalWildcard() {
        WildcardTester.INSTANCE.testSerializeAnimalWildcard( createWriter( AnimalWildcard.class ) );
    }

    @Test
    public void testDeserializeAnimalWildcard() {
        WildcardTester.INSTANCE.testDeserializeAnimalWildcard( createReader( AnimalWildcard.class ) );
    }

    @Test
    public void testSerializeGenericWildcard() {
        WildcardTester.INSTANCE.testSerializeGenericWildcard( createWriter( new TypeReference<GenericWildcard<Animal>>() {} ) );
    }

    @Test
    public void testDeserializeGenericWildcard() {
        WildcardTester.INSTANCE.testDeserializeGenericWildcard( createReader( new TypeReference<GenericWildcard<Animal>>() {} ) );
    }

    @Test
    public void testSerializeBeanWithCustomEnumMap() {
        WildcardTester.INSTANCE.testSerializeBeanWithCustomEnumMap( createWriter( new TypeReference<BeanWithCustomEnumMap<String>>() {} ) );
    }

    @Test
    public void testDeserializeBeanWithCustomEnumMap() {
        WildcardTester.INSTANCE
                .testDeserializeBeanWithCustomEnumMap( createReader( new TypeReference<BeanWithCustomEnumMap<String>>() {} ) );
    }
}
