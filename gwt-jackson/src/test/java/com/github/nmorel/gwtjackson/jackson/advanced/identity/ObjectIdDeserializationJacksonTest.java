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
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdParameterWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdParameterWrapperExt;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdPropertyWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdPropertyWrapperExt;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdentifiableCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.ListFinalPropertyId;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.UUIDNode;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ObjectIdDeserializationJacksonTest extends AbstractJacksonTest {

    private ObjectIdDeserializationTester tester = ObjectIdDeserializationTester.INSTANCE;

    @Test
    public void testSimpleDeserializationClass() {
        tester.testSimpleDeserializationClass( createMapper( Identifiable.class ) );
    }

    @Test
    public void testSimpleUUIDForClassRoundTrip() {
        tester.testSimpleUUIDForClassRoundTrip( createMapper( UUIDNode.class ) );
    }

    @Test
    public void testSimpleDeserializationProperty() {
        tester.testSimpleDeserializationProperty( createMapper( IdPropertyWrapper.class ) );
    }

    @Test
    public void testSimpleDeserWithForwardRefs() {
        tester.testSimpleDeserWithForwardRefs( createMapper( IdPropertyWrapper.class ) );
    }

    @Test
    public void testCustomDeserializationClass() {
        tester.testCustomDeserializationClass( createMapper( IdentifiableCustom.class ) );
    }

    @Test
    public void testCustomDeserializationProperty() {
        tester.testCustomDeserializationProperty( createMapper( IdPropertyWrapperExt.class ) );
    }

    @Test
    public void testSimpleDeserializationParameter() {
        tester.testSimpleDeserializationParameter( createMapper( IdParameterWrapper.class ) );
    }

    @Test
    public void testCustomDeserializationParameter() {
        tester.testCustomDeserializationParameter( createMapper( IdParameterWrapperExt.class ) );
    }

    @Test
    public void testFinalPropertyId() {
        tester.testFinalPropertyId( createMapper( ListFinalPropertyId.class ) );
    }
}
