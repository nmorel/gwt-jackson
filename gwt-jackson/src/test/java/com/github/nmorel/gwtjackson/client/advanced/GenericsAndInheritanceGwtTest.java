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
import com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester.Owner;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsAndInheritanceTester.Result;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel.
 */
public class GenericsAndInheritanceGwtTest extends GwtJacksonTestCase {

    private GenericsAndInheritanceTester tester = GenericsAndInheritanceTester.INSTANCE;

    public static interface ListResultMapper extends ObjectMapper<Result<Integer>[]>, ObjectMapperTester<Result<Integer>[]> {

        static ListResultMapper INSTANCE = GWT.create( ListResultMapper.class );
    }

    public void test() {
        tester.test( ListResultMapper.INSTANCE );
    }

    //#######

    public static interface OwnerMapper extends ObjectMapper<Owner>, ObjectMapperTester<Owner> {

        static OwnerMapper INSTANCE = GWT.create( OwnerMapper.class );
    }

    public void testMap() {
        tester.testMap( OwnerMapper.INSTANCE );
    }
}
