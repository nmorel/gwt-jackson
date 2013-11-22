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

package com.github.nmorel.gwtjackson.client.advanced.jsontype;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithGenericsTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithGenericsTester.Animal;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithGenericsTester.ContainerWithField;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithGenericsTester.ContainerWithGetter;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonTypeWithGenericsGwtTest extends GwtJacksonTestCase {

    public interface ContainerWithGetterAnimalMapper extends ObjectMapper<ContainerWithGetter<Animal>>, ObjectMapperTester<ContainerWithGetter<Animal>> {

        static ContainerWithGetterAnimalMapper INSTANCE = GWT.create( ContainerWithGetterAnimalMapper.class );
    }

    public interface ContainerWithFieldAnimalMapper extends ObjectWriter<ContainerWithField<Animal>>,
        ObjectWriterTester<ContainerWithField<Animal>> {

        static ContainerWithFieldAnimalMapper INSTANCE = GWT.create( ContainerWithFieldAnimalMapper.class );
    }

    private JsonTypeWithGenericsTester tester = JsonTypeWithGenericsTester.INSTANCE;

    public void testWrapperWithGetter() {
        tester.testWrapperWithGetter( ContainerWithGetterAnimalMapper.INSTANCE );
    }

    public void testWrapperWithField() {
        tester.testWrapperWithField( ContainerWithFieldAnimalMapper.INSTANCE );
    }
}
