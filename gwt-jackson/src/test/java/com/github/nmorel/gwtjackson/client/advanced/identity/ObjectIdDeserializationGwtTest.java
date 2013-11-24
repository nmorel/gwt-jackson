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
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdWrapperExt;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdentifiableCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.UUIDNode;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ObjectIdDeserializationGwtTest extends GwtJacksonTestCase {

    public interface IdentifiableMapper extends ObjectReader<Identifiable>, ObjectReaderTester<Identifiable> {

        static IdentifiableMapper INSTANCE = GWT.create( IdentifiableMapper.class );
    }

    public interface UUIDNodeMapper extends ObjectMapper<UUIDNode>, ObjectMapperTester<UUIDNode> {

        static UUIDNodeMapper INSTANCE = GWT.create( UUIDNodeMapper.class );
    }

    public interface IdWrapperMapper extends ObjectReader<IdWrapper>, ObjectReaderTester<IdWrapper> {

        static IdWrapperMapper INSTANCE = GWT.create( IdWrapperMapper.class );
    }

    public interface IdentifiableCustomMapper extends ObjectReader<IdentifiableCustom>, ObjectReaderTester<IdentifiableCustom> {

        static IdentifiableCustomMapper INSTANCE = GWT.create( IdentifiableCustomMapper.class );
    }

    public interface IdWrapperExtMapper extends ObjectReader<IdWrapperExt>, ObjectReaderTester<IdWrapperExt> {

        static IdWrapperExtMapper INSTANCE = GWT.create( IdWrapperExtMapper.class );
    }

    private ObjectIdDeserializationTester tester = ObjectIdDeserializationTester.INSTANCE;

    public void testSimpleDeserializationClass() {
        tester.testSimpleDeserializationClass( IdentifiableMapper.INSTANCE );
    }

    public void testSimpleUUIDForClassRoundTrip() {
        tester.testSimpleUUIDForClassRoundTrip( UUIDNodeMapper.INSTANCE );
    }

    public void testSimpleDeserializationProperty() {
        tester.testSimpleDeserializationProperty( IdWrapperMapper.INSTANCE );
    }

    public void testSimpleDeserWithForwardRefs() {
        tester.testSimpleDeserWithForwardRefs( IdWrapperMapper.INSTANCE );
    }

    public void testCustomDeserializationClass() {
        tester.testCustomDeserializationClass( IdentifiableCustomMapper.INSTANCE );
    }

    public void testCustomDeserializationProperty() {
        tester.testCustomDeserializationProperty( IdWrapperExtMapper.INSTANCE );
    }
}
