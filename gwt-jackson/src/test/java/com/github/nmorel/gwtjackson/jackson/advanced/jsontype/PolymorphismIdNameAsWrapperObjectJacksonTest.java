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

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.PolymorphismIdNameAsWrapperObjectTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class PolymorphismIdNameAsWrapperObjectJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialize() {
        PolymorphismIdNameAsWrapperObjectTester.INSTANCE
            .testSerialize( createWriter( PolymorphismIdNameAsWrapperObjectTester.Person[].class ) );
    }

    @Test
    @Ignore("jackson can't event find the subtype, booooo")
    public void testDeserialize() {
        PolymorphismIdNameAsWrapperObjectTester.INSTANCE
            .testDeserialize( createReader( PolymorphismIdNameAsWrapperObjectTester.Person[].class ) );
    }
}
