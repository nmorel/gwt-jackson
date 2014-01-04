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

package com.github.nmorel.gwtjackson.jackson.advanced;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester.GenericOneType;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester.GenericTwoType;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class GenericsJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializeString() {
        GenericsTester.INSTANCE.testSerializeString( createWriter( new TypeReference<GenericOneType<String>>() {} ) );
    }

    @Test
    public void testDeserializeString() {
        GenericsTester.INSTANCE.testDeserializeString( createReader( new TypeReference<GenericOneType<String>>() {} ) );
    }

    @Test
    public void testSerializeStringString() {
        GenericsTester.INSTANCE.testSerializeStringString( createWriter( new TypeReference<GenericTwoType<String, String>>() {} ) );
    }

    @Test
    public void testDeserializeStringString() {
        GenericsTester.INSTANCE.testDeserializeStringString( createReader( new TypeReference<GenericTwoType<String, String>>() {} ) );
    }

    @Test
    public void testSerializeIntegerString() {
        GenericsTester.INSTANCE.testSerializeIntegerString( createWriter( new TypeReference<GenericTwoType<Integer, String>>() {} ) );
    }

    @Test
    public void testDeserializeIntegerString() {
        GenericsTester.INSTANCE.testDeserializeIntegerString( createReader( new TypeReference<GenericTwoType<Integer, String>>() {} ) );
    }

    @Test
    public void testSerializeIntegerGenericString() {
        GenericsTester.INSTANCE
                .testSerializeIntegerGenericString( createWriter( new TypeReference<GenericTwoType<Integer,
                        GenericOneType<String>>>() {} ) );
    }

    @Test
    public void testDeserializeIntegerGenericString() {
        GenericsTester.INSTANCE
                .testDeserializeIntegerGenericString( createReader( new TypeReference<GenericTwoType<Integer,
                        GenericOneType<String>>>() {} ) );
    }
}
