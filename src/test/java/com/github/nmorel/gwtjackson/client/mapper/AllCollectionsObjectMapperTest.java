package com.github.nmorel.gwtjackson.client.mapper;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractQueue;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.model.AnEnum;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class AllCollectionsObjectMapperTest extends GwtJacksonTestCase {

    public static interface BeanWithCollectionsTypeMapper extends ObjectMapper<BeanWithCollectionsType> {

        static BeanWithCollectionsTypeMapper INSTANCE = GWT.create( BeanWithCollectionsTypeMapper.class );
    }

    public static class BeanWithCollectionsType {

        public AbstractCollection<String> abstractCollection;

        public AbstractList<String> abstractList;

        public AbstractQueue<String> abstractQueue;

        public AbstractSequentialList<String> abstractSequentialList;

        public AbstractSet<String> abstractSet;

        public ArrayList<String> arrayList;

        public Collection<String> collection;

        public EnumSet<AnEnum> enumSet;

        public HashSet<String> hashSet;

        public Iterable<String> iterable;

        public LinkedHashSet<String> linkedHashSet;

        public LinkedList<String> linkedList;

        public List<String> list;

        public PriorityQueue<String> priorityQueue;

        public Queue<String> queue;

        public Set<String> set;

        public SortedSet<String> sortedSet;

        public Stack<String> stack;

        public TreeSet<String> treeSet;

        public Vector<String> vector;
    }

    public void testDecodeValue() {
        String input = "{" +
            "\"abstractCollection\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"abstractList\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"abstractQueue\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"abstractSequentialList\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"abstractSet\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"arrayList\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"collection\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"enumSet\":[\"B\",null,\"C\",\"A\"]," +
            "\"hashSet\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"iterable\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"linkedHashSet\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"linkedList\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"list\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"priorityQueue\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"queue\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"set\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"sortedSet\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"stack\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"treeSet\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"vector\":[\"Hello\",null,\"World\",\"!\"]" +
            "}";

        BeanWithCollectionsType bean = BeanWithCollectionsTypeMapper.INSTANCE.decode( input );
        assertNotNull( bean );

        Collection<String> baseExpectedList = Arrays.asList( "Hello", null, "World", "!" );
        Collection<String> baseExpectedCollectionWithoutNull = Arrays.asList( "Hello", "World", "!" );
        Collection<String> baseExpectedSet = new LinkedHashSet<String>( Arrays.asList( "Hello", null, "World", "!" ) );
        Collection<String> baseExpectedSortedSet = new TreeSet<String>( Arrays.asList( "!", "Hello", "World" ) );

        assertEquals( baseExpectedList, bean.abstractCollection );
        assertEquals( baseExpectedList, bean.abstractList );
        assertEquals( baseExpectedList, bean.abstractSequentialList );
        assertEquals( baseExpectedList, bean.arrayList );
        assertEquals( baseExpectedList, bean.collection );
        assertEquals( baseExpectedList, bean.iterable );
        assertEquals( baseExpectedList, bean.linkedList );
        assertEquals( baseExpectedList, bean.list );
        assertEquals( baseExpectedList, bean.stack );
        assertEquals( baseExpectedList, bean.vector );

        // LinkedList by default and we don't add null element
        assertEquals( baseExpectedCollectionWithoutNull, bean.queue );

        assertEquals( baseExpectedSet, bean.set );
        assertEquals( baseExpectedSet, bean.abstractSet );
        assertEquals( baseExpectedSet, bean.hashSet );
        assertEquals( baseExpectedSet, bean.linkedHashSet );

        assertEquals( EnumSet.copyOf( Arrays.asList( AnEnum.A, AnEnum.B, AnEnum.C ) ), bean.enumSet );

        assertTrue( Arrays.deepEquals( new PriorityQueue<String>( baseExpectedCollectionWithoutNull ).toArray(), bean.abstractQueue
            .toArray() ) );
        assertTrue( Arrays.deepEquals( new PriorityQueue<String>( baseExpectedCollectionWithoutNull ).toArray(), bean.priorityQueue
            .toArray() ) );

        assertEquals( baseExpectedSortedSet, bean.sortedSet );
        assertEquals( baseExpectedSortedSet, bean.treeSet );
    }

    public void testEncodeValue() {

        ArrayList<String> list = new ArrayList<String>( Arrays.asList( "Hello", null, "World", "!" ) );
        LinkedList<String> linkedList = new LinkedList<String>( list );

        BeanWithCollectionsType bean = new BeanWithCollectionsType();
        bean.abstractCollection = list;
        bean.abstractList = list;
        bean.abstractSequentialList = linkedList;
        bean.arrayList = list;
        bean.collection = list;
        bean.iterable = list;
        bean.linkedList = linkedList;
        bean.list = list;
        bean.stack = new Stack<String>();
        bean.stack.add( "Hello" );
        bean.stack.add( null );
        bean.stack.add( "World" );
        bean.stack.add( "!" );
        bean.vector = new Vector<String>( list );

        PriorityQueue<String> queue = new PriorityQueue<String>( Arrays.asList( "!", "World", "Hello" ) );
        bean.queue = queue;
        bean.abstractQueue = queue;
        bean.priorityQueue = queue;

        bean.enumSet = EnumSet.copyOf( Arrays.asList( AnEnum.A ) );

        bean.linkedHashSet = new LinkedHashSet<String>( list );
        bean.abstractSet = bean.linkedHashSet;
        bean.set = bean.linkedHashSet;
        bean.hashSet = bean.linkedHashSet;

        bean.treeSet = new TreeSet<String>( Arrays.asList( "Hello", "World", "!" ) );
        bean.sortedSet = bean.treeSet;

        String expected = "{" +
            "\"abstractCollection\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"abstractList\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"abstractQueue\":[\"!\",\"World\",\"Hello\"]," +
            "\"abstractSequentialList\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"abstractSet\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"arrayList\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"collection\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"enumSet\":[\"A\"]," +
            "\"hashSet\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"iterable\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"linkedHashSet\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"linkedList\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"list\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"priorityQueue\":[\"!\",\"World\",\"Hello\"]," +
            "\"queue\":[\"!\",\"World\",\"Hello\"]," +
            "\"set\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"sortedSet\":[\"!\",\"Hello\",\"World\"]," +
            "\"stack\":[\"Hello\",null,\"World\",\"!\"]," +
            "\"treeSet\":[\"!\",\"Hello\",\"World\"]," +
            "\"vector\":[\"Hello\",null,\"World\",\"!\"]" +
            "}";

        assertEquals( expected, BeanWithCollectionsTypeMapper.INSTANCE.encode( bean ) );
    }
}
