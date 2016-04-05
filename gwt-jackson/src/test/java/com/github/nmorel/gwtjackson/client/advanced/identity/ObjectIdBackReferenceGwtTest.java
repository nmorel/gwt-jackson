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

package com.github.nmorel.gwtjackson.client.advanced.identity;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdBackReferenceTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdBackReferenceTester.Owner;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ObjectIdBackReferenceGwtTest extends GwtJacksonTestCase {

    public interface OwnerMapper extends ObjectMapper<Owner>, ObjectMapperTester<Owner> {

        static OwnerMapper INSTANCE = GWT.create( OwnerMapper.class );
    }

    private ObjectIdBackReferenceTester tester = ObjectIdBackReferenceTester.INSTANCE;

    public void testMapper() {
        tester.testMapper( OwnerMapper.INSTANCE );
    }
}
