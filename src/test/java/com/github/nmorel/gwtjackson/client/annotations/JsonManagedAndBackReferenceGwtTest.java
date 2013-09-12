package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonMapper;
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
import com.google.gwt.core.client.GWT;

/** @author Nicolas Morel */
public class JsonManagedAndBackReferenceGwtTest extends GwtJacksonTestCase
{
    public interface SimpleTreeNodeMapper extends JsonMapper<SimpleTreeNode>
    {
        static SimpleTreeNodeMapper INSTANCE = GWT.create( SimpleTreeNodeMapper.class );
    }

    public interface SimpleTreeNode2Mapper extends JsonMapper<SimpleTreeNode2>
    {
        static SimpleTreeNode2Mapper INSTANCE = GWT.create( SimpleTreeNode2Mapper.class );
    }

    public interface FullTreeNodeMapper extends JsonMapper<FullTreeNode>
    {
        static FullTreeNodeMapper INSTANCE = GWT.create( FullTreeNodeMapper.class );
    }

    public interface NodeArrayMapper extends JsonMapper<NodeArray>
    {
        static NodeArrayMapper INSTANCE = GWT.create( NodeArrayMapper.class );
    }

    public interface NodeListMapper extends JsonMapper<NodeList>
    {
        static NodeListMapper INSTANCE = GWT.create( NodeListMapper.class );
    }

    public interface NodeMapMapper extends JsonMapper<NodeMap>
    {
        static NodeMapMapper INSTANCE = GWT.create( NodeMapMapper.class );
    }

    public interface AbstractNodeMapper extends JsonMapper<AbstractNode>
    {
        static AbstractNodeMapper INSTANCE = GWT.create( AbstractNodeMapper.class );
    }

    public interface ParentMapper extends JsonMapper<Parent>
    {
        static ParentMapper INSTANCE = GWT.create( ParentMapper.class );
    }

    public interface Advertisement708Mapper extends JsonMapper<Advertisement708>
    {
        static Advertisement708Mapper INSTANCE = GWT.create( Advertisement708Mapper.class );
    }

    private JsonManagedAndBackReferenceTester tester = JsonManagedAndBackReferenceTester.INSTANCE;

    public void testSimpleRefs()
    {
        tester.testSimpleRefs( createDecoder( SimpleTreeNodeMapper.INSTANCE ), createEncoder( SimpleTreeNodeMapper.INSTANCE ) );
    }

    public void testSimpleRefsWithGetter()
    {
        tester.testSimpleRefsWithGetter( createDecoder( SimpleTreeNode2Mapper.INSTANCE ), createEncoder( SimpleTreeNode2Mapper.INSTANCE ) );
    }

    public void testFullRefs()
    {
        tester.testFullRefs( createDecoder( FullTreeNodeMapper.INSTANCE ), createEncoder( FullTreeNodeMapper.INSTANCE ) );
    }

    public void testArrayOfRefs()
    {
        tester.testArrayOfRefs( createDecoder( NodeArrayMapper.INSTANCE ), createEncoder( NodeArrayMapper.INSTANCE ) );
    }

    public void testListOfRefs()
    {
        tester.testListOfRefs( createDecoder( NodeListMapper.INSTANCE ), createEncoder( NodeListMapper.INSTANCE ) );
    }

    public void testMapOfRefs()
    {
        tester.testMapOfRefs( createDecoder( NodeMapMapper.INSTANCE ), createEncoder( NodeMapMapper.INSTANCE ) );
    }

    public void testAbstract368()
    {
        tester.testAbstract368( createDecoder( AbstractNodeMapper.INSTANCE ), createEncoder( AbstractNodeMapper.INSTANCE ) );
    }

    public void testIssue693()
    {
        tester.testIssue693( createDecoder( ParentMapper.INSTANCE ), createEncoder( ParentMapper.INSTANCE ) );
    }

    public void testIssue708()
    {
        tester.testIssue708( createDecoder( Advertisement708Mapper.INSTANCE ) );
    }
}
