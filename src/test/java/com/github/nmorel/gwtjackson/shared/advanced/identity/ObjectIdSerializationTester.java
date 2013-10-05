package com.github.nmorel.gwtjackson.shared.advanced.identity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * Test from jackson-databind and adapted for the project
 */
public final class ObjectIdSerializationTester extends AbstractTester {

    @JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id" )
    public static class Identifiable {

        public int value;

        public Identifiable next;

        public Identifiable() {
            this( 0 );
        }

        public Identifiable( int v ) {
            value = v;
        }
    }

    @JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "customId" )
    public static class IdentifiableWithProp {

        public int value;

        // Property that contains Object Id to use
        public int customId;

        public IdentifiableWithProp next;

        public IdentifiableWithProp() {
            this( 0, 0 );
        }

        public IdentifiableWithProp( int id, int value ) {
            this.customId = id;
            this.value = value;
        }
    }

    // For property reference, need another class:

    public static class IdWrapper {

        @JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id" )
        public ValueNode node;

        public IdWrapper() {
        }

        public IdWrapper( int v ) {
            node = new ValueNode( v );
        }
    }

    public static class ValueNode {

        public int value;

        public IdWrapper next;

        public ValueNode() {
            this( 0 );
        }

        public ValueNode( int v ) {
            value = v;
        }
    }

    // Similarly for property-ref via property:

    public static class IdWrapperCustom {

        @JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
        public ValueNodeCustom node;

        public IdWrapperCustom() {
        }

        public IdWrapperCustom( int id, int value ) {
            node = new ValueNodeCustom( id, value );
        }
    }

    public static class ValueNodeCustom {

        public int value;

        public IdWrapperCustom next;

        private int id;

        public ValueNodeCustom() {
            this( 0, 0 );
        }

        public ValueNodeCustom( int id, int value ) {
            this.id = id;
            this.value = value;
        }

        public int getId() {
            return id;
        }
    }

    @JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id" )
    public static class AlwaysAsId {

        public int value;

        public AlwaysAsId() {
            this( 0 );
        }

        public AlwaysAsId( int v ) {
            value = v;
        }
    }

    // For [https://github.com/FasterXML/jackson-annotations/issues/4]
    @JsonPropertyOrder( alphabetic = true )
    public static class AlwaysContainer {

        @JsonIdentityReference( alwaysAsId = true )
        public AlwaysAsId a = new AlwaysAsId( 13 );

        @JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id" )
        @JsonIdentityReference( alwaysAsId = true )
        public Value b = new Value();
    }

    public static class Value {

        public int x = 3;
    }

    @JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
    public static class TreeNode {

        public int id;

        public String name;

        @JsonIdentityReference( alwaysAsId = true )
        public TreeNode parent;

        // children serialized with ids if need be
        public TreeNode child;

        public TreeNode() {
        }

        public TreeNode( TreeNode p, int id, String name ) {
            parent = p;
            this.id = id;
            this.name = name;
        }
    }

    // // Let's also have one 'broken' test

    // no "id" property
    @JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
    public static class Broken {

        public int value;

        public int customId;
    }

    /*
    /**********************************************************
    /* Constantes
    /**********************************************************
     */
    public static final ObjectIdSerializationTester INSTANCE = new ObjectIdSerializationTester();

    private final static String EXP_SIMPLE_INT_CLASS = "{\"id\":1,\"value\":13,\"next\":1}";

    // Bit more complex, due to extra wrapping etc:
    private final static String EXP_SIMPLE_INT_PROP = "{\"node\":{\"@id\":1,\"value\":7,\"next\":{\"node\":1}}}";

    private final static String EXP_CUSTOM_PROP = "{\"customId\":123,\"value\":-19,\"next\":123}";

    private final static String EXP_CUSTOM_PROP_VIA_REF = "{\"node\":{\"id\":123,\"value\":7,\"next\":{\"node\":123}}}";

    private ObjectIdSerializationTester() {
    }

    /*
    /*****************************************************
    /* Unit tests
    /*****************************************************
     */

    public void testSimpleSerializationClass( ObjectWriterTester<Identifiable> writer ) {
        Identifiable src = new Identifiable( 13 );
        src.next = src;

        // First, serialize:
        String json = writer.write( src );
        assertEquals( EXP_SIMPLE_INT_CLASS, json );

        // and ensure that state is cleared in-between as well:
        json = writer.write( src );
        assertEquals( EXP_SIMPLE_INT_CLASS, json );
    }

    public void testSimpleSerializationProperty( ObjectWriterTester<IdWrapper> writer ) {
        IdWrapper src = new IdWrapper( 7 );
        src.node.next = src;

        // First, serialize:
        String json = writer.write( src );
        assertEquals( EXP_SIMPLE_INT_PROP, json );
        // and second time too, for a good measure
        json = writer.write( src );
        assertEquals( EXP_SIMPLE_INT_PROP, json );
    }

    // Test for verifying that custom
    public void testCustomPropertyForClass( ObjectWriterTester<IdentifiableWithProp> writer ) {
        IdentifiableWithProp src = new IdentifiableWithProp( 123, -19 );
        src.next = src;

        // First, serialize:
        String json = writer.write( src );
        assertEquals( EXP_CUSTOM_PROP, json );

        // and ensure that state is cleared in-between as well:
        json = writer.write( src );
        assertEquals( EXP_CUSTOM_PROP, json );
    }

    // Test for verifying that custom
    public void testCustomPropertyViaProperty( ObjectWriterTester<IdWrapperCustom> writer ) {
        IdWrapperCustom src = new IdWrapperCustom( 123, 7 );
        src.node.next = src;

        // First, serialize:
        String json = writer.write( src );
        assertEquals( EXP_CUSTOM_PROP_VIA_REF, json );
        // and second time too, for a good measure
        json = writer.write( src );
        assertEquals( EXP_CUSTOM_PROP_VIA_REF, json );
    }

    public void testAlwaysAsId( ObjectWriterTester<AlwaysContainer> writer ) {
        String json = writer.write( new AlwaysContainer() );
        assertEquals( "{\"a\":1,\"b\":2}", json );
    }

    public void testAlwaysIdForTree( ObjectWriterTester<TreeNode> writer ) {
        TreeNode root = new TreeNode( null, 1, "root" );
        TreeNode leaf = new TreeNode( root, 2, "leaf" );
        root.child = leaf;
        String json = writer.write( root );
        assertEquals( "{\"id\":1,\"name\":\"root\",\"parent\":null,\"child\":{\"id\":2,\"name\":\"leaf\",\"parent\":1,\"child\":null}}", json );
    }

    /*
    /*****************************************************
    /* Unit tests, error handling
    /*****************************************************
     */

    public void testInvalidProp( ObjectWriterTester<Broken> writer ) {
        try {
            writer.write( new Broken() );
            fail( "Should have thrown an exception" );
        } catch ( JsonSerializationException e ) {
        }
    }
}
