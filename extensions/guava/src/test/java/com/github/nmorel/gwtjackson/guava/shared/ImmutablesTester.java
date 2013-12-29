package com.github.nmorel.gwtjackson.guava.shared;

import java.util.Iterator;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;

/**
 * Unit tests for verifying that various immutable types
 * (like {@link ImmutableList}, {@link ImmutableMap} and {@link ImmutableSet})
 * work as expected.
 *
 * @author tsaloranta
 */
public final class ImmutablesTester extends AbstractTester {

    public static final ImmutablesTester INSTANCE = new ImmutablesTester();

    private ImmutablesTester() {
    }

    /*
    /**********************************************************************
    /* Unit tests for actual registered module
    /**********************************************************************
     */

    public void testImmutableList( ObjectMapperTester<ImmutableList<Integer>> mapper ) {
        ImmutableList<Integer> list = mapper.read( "[1,2,3]" );
        assertEquals( 3, list.size() );
        assertEquals( Integer.valueOf( 1 ), list.get( 0 ) );
        assertEquals( Integer.valueOf( 2 ), list.get( 1 ) );
        assertEquals( Integer.valueOf( 3 ), list.get( 2 ) );
    }

    public void testImmutableSet( ObjectMapperTester<ImmutableSet<Integer>> mapper ) {
        ImmutableSet<Integer> set = mapper.read( "[3,7,8]" );
        assertEquals( 3, set.size() );
        Iterator<Integer> it = set.iterator();
        assertEquals( Integer.valueOf( 3 ), it.next() );
        assertEquals( Integer.valueOf( 7 ), it.next() );
        assertEquals( Integer.valueOf( 8 ), it.next() );

        set = mapper.read( "[  ]" );
        assertEquals( 0, set.size() );
    }

    public void testImmutableSetFromSingle( ObjectMapperTester<ImmutableSet<String>> mapper ) {
        ImmutableSet<String> set = mapper.read( "\"abc\"" );
        assertEquals( 1, set.size() );
        assertTrue( set.contains( "abc" ) );
    }

    public void testImmutableSortedSet( ObjectMapperTester<ImmutableSortedSet<Integer>> mapper ) {
        ImmutableSortedSet<Integer> set = mapper.read( "[5,1,2]" );
        assertEquals( 3, set.size() );
        Iterator<Integer> it = set.iterator();
        assertEquals( Integer.valueOf( 1 ), it.next() );
        assertEquals( Integer.valueOf( 2 ), it.next() );
        assertEquals( Integer.valueOf( 5 ), it.next() );
    }

    public void testImmutableMap( ObjectMapperTester<ImmutableMap<Integer, Boolean>> mapper ) {
        ImmutableMap<Integer, Boolean> map = mapper.read( "{\"12\":true,\"4\":false}" );
        assertEquals( 2, map.size() );
        assertEquals( Boolean.TRUE, map.get( Integer.valueOf( 12 ) ) );
        assertEquals( Boolean.FALSE, map.get( Integer.valueOf( 4 ) ) );

        map = mapper.read( "{}" );
        assertNotNull( map );
        assertEquals( 0, map.size() );
    }

    public void testImmutableSortedMap( ObjectMapperTester<ImmutableSortedMap<Integer, Boolean>> mapper ) {
        ImmutableSortedMap<Integer, Boolean> map = mapper.read( "{\"12\":true,\"4\":false}" );
        assertEquals( 2, map.size() );
        assertEquals( Boolean.TRUE, map.get( Integer.valueOf( 12 ) ) );
        assertEquals( Boolean.FALSE, map.get( Integer.valueOf( 4 ) ) );
    }

    public void testImmutableBiMap( ObjectMapperTester<ImmutableBiMap<Integer, Boolean>> mapper ) {
        ImmutableBiMap<Integer, Boolean> map = mapper.read( "{\"12\":true,\"4\":false}" );
        assertEquals( 2, map.size() );
        assertEquals( Boolean.TRUE, map.get( 12 ) );
        assertEquals( Boolean.FALSE, map.get( 4 ) );
        assertEquals( map.get( 12 ), Boolean.TRUE );
        assertEquals( map.get( 4 ), Boolean.FALSE );
    }

}
