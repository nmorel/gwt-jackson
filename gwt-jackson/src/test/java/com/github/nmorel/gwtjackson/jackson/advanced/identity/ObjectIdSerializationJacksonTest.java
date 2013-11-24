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
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.AlwaysContainer;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.Broken;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdWrapperCustom;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.Identifiable;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.IdentifiableWithProp;
import com.github.nmorel.gwtjackson.shared.advanced.identity.ObjectIdSerializationTester.TreeNode;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class ObjectIdSerializationJacksonTest extends AbstractJacksonTest {

    private ObjectIdSerializationTester tester = ObjectIdSerializationTester.INSTANCE;

    @Test
    public void testSimpleSerializationClass() {
        tester.testSimpleSerializationClass( createMapper( Identifiable.class ) );
    }

    @Test
    public void testSimpleSerializationProperty() {
        tester.testSimpleSerializationProperty( createMapper( IdWrapper.class ) );
    }

    @Test
    public void testCustomPropertyForClass() {
        tester.testCustomPropertyForClass( createMapper( IdentifiableWithProp.class ) );
    }

    @Test
    public void testCustomPropertyViaProperty() {
        tester.testCustomPropertyViaProperty( createMapper( IdWrapperCustom.class ) );
    }

    @Test
    public void testAlwaysAsId() {
        tester.testAlwaysAsId( createMapper( AlwaysContainer.class ) );
    }

    @Test
    public void testAlwaysIdForTree() {
        tester.testAlwaysIdForTree( createMapper( TreeNode.class ) );
    }

    @Test
    public void testInvalidProp() {
        tester.testInvalidProp( createMapper( Broken.class ) );
    }
}
