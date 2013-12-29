package com.github.nmorel.gwtjackson.guava.shared;

import java.util.Iterator;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;

/**
 * Unit tests to verify serialization of {@link FluentIterable}s.
 */
public final class FluentIterableTester extends AbstractTester {

    FluentIterable<Integer> createFluentIterable() {
        return new FluentIterable<Integer>() {
            private final Iterable<Integer> _iterable = Sets.newHashSet( 1, 2, 3 );

            @Override
            public Iterator<Integer> iterator() {
                return _iterable.iterator();
            }
        };
    }

    public static final FluentIterableTester INSTANCE = new FluentIterableTester();

    private FluentIterableTester() {
    }

    public void testSerialization( ObjectWriterTester<FluentIterable<Integer>> writer ) {
        FluentIterable<Integer> fluentIterable = createFluentIterable();
        String json = writer.write( fluentIterable );
        assertEquals( "[1,2,3]", json );
    }

}
