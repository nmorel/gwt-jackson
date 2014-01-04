package com.github.nmorel.gwtjackson.guava.client;

import java.util.Arrays;
import java.util.List;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumMultiset;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import com.google.gwt.core.client.GWT;

/**
 * Unit tests to verify serialization and deserialization of {@link BiMap}s.
 */
public class MultisetGwtTest extends GwtJacksonGuavaTestCase {

    public static enum AlphaEnum {
        A, B, C, D
    }

    public interface BeanWithMultisetTypesMapper extends ObjectMapper<BeanWithMultisetTypes>, ObjectMapperTester<BeanWithMultisetTypes> {

        static BeanWithMultisetTypesMapper INSTANCE = GWT.create( BeanWithMultisetTypesMapper.class );
    }

    public static class BeanWithMultisetTypes {

        public Multiset<String> multiset;

        public HashMultiset<String> hashMultiset;

        public LinkedHashMultiset<String> linkedHashMultiset;

        public SortedMultiset<String> sortedMultiset;

        public TreeMultiset<String> treeMultiset;

        public ImmutableMultiset<String> immutableMultiset;

        public EnumMultiset<AlphaEnum> enumMultiset;
    }

    public void testSerialization() {
        BeanWithMultisetTypes bean = new BeanWithMultisetTypes();

        List<String> list = Arrays.asList( "foo", "abc", null, "abc" );
        List<String> listWithNonNull = Arrays.asList( "foo", "abc", "bar", "abc" );

        bean.multiset = LinkedHashMultiset.create( list );
        bean.hashMultiset = HashMultiset.create( Arrays.asList( "abc", "abc" ) );
        bean.linkedHashMultiset = LinkedHashMultiset.create( list );
        bean.sortedMultiset = TreeMultiset.create( listWithNonNull );
        bean.treeMultiset = TreeMultiset.create( listWithNonNull );
        bean.immutableMultiset = ImmutableMultiset.copyOf( listWithNonNull );
        bean.enumMultiset = EnumMultiset.create( Arrays.asList( AlphaEnum.B, AlphaEnum.A, AlphaEnum.D, AlphaEnum.A ) );

        String expected = "{" +
                "\"multiset\":[\"foo\",\"abc\",\"abc\",null]," +
                "\"hashMultiset\":[\"abc\",\"abc\"]," +
                "\"linkedHashMultiset\":[\"foo\",\"abc\",\"abc\",null]," +
                "\"sortedMultiset\":[\"abc\",\"abc\",\"bar\",\"foo\"]," +
                "\"treeMultiset\":[\"abc\",\"abc\",\"bar\",\"foo\"]," +
                "\"immutableMultiset\":[\"foo\",\"abc\",\"abc\",\"bar\"]," +
                "\"enumMultiset\":[\"A\",\"A\",\"B\",\"D\"]" +
                "}";

        assertEquals( expected, BeanWithMultisetTypesMapper.INSTANCE.write( bean ) );
    }

    public void testDeserialization() {
        String input = "{" +
                "\"multiset\":[\"foo\",\"abc\",\"abc\",null]," +
                "\"hashMultiset\":[\"abc\",\"abc\"]," +
                "\"linkedHashMultiset\":[\"foo\",\"abc\",\"abc\",null]," +
                "\"sortedMultiset\":[\"foo\",\"abc\",\"bar\",\"abc\",null]," +
                "\"treeMultiset\":[\"bar\",\"abc\",\"abc\",\"foo\",null]," +
                "\"immutableMultiset\":[\"foo\",\"abc\",\"abc\",\"bar\",null]," +
                "\"enumMultiset\":[\"B\",\"A\",\"A\",\"D\",null]" +
                "}";

        BeanWithMultisetTypes result = BeanWithMultisetTypesMapper.INSTANCE.read( input );
        assertNotNull( result );

        List<String> expectedList = Arrays.asList( "foo", "abc", null, "abc" );
        List<String> expectedListWithNonNull = Arrays.asList( "foo", "abc", "bar", "abc" );

        assertEquals( LinkedHashMultiset.create( expectedList ), result.multiset );
        assertEquals( HashMultiset.create( Arrays.asList( "abc", "abc" ) ), result.hashMultiset );
        assertEquals( LinkedHashMultiset.create( expectedList ), result.linkedHashMultiset );
        assertEquals( TreeMultiset.create( expectedListWithNonNull ), result.sortedMultiset );
        assertEquals( TreeMultiset.create( expectedListWithNonNull ), result.treeMultiset );
        assertEquals( ImmutableMultiset.copyOf( expectedListWithNonNull ), result.immutableMultiset );
        assertEquals( EnumMultiset.create( Arrays.asList( AlphaEnum.B, AlphaEnum.A, AlphaEnum.D, AlphaEnum.A ) ), result.enumMultiset );
    }

}
