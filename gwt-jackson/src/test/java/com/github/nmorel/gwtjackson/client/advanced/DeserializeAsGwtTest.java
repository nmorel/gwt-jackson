/*
 * Copyright 2015 Nicolas Morel
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
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.client.advanced.WildcardGwtTest.GenericWildcardMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.advanced.DeserializeAsTester;
import com.github.nmorel.gwtjackson.shared.advanced.DeserializeAsTester.DeserializeAsWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.Animal;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.AnimalWildcard;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.GenericWildcard;
import com.github.nmorel.gwtjackson.shared.advanced.WildcardTester.SimpleWildcard;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class DeserializeAsGwtTest extends GwtJacksonTestCase {

    public interface DeserializeAsWrapperReader extends ObjectReader<DeserializeAsWrapper>, ObjectReaderTester<DeserializeAsWrapper> {

        static DeserializeAsWrapperReader INSTANCE = GWT.create( DeserializeAsWrapperReader.class );
    }

    private DeserializeAsTester tester = DeserializeAsTester.INSTANCE;

    public void testDeserializeAs() {
        tester.testDeserializeAs( DeserializeAsWrapperReader.INSTANCE );
    }
}
