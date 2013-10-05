package com.github.nmorel.gwtjackson.shared.annotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * Test from jackson-databind and adapted for the project
 */
public class JsonManagedAndBackReferenceTester extends AbstractTester {
    /*
    /**********************************************************
    /* Test classes
    /**********************************************************
     */

    /**
     * First, a simple 'tree': just parent/child linkage
     */
    public static class SimpleTreeNode {

        public String name;

        // Reference back to parent; reference, ignored during ser,
        // re-constructed during deser
        @JsonBackReference
        public SimpleTreeNode parent;

        // Reference that is serialized normally during ser, back
        // reference within pointed-to instance assigned to point to
        // referring bean ("this")
        @JsonManagedReference
        public SimpleTreeNode child;

        public SimpleTreeNode() {
            this( null );
        }

        public SimpleTreeNode( String n ) {
            name = n;
        }
    }

    public static class SimpleTreeNode2 {

        public String name;

        private SimpleTreeNode2 parent;

        private SimpleTreeNode2 child;

        public SimpleTreeNode2() {
            this( null );
        }

        public SimpleTreeNode2( String n ) {
            name = n;
        }

        @JsonBackReference
        public SimpleTreeNode2 getParent() {
            return parent;
        }

        public void setParent( SimpleTreeNode2 p ) {
            parent = p;
        }

        @JsonManagedReference
        public SimpleTreeNode2 getChild() {
            return child;
        }

        public void setChild( SimpleTreeNode2 c ) {
            child = c;
        }
    }

    /**
     * Then nodes with two separate linkages; parent/child and prev/next-sibling
     */
    public static class FullTreeNode {

        public String name;

        // parent-child links
        @JsonBackReference("parent")
        public FullTreeNode parent;

        @JsonManagedReference("parent")
        public FullTreeNode firstChild;

        // sibling-links
        @JsonManagedReference("sibling")
        public FullTreeNode next;

        @JsonBackReference("sibling")
        protected FullTreeNode prev;

        public FullTreeNode() {
            this( null );
        }

        public FullTreeNode( String name ) {
            this.name = name;
        }
    }

    /**
     * Class for testing managed references via arrays
     */
    public static class NodeArray {

        @JsonManagedReference("arr")
        public ArrayNode[] nodes;
    }

    public static class ArrayNode {

        public String name;

        @JsonBackReference("arr")
        public NodeArray parent;

        public ArrayNode() {
            this( null );
        }

        public ArrayNode( String n ) {
            name = n;
        }
    }

    /**
     * Class for testing managed references via Collections
     */
    public static class NodeList {

        @JsonManagedReference
        public List<NodeForList> nodes;
    }

    public static class NodeForList {

        public String name;

        @JsonBackReference
        public NodeList parent;

        public NodeForList() {
            this( null );
        }

        public NodeForList( String n ) {
            name = n;
        }
    }

    public static class NodeMap {

        @JsonManagedReference
        public Map<String, NodeForMap> nodes;
    }

    public static class NodeForMap {

        public String name;

        @JsonBackReference
        public NodeMap parent;

        public NodeForMap() {
            this( null );
        }

        public NodeForMap( String n ) {
            name = n;
        }
    }

    public static class Parent {

        @JsonManagedReference
        private final List<Child> children = new ArrayList<Child>();

        public List<Child> getChildren() {
            return children;
        }

        public void addChild( Child child ) {
            children.add( child );
            child.setParent( this );
        }
    }

    public static class Child {

        private final String value; // So that the bean is not empty of properties

        private Parent parent;

        public Child( @JsonProperty("value") String value ) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @JsonBackReference
        public Parent getParent() {
            return parent;
        }

        public void setParent( Parent parent ) {
            this.parent = parent;
        }
    }

    // [JACKSON-368]

    @JsonTypeInfo(use = Id.NAME)
    @JsonSubTypes({@JsonSubTypes.Type(ConcreteNode.class)})
    public static abstract class AbstractNode {

        public String id;

        @JsonManagedReference
        public AbstractNode next;

        @JsonBackReference
        public AbstractNode prev;
    }

    @JsonTypeName("concrete")
    public static class ConcreteNode extends AbstractNode {

        public ConcreteNode() {
        }

        public ConcreteNode( String id ) {
            this.id = id;
        }
    }

    // [JACKSON-708]
    public static class Model708 {}

    public static class Advertisement708 extends Model708 {

        public String title;

        @JsonManagedReference
        public List<Photo708> photos;
    }

    public static class Photo708 extends Model708 {

        public int id;

        @JsonBackReference
        public Advertisement708 advertisement;
    }

    public static final JsonManagedAndBackReferenceTester INSTANCE = new JsonManagedAndBackReferenceTester();

    private JsonManagedAndBackReferenceTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */
    public void testBackReferenceWithoutManaged( ObjectMapperTester<SimpleTreeNode> mapper ) {
        SimpleTreeNode root = new SimpleTreeNode( "root" );
        SimpleTreeNode child = new SimpleTreeNode( "kid" );
        root.child = child;
        child.parent = root;

        String json = mapper.write( child );
        assertEquals( "{\"name\":\"kid\",\"child\":null}", json );

        SimpleTreeNode resultNode = mapper.read( json );
        assertEquals( "kid", resultNode.name );
        assertNull( resultNode.parent );
        assertNull( resultNode.child );
    }

    public void testSimpleRefs( ObjectMapperTester<SimpleTreeNode> mapper ) {
        SimpleTreeNode root = new SimpleTreeNode( "root" );
        SimpleTreeNode child = new SimpleTreeNode( "kid" );
        root.child = child;
        child.parent = root;

        String json = mapper.write( root );
        assertEquals( "{\"name\":\"root\",\"child\":{\"name\":\"kid\",\"child\":null}}", json );

        SimpleTreeNode resultNode = mapper.read( json );
        assertEquals( "root", resultNode.name );
        SimpleTreeNode resultChild = resultNode.child;
        assertNotNull( resultChild );
        assertEquals( "kid", resultChild.name );
        assertSame( resultChild.parent, resultNode );
    }

    // [JACKSON-693]
    public void testSimpleRefsWithGetter( ObjectMapperTester<SimpleTreeNode2> mapper ) {
        SimpleTreeNode2 root = new SimpleTreeNode2( "root" );
        SimpleTreeNode2 child = new SimpleTreeNode2( "kid" );
        root.child = child;
        child.parent = root;

        String json = mapper.write( root );
        assertEquals( "{\"name\":\"root\",\"child\":{\"name\":\"kid\",\"child\":null}}", json );

        SimpleTreeNode2 resultNode = mapper.read( json );
        assertEquals( "root", resultNode.name );
        SimpleTreeNode2 resultChild = resultNode.child;
        assertNotNull( resultChild );
        assertEquals( "kid", resultChild.name );
        assertSame( resultChild.parent, resultNode );
    }

    public void testFullRefs( ObjectMapperTester<FullTreeNode> mapper ) {
        FullTreeNode root = new FullTreeNode( "root" );
        FullTreeNode child1 = new FullTreeNode( "kid1" );
        FullTreeNode child2 = new FullTreeNode( "kid2" );
        root.firstChild = child1;
        child1.parent = root;
        child1.next = child2;
        child2.prev = child1;

        String json = mapper.write( root );
        assertEquals( "{\"name\":\"root\",\"firstChild\":{\"name\":\"kid1\",\"firstChild\":null,\"next\":{\"name\":\"kid2\"," +
            "\"firstChild\":null,\"next\":null}},\"next\":null}", json );

        FullTreeNode resultNode = mapper.read( json );
        assertEquals( "root", resultNode.name );
        FullTreeNode resultChild = resultNode.firstChild;
        assertNotNull( resultChild );
        assertEquals( "kid1", resultChild.name );
        assertSame( resultChild.parent, resultNode );

        // and then sibling linkage
        assertNull( resultChild.prev );
        FullTreeNode resultChild2 = resultChild.next;
        assertNotNull( resultChild2 );
        assertEquals( "kid2", resultChild2.name );
        assertSame( resultChild, resultChild2.prev );
        assertNull( resultChild2.next );
    }

    public void testArrayOfRefs( ObjectMapperTester<NodeArray> mapper ) {
        NodeArray root = new NodeArray();
        ArrayNode node1 = new ArrayNode( "a" );
        ArrayNode node2 = new ArrayNode( "b" );
        root.nodes = new ArrayNode[]{node1, node2};

        String json = mapper.write( root );
        assertEquals( "{\"nodes\":[{\"name\":\"a\"},{\"name\":\"b\"}]}", json );

        NodeArray result = mapper.read( json );
        ArrayNode[] kids = result.nodes;
        assertNotNull( kids );
        assertEquals( 2, kids.length );
        assertEquals( "a", kids[0].name );
        assertEquals( "b", kids[1].name );
        assertSame( result, kids[0].parent );
        assertSame( result, kids[1].parent );
    }

    public void testListOfRefs( ObjectMapperTester<NodeList> mapper ) {
        NodeList root = new NodeList();
        NodeForList node1 = new NodeForList( "a" );
        NodeForList node2 = new NodeForList( "b" );
        root.nodes = Arrays.asList( node1, node2 );

        String json = mapper.write( root );
        assertEquals( "{\"nodes\":[{\"name\":\"a\"},{\"name\":\"b\"}]}", json );

        NodeList result = mapper.read( json );
        List<NodeForList> kids = result.nodes;
        assertNotNull( kids );
        assertEquals( 2, kids.size() );
        assertEquals( "a", kids.get( 0 ).name );
        assertEquals( "b", kids.get( 1 ).name );
        assertSame( result, kids.get( 0 ).parent );
        assertSame( result, kids.get( 1 ).parent );
    }

    public void testMapOfRefs( ObjectMapperTester<NodeMap> mapper ) {
        NodeMap root = new NodeMap();
        NodeForMap node1 = new NodeForMap( "a" );
        NodeForMap node2 = new NodeForMap( "b" );
        Map<String, NodeForMap> nodes = new HashMap<String, NodeForMap>();
        nodes.put( "a1", node1 );
        nodes.put( "b2", node2 );
        root.nodes = nodes;

        String json = mapper.write( root );
        assertEquals( "{\"nodes\":{\"a1\":{\"name\":\"a\"},\"b2\":{\"name\":\"b\"}}}", json );

        NodeMap result = mapper.read( json );
        Map<String, NodeForMap> kids = result.nodes;
        assertNotNull( kids );
        assertEquals( 2, kids.size() );
        assertNotNull( kids.get( "a1" ) );
        assertNotNull( kids.get( "b2" ) );
        assertEquals( "a", kids.get( "a1" ).name );
        assertEquals( "b", kids.get( "b2" ).name );
        assertSame( result, kids.get( "a1" ).parent );
        assertSame( result, kids.get( "b2" ).parent );
    }

    // for [JACKSON-368]
    public void testAbstract368( ObjectMapperTester<AbstractNode> mapper ) {
        AbstractNode parent = new ConcreteNode( "p" );
        AbstractNode child = new ConcreteNode( "c" );
        parent.next = child;
        child.prev = parent;

        // serialization ought to be ok
        String json = mapper.write( parent );
        assertEquals( "{\"@type\":\"concrete\",\"id\":\"p\",\"next\":{\"@type\":\"concrete\",\"id\":\"c\",\"next\":null}}", json );

        AbstractNode root = mapper.read( json );

        assertEquals( ConcreteNode.class, root.getClass() );
        assertEquals( "p", root.id );
        assertNull( root.prev );
        AbstractNode leaf = root.next;
        assertNotNull( leaf );
        assertEquals( "c", leaf.id );
        assertSame( root, leaf.prev );
    }

    public void testIssue693( ObjectMapperTester<Parent> mapper ) {
        Parent parent = new Parent();
        parent.addChild( new Child( "foo" ) );
        parent.addChild( new Child( "bar" ) );

        String json = mapper.write( parent );
        assertEquals( "{\"children\":[{\"value\":\"foo\"},{\"value\":\"bar\"}]}", json );

        Parent value = mapper.read( json );
        for ( Child child : value.children ) {
            assertEquals( value, child.getParent() );
        }
    }

    public void testIssue708( ObjectMapperTester<Advertisement708> mapper ) {
        Advertisement708 ad = mapper.read( "{\"title\":\"Hroch\",\"photos\":[{\"id\":3}]}" );
        assertNotNull( ad );
    }
}
