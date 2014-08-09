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

package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.mapper.CustomIterableTester;
import com.github.nmorel.gwtjackson.shared.mapper.CustomIterableTester.MyIntegerIterable;
import com.github.nmorel.gwtjackson.shared.mapper.CustomIterableTester.Wrapper;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class CustomIterableGwtTest extends GwtJacksonTestCase {

    public interface WrapperMapper extends ObjectMapper<Wrapper>, ObjectMapperTester<Wrapper> {

        static WrapperMapper INSTANCE = GWT.create( WrapperMapper.class );
    }

    public interface MyIntegerIterableMapper extends ObjectMapper<MyIntegerIterable>, ObjectMapperTester<MyIntegerIterable> {

        static MyIntegerIterableMapper INSTANCE = GWT.create( MyIntegerIterableMapper.class );
    }

    public void testCustomIntegerIterable() {
        CustomIterableTester.INSTANCE.testCustomIntegerIterable( MyIntegerIterableMapper.INSTANCE );
    }

    public void testWrapper() {
        CustomIterableTester.INSTANCE.testWrapper( WrapperMapper.INSTANCE );
    }
}
