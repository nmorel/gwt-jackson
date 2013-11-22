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
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithEqualsTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdWithEqualsTester.Foo;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ObjectIdWithEqualsJacksonTest extends AbstractJacksonTest {

    private ObjectIdWithEqualsTester tester = ObjectIdWithEqualsTester.INSTANCE;

    @Override
    public void setUp() {
        super.setUp();
        //objectMapper.enable( com.fasterxml.jackson.databind.SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID );
    }

    @Test
    @Ignore( "Ignored because the options to use equality is only available since 2.3.0" )
    public void testSimpleEquals() {
        tester.testSimpleEquals( createMapper( Foo.class ) );
    }
}
