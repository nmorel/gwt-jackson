package com.github.nmorel.gwtjackson.shared.options;

import java.util.LinkedHashMap;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class OrderMapEntriesByKeysOptionTester extends AbstractTester {

    public static final OrderMapEntriesByKeysOptionTester INSTANCE = new OrderMapEntriesByKeysOptionTester();

    private OrderMapEntriesByKeysOptionTester() {
    }

    public void testWriteUnordered( ObjectWriterTester<LinkedHashMap<String, Integer>> writer ) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put( "b", 3 );
        map.put( "a", 6 );
        assertEquals( "{\"b\":3,\"a\":6}", writer.write( map ) );
    }

    public void testWriteOrdered( ObjectWriterTester<LinkedHashMap<String, Integer>> writer ) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put( "b", 3 );
        map.put( "a", 6 );
        assertEquals( "{\"a\":6,\"b\":3}", writer.write( map ) );
    }

}
