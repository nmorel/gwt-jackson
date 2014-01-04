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

package com.github.nmorel.gwtjackson.guava.client;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext.Builder;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.guava.shared.OptionalTester;
import com.github.nmorel.gwtjackson.guava.shared.OptionalTester.BeanWithOptional;
import com.github.nmorel.gwtjackson.guava.shared.OptionalTester.OptionalGenericData;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.common.base.Optional;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class OptionalGwtTest extends GwtJacksonGuavaTestCase {

    public interface BeanWithOptionalMapper extends ObjectMapper<BeanWithOptional>, ObjectMapperTester<BeanWithOptional> {

        static BeanWithOptionalMapper INSTANCE = GWT.create( BeanWithOptionalMapper.class );
    }

    public interface OptionalGenericDataMapper extends ObjectMapper<Optional<OptionalGenericData<String>>>,
            ObjectMapperTester<Optional<OptionalGenericData<String>>> {

        static OptionalGenericDataMapper INSTANCE = GWT.create( OptionalGenericDataMapper.class );
    }

    private OptionalTester tester = OptionalTester.INSTANCE;

    public void testSerialize() {
        tester.testSerialize( BeanWithOptionalMapper.INSTANCE );
    }

    public void testSerializeWithNonNullSerialization() {
        tester.testSerializeWithNonNullSerialization( createWriter( BeanWithOptionalMapper.INSTANCE, new Builder().serializeNulls( false )
                .build() ) );
    }

    public void testDeserialize() {
        tester.testDeserialize( BeanWithOptionalMapper.INSTANCE );
    }

    public void testDeserializeGeneric() {
        tester.testDeserializeGeneric( OptionalGenericDataMapper.INSTANCE );
    }

    public void testSerializeGeneric() {
        tester.testSerializeGeneric( OptionalGenericDataMapper.INSTANCE );
    }
}
