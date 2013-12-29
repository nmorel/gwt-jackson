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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.guava.shared.OptionalTester;
import com.github.nmorel.gwtjackson.guava.shared.OptionalTester.BeanWithOptional;
import com.github.nmorel.gwtjackson.guava.shared.OptionalTester.OptionalGenericData;
import com.google.common.base.Optional;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class OptionalJacksonTest extends AbstractJacksonGuavaTest {

    @Test
    public void testSerialize() {
        OptionalTester.INSTANCE.testSerialize( createWriter( BeanWithOptional.class ) );
    }

    @Test
    @Ignore( "jackson does not force the null value" )
    public void testSerializeWithNonNullSerialization() {
        objectMapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );
        OptionalTester.INSTANCE.testSerializeWithNonNullSerialization( createWriter( BeanWithOptional.class ) );
    }

    @Test
    public void testDeserialize() {
        OptionalTester.INSTANCE.testDeserialize( createReader( BeanWithOptional.class ) );
    }

    @Test
    public void testDeserializeGeneric() {
        OptionalTester.INSTANCE.testDeserializeGeneric( createReader( new TypeReference<Optional<OptionalGenericData<String>>>() {} ) );
    }

    @Test
    public void testSerializeGeneric() {
        OptionalTester.INSTANCE.testSerializeGeneric( createWriter( new TypeReference<Optional<OptionalGenericData<String>>>() {} ) );
    }
}
