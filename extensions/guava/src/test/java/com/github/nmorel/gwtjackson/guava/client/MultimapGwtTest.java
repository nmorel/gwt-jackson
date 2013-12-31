package com.github.nmorel.gwtjackson.guava.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.gwt.core.client.GWT;

/**
 * Unit tests to verify serialization and deserialization of {@link BiMap}s.
 */
public class MultimapGwtTest extends GwtJacksonGuavaTestCase {

    public static enum AlphaEnum {
        A, B, C, D
    }

    public interface BeanWithMultimapTypesMapper extends ObjectMapper<BeanWithMultimapTypes>, ObjectMapperTester<BeanWithMultimapTypes> {

        static BeanWithMultimapTypesMapper INSTANCE = GWT.create( BeanWithMultimapTypesMapper.class );
    }

    public static class BeanWithMultimapTypes {

        public Multimap<String, Integer> multimap;

        public ImmutableMultimap<String, Integer> immutableMultimap;

        public ImmutableSetMultimap<String, Integer> immutableSetMultimap;

        public ImmutableListMultimap<String, Integer> immutableListMultimap;

        public SetMultimap<String, Integer> setMultimap;

        public HashMultimap<String, Integer> hashMultimap;

        public LinkedHashMultimap<String, Integer> linkedHashMultimap;

        public SortedSetMultimap<String, Integer> sortedSetMultimap;

        public TreeMultimap<String, Integer> treeMultimap;

        public ListMultimap<String, Integer> listMultimap;

        public ArrayListMultimap<String, Integer> arrayListMultimap;

        public LinkedListMultimap<String, Integer> linkedListMultimap;
    }

    public void testSerialization() {
        BeanWithMultimapTypes bean = new BeanWithMultimapTypes();

        // insertion order for both keys and values
        bean.immutableMultimap = ImmutableMultimap.of( "foo", 3, "bar", 4, "foo", 2, "foo", 5 );
        bean.immutableListMultimap = ImmutableListMultimap.of( "foo", 3, "bar", 4, "foo", 2, "foo", 5 );
        bean.multimap = LinkedListMultimap.create( bean.immutableListMultimap );
        bean.setMultimap = LinkedHashMultimap.create( bean.immutableListMultimap );
        bean.linkedHashMultimap = LinkedHashMultimap.create( bean.immutableMultimap );
        bean.linkedListMultimap = LinkedListMultimap.create( bean.immutableListMultimap );
        bean.listMultimap = LinkedListMultimap.create( bean.immutableMultimap );

        // no order
        bean.immutableSetMultimap = ImmutableSetMultimap.of( "foo", 3 );
        bean.hashMultimap = HashMultimap.create( bean.immutableSetMultimap );

        // natural ordering on both keys and values
        bean.sortedSetMultimap = TreeMultimap.create( bean.immutableMultimap );
        bean.treeMultimap = TreeMultimap.create( bean.immutableMultimap );

        // insertion order on values but no order on keys
        bean.arrayListMultimap = ArrayListMultimap.create( ImmutableListMultimap.of( "foo", 3, "foo", 2, "foo", 5 ) );

        String expected = "{" +
            "\"multimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"immutableMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"immutableSetMultimap\":{\"foo\":[3]}," +
            "\"immutableListMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"setMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"hashMultimap\":{\"foo\":[3]}," +
            "\"linkedHashMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"sortedSetMultimap\":{\"bar\":[4],\"foo\":[2,3,5]}," +
            "\"treeMultimap\":{\"bar\":[4],\"foo\":[2,3,5]}," +
            "\"listMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"arrayListMultimap\":{\"foo\":[3,2,5]}," +
            "\"linkedListMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}" +
            "}";

        assertEquals( expected, BeanWithMultimapTypesMapper.INSTANCE.write( bean ) );
    }

    public void testDeserialization() {
        String input = "{" +
            "\"multimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"immutableMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"immutableSetMultimap\":{\"foo\":[3]}," +
            "\"immutableListMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"setMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"hashMultimap\":{\"foo\":[3]}," +
            "\"linkedHashMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"sortedSetMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"treeMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"listMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}," +
            "\"arrayListMultimap\":{\"foo\":[3,2,5]}," +
            "\"linkedListMultimap\":{\"foo\":[3,2,5],\"bar\":[4]}" +
            "}";

        BeanWithMultimapTypes result = BeanWithMultimapTypesMapper.INSTANCE.read( input );
        assertNotNull( result );

        ImmutableListMultimap<String, Integer> expectedOrderedKeysAndValues = ImmutableListMultimap
            .of( "foo", 3, "bar", 4, "foo", 2, "foo", 5 );
        ImmutableSetMultimap<String, Integer> expectedNonOrdered = ImmutableSetMultimap.of( "foo", 3 );

        assertEquals( LinkedHashMultimap.create( expectedOrderedKeysAndValues ), result.multimap );
        assertEquals( ImmutableMultimap.copyOf( expectedOrderedKeysAndValues ), result.immutableMultimap );
        assertEquals( expectedOrderedKeysAndValues, result.immutableListMultimap );
        assertEquals( LinkedHashMultimap.create( expectedOrderedKeysAndValues ), result.setMultimap );
        assertEquals( LinkedHashMultimap.create( expectedOrderedKeysAndValues ), result.linkedHashMultimap );
        assertEquals( LinkedListMultimap.create( expectedOrderedKeysAndValues ), result.listMultimap );
        assertEquals( LinkedListMultimap.create( expectedOrderedKeysAndValues ), result.linkedListMultimap );

        assertEquals( TreeMultimap.create( expectedOrderedKeysAndValues ), result.sortedSetMultimap );
        assertEquals( TreeMultimap.create( expectedOrderedKeysAndValues ), result.treeMultimap );

        assertEquals( expectedNonOrdered, result.immutableSetMultimap );
        assertEquals( HashMultimap.create( expectedNonOrdered ), result.hashMultimap );

        assertEquals( ArrayListMultimap.create( ImmutableListMultimap.of( "foo", 3, "foo", 2, "foo", 5 ) ), result.arrayListMultimap );
    }

}
