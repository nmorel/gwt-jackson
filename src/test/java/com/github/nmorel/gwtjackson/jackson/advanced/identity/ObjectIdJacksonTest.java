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

package com.github.nmorel.gwtjackson.jackson.advanced.identity;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester.Company;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdTester.Wrapper;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ObjectIdJacksonTest extends AbstractJacksonTest {

    private ObjectIdTester tester = ObjectIdTester.INSTANCE;

    @Test
    public void testColumnMetadata() {
        tester.testColumnMetadata( createMapper( Wrapper.class ) );
    }

    @Test
    public void testMixedRefsIssue188() {
        tester.testMixedRefsIssue188( createMapper( Company.class ) );
    }
}
