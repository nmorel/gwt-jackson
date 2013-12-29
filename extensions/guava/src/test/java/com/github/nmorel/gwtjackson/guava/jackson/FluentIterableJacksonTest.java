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
import com.github.nmorel.gwtjackson.guava.shared.FluentIterableTester;
import com.google.common.collect.FluentIterable;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class FluentIterableJacksonTest extends AbstractJacksonGuavaTest {

    @Test
    public void testSerialization() {
        FluentIterableTester.INSTANCE.testSerialization( createWriter( new TypeReference<FluentIterable<Integer>>() {} ) );
    }
}
