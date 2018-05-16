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
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonCreatorDelegationTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonCreatorDelegationTester.Person;
import com.google.gwt.core.client.GWT;

/**
 * @author Nandor Elod Fekete
 *
 */
public class JsonCreatorDelegationGwtTest extends GwtJacksonTestCase {

    public interface JsonCreatorDelegationMapper extends ObjectMapper<Person[]>, ObjectMapperTester<Person[]> {

        static JsonCreatorDelegationMapper INSTANCE = GWT.create( JsonCreatorDelegationMapper.class );
    }

    private JsonCreatorDelegationTester tester = JsonCreatorDelegationTester.INSTANCE;

    public void testSerialize() {
        tester.testSerialize( JsonCreatorDelegationMapper.INSTANCE );
    }

    public void testDeserialize() {
        tester.testDeserialize( JsonCreatorDelegationMapper.INSTANCE );
    }
}
