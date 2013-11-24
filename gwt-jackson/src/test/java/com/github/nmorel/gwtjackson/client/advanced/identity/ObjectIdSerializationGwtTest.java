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
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.AlwaysContainer;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdWrapperCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdentifiableWithProp;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.TreeNode;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class ObjectIdSerializationGwtTest extends GwtJacksonTestCase {

    public interface IdentifiableMapper extends ObjectWriter<Identifiable>, ObjectWriterTester<Identifiable> {

        static IdentifiableMapper INSTANCE = GWT.create( IdentifiableMapper.class );
    }

    public interface IdWrapperMapper extends ObjectWriter<IdWrapper>, ObjectMapperTester<IdWrapper> {

        static IdWrapperMapper INSTANCE = GWT.create( IdWrapperMapper.class );
    }

    public interface IdentifiableWithPropMapper extends ObjectWriter<IdentifiableWithProp>, ObjectWriterTester<IdentifiableWithProp> {

        static IdentifiableWithPropMapper INSTANCE = GWT.create( IdentifiableWithPropMapper.class );
    }

    public interface IdWrapperCustomMapper extends ObjectWriter<IdWrapperCustom>, ObjectWriterTester<IdWrapperCustom> {

        static IdWrapperCustomMapper INSTANCE = GWT.create( IdWrapperCustomMapper.class );
    }

    public interface AlwaysContainerMapper extends ObjectWriter<AlwaysContainer>, ObjectWriterTester<AlwaysContainer> {

        static AlwaysContainerMapper INSTANCE = GWT.create( AlwaysContainerMapper.class );
    }

    public interface TreeNodeMapper extends ObjectWriter<TreeNode>, ObjectWriterTester<TreeNode> {

        static TreeNodeMapper INSTANCE = GWT.create( TreeNodeMapper.class );
    }

    private ObjectIdSerializationTester tester = ObjectIdSerializationTester.INSTANCE;

    public void testSimpleSerializationClass() {
        tester.testSimpleSerializationClass( IdentifiableMapper.INSTANCE );
    }

    public void testSimpleSerializationProperty() {
        tester.testSimpleSerializationProperty( IdWrapperMapper.INSTANCE );
    }

    public void testCustomPropertyForClass() {
        tester.testCustomPropertyForClass( IdentifiableWithPropMapper.INSTANCE );
    }

    public void testCustomPropertyViaProperty() {
        tester.testCustomPropertyViaProperty( IdWrapperCustomMapper.INSTANCE );
    }

    public void testAlwaysAsId() {
        tester.testAlwaysAsId( AlwaysContainerMapper.INSTANCE );
    }

    public void testAlwaysIdForTree() {
        tester.testAlwaysIdForTree( TreeNodeMapper.INSTANCE );
    }

    // The error is detected during generation so we can't catch it
    //    public void testInvalidProp()
    //    {
    //        tester.testInvalidProp( BrokenMapper.INSTANCE );
    //    }
}
