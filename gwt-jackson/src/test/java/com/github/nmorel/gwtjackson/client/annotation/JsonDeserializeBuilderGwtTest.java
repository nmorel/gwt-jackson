/*
 * Copyright 2016 Nicolas Morel
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

package com.github.nmorel.gwtjackson.client.annotation;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonDeserializeBuilderTester;
import com.github.nmorel.gwtjackson.shared.model.Animal;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonDeserializeBuilderGwtTest extends GwtJacksonTestCase {

    public interface AnimalMapper extends ObjectReader<Animal>,
            ObjectReaderTester<Animal> {

        static AnimalMapper INSTANCE = GWT.create( AnimalMapper.class );
    }

    public void testDeserialize() {
        JsonDeserializeBuilderTester.INSTANCE.testDeserialize( AnimalMapper.INSTANCE );
    }
}
