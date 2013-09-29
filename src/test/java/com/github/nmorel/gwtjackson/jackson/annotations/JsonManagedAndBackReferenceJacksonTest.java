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
