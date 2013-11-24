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

package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.TypeNamesTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.TypeNamesTester.Animal;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class TypeNamesJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialization() {
        TypeNamesTester.INSTANCE.testSerialization( createWriter( Animal[].class ) );
    }

    @Test
    public void testRoundTrip() {
        TypeNamesTester.INSTANCE.testRoundTrip( createMapper( Animal[].class ) );
    }

    @Test
    @Ignore( "for some reasons, jackson don't add type info. It works on original jackson test with a class extending LinkedHashMap" )
    public void testRoundTripMap() {
        TypeNamesTester.INSTANCE.testRoundTripMap( createMapper( new TypeReference<LinkedHashMap<String, Animal>>() {} ) );
    }
}
