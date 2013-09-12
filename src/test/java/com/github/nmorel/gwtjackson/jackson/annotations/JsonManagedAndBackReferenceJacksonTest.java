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

/** @author Nicolas Morel */
public class JsonManagedAndBackReferenceJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testSimpleRefs()
    {
        JsonManagedAndBackReferenceTester.INSTANCE
            .testSimpleRefs( createDecoder( SimpleTreeNode.class ), createEncoder( SimpleTreeNode.class ) );
    }

    @Test
    public void testSimpleRefsWithGetter()
    {
        JsonManagedAndBackReferenceTester.INSTANCE
            .testSimpleRefsWithGetter( createDecoder( SimpleTreeNode2.class ), createEncoder( SimpleTreeNode2.class ) );
    }

    @Test
    public void testFullRefs()
    {
        JsonManagedAndBackReferenceTester.INSTANCE.testFullRefs( createDecoder( FullTreeNode.class ), createEncoder( FullTreeNode.class ) );
    }

    @Test
    public void testArrayOfRefs()
    {
        JsonManagedAndBackReferenceTester.INSTANCE.testArrayOfRefs( createDecoder( NodeArray.class ), createEncoder( NodeArray.class ) );
    }

    @Test
    public void testListOfRefs()
    {
        JsonManagedAndBackReferenceTester.INSTANCE.testListOfRefs( createDecoder( NodeList.class ), createEncoder( NodeList.class ) );
    }

    @Test
    public void testMapOfRefs()
    {
        JsonManagedAndBackReferenceTester.INSTANCE.testMapOfRefs( createDecoder( NodeMap.class ), createEncoder( NodeMap.class ) );
    }

    @Test
    public void testAbstract368()
    {
        JsonManagedAndBackReferenceTester.INSTANCE
            .testAbstract368( createDecoder( AbstractNode.class ), createEncoder( AbstractNode.class ) );
    }

    @Test
    public void testIssue693()
    {
        JsonManagedAndBackReferenceTester.INSTANCE.testIssue693( createDecoder( Parent.class ), createEncoder( Parent.class ) );
    }

    @Test
    public void testIssue708()
    {
        JsonManagedAndBackReferenceTester.INSTANCE.testIssue708( createDecoder( Advertisement708.class ) );
    }
}
