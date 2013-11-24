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

package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.AbstractNode;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.Advertisement708;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.FullTreeNode;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.NodeArray;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.NodeList;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.NodeMap;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.Parent;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.SimpleTreeNode;
import com.github.nmorel.gwtjackson.shared.annotations.JsonManagedAndBackReferenceTester.SimpleTreeNode2;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonManagedAndBackReferenceJacksonTest extends AbstractJacksonTest {

    private final JsonManagedAndBackReferenceTester tester = JsonManagedAndBackReferenceTester.INSTANCE;

    @Test
    public void testBackReferenceWithoutManaged() {
        tester.testBackReferenceWithoutManaged( createMapper( SimpleTreeNode.class ) );
    }

    @Test
    public void testSimpleRefs() {
        tester.testSimpleRefs( createMapper( SimpleTreeNode.class ) );
    }

    @Test
    public void testSimpleRefsWithGetter() {
        tester.testSimpleRefsWithGetter( createMapper( SimpleTreeNode2.class ) );
    }

    @Test
    public void testFullRefs() {
        tester.testFullRefs( createMapper( FullTreeNode.class ) );
    }

    @Test
    public void testArrayOfRefs() {
        tester.testArrayOfRefs( createMapper( NodeArray.class ) );
    }

    @Test
    public void testListOfRefs() {
        tester.testListOfRefs( createMapper( NodeList.class ) );
    }

    @Test
    public void testMapOfRefs() {
        tester.testMapOfRefs( createMapper( NodeMap.class ) );
    }

    @Test
    public void testAbstract368() {
        tester.testAbstract368( createMapper( AbstractNode.class ) );
    }

    @Test
    public void testIssue693() {
        tester.testIssue693( createMapper( Parent.class ) );
    }

    @Test
    public void testIssue708() {
        tester.testIssue708( createMapper( Advertisement708.class ) );
    }
}
