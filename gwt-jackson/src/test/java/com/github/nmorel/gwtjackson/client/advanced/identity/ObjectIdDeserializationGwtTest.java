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
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdParameterWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdParameterWrapperExt;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdPropertyWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdPropertyWrapperExt;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.IdentifiableCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdDeserializationTester.ListFinalPropertyId;
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

    public interface IdPropertyWrapperMapper extends ObjectReader<IdPropertyWrapper>, ObjectReaderTester<IdPropertyWrapper> {

        static IdPropertyWrapperMapper INSTANCE = GWT.create( IdPropertyWrapperMapper.class );
    }

    public interface IdParameterWrapperMapper extends ObjectReader<IdParameterWrapper>, ObjectReaderTester<IdParameterWrapper> {

        static IdParameterWrapperMapper INSTANCE = GWT.create( IdParameterWrapperMapper.class );
    }

    public interface IdentifiableCustomMapper extends ObjectReader<IdentifiableCustom>, ObjectReaderTester<IdentifiableCustom> {

        static IdentifiableCustomMapper INSTANCE = GWT.create( IdentifiableCustomMapper.class );
    }

    public interface IdPropertyWrapperExtMapper extends ObjectReader<IdPropertyWrapperExt>, ObjectReaderTester<IdPropertyWrapperExt> {

        static IdPropertyWrapperExtMapper INSTANCE = GWT.create( IdPropertyWrapperExtMapper.class );
    }

    public interface IdParameterWrapperExtMapper extends ObjectReader<IdParameterWrapperExt>, ObjectReaderTester<IdParameterWrapperExt> {

        static IdParameterWrapperExtMapper INSTANCE = GWT.create( IdParameterWrapperExtMapper.class );
    }

    public interface ListFinalPropertyIdMapper extends ObjectReader<ListFinalPropertyId>, ObjectReaderTester<ListFinalPropertyId> {

        static ListFinalPropertyIdMapper INSTANCE = GWT.create( ListFinalPropertyIdMapper.class );
    }

    private ObjectIdDeserializationTester tester = ObjectIdDeserializationTester.INSTANCE;

    public void testSimpleDeserializationClass() {
        tester.testSimpleDeserializationClass( IdentifiableMapper.INSTANCE );
    }

    public void testSimpleUUIDForClassRoundTrip() {
        tester.testSimpleUUIDForClassRoundTrip( UUIDNodeMapper.INSTANCE );
    }

    public void testSimpleDeserializationProperty() {
        tester.testSimpleDeserializationProperty( IdPropertyWrapperMapper.INSTANCE );
    }

    public void testSimpleDeserWithForwardRefs() {
        tester.testSimpleDeserWithForwardRefs( IdPropertyWrapperMapper.INSTANCE );
    }

    public void testCustomDeserializationClass() {
        tester.testCustomDeserializationClass( IdentifiableCustomMapper.INSTANCE );
    }

    public void testCustomDeserializationProperty() {
        tester.testCustomDeserializationProperty( IdPropertyWrapperExtMapper.INSTANCE );
    }

    public void testSimpleDeserializationParameter() {
        tester.testSimpleDeserializationParameter( IdParameterWrapperMapper.INSTANCE );
    }

    public void testCustomDeserializationParameter() {
        tester.testCustomDeserializationParameter( IdParameterWrapperExtMapper.INSTANCE );
    }

    public void testFinalPropertyId() {
        tester.testFinalPropertyId( ListFinalPropertyIdMapper.INSTANCE );
    }
}
