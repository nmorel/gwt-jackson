package com.github.nmorel.gwtjackson.guava.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.core.client.GWT;

/**
 * Unit tests to verify serialization and deserialization of {@link BiMap}s.
 */
public class BiMapGwtTest extends GwtJacksonGuavaTestCase {

    public static enum AlphaEnum {
        A, B, C, D
    }

    public static enum NumericEnum {
        ONE, TWO, THREE, FOUR
    }

    public interface BeanWithBiMapTypesMapper extends ObjectMapper<BeanWithBiMapTypes>, ObjectMapperTester<BeanWithBiMapTypes> {

        static BeanWithBiMapTypesMapper INSTANCE = GWT.create( BeanWithBiMapTypesMapper.class );
    }

    public static class BeanWithBiMapTypes {

        public BiMap<String, Integer> biMap;

        public HashBiMap<String, Integer> hashBiMap;

        public EnumHashBiMap<AlphaEnum, Integer> enumHashBiMap;

        public EnumBiMap<AlphaEnum, NumericEnum> enumBiMap;
    }

    public void testSerialization() {
        BeanWithBiMapTypes bean = new BeanWithBiMapTypes();

        bean.hashBiMap = HashBiMap.create();
        // only one value since it is not sorted
        bean.hashBiMap.put( "one", 1 );

        bean.biMap = bean.hashBiMap;

        bean.enumHashBiMap = EnumHashBiMap.create( AlphaEnum.class );
        bean.enumHashBiMap.put( AlphaEnum.A, 1 );
        bean.enumHashBiMap.put( AlphaEnum.D, 4 );
        bean.enumHashBiMap.put( AlphaEnum.C, 3 );
        bean.enumHashBiMap.put( AlphaEnum.B, 2 );

        bean.enumBiMap = EnumBiMap.create( AlphaEnum.class, NumericEnum.class );
        bean.enumBiMap.put( AlphaEnum.A, NumericEnum.ONE );
        bean.enumBiMap.put( AlphaEnum.D, NumericEnum.FOUR );
        bean.enumBiMap.put( AlphaEnum.C, NumericEnum.THREE );
        bean.enumBiMap.put( AlphaEnum.B, NumericEnum.TWO );

        String expected = "{" +
                "\"biMap\":{\"one\":1}," +
                "\"hashBiMap\":{\"one\":1}," +
                "\"enumHashBiMap\":{\"A\":1,\"B\":2,\"C\":3,\"D\":4}," +
                "\"enumBiMap\":{\"A\":\"ONE\",\"B\":\"TWO\",\"C\":\"THREE\",\"D\":\"FOUR\"}" +
                "}";

        assertEquals( expected, BeanWithBiMapTypesMapper.INSTANCE.write( bean ) );
    }

    public void testDeserialization() {
        String input = "{" +
                "\"biMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"hashBiMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"enumHashBiMap\":{\"A\":1,\"B\":2,\"C\":3,\"D\":4}," +
                "\"enumBiMap\":{\"A\":\"ONE\",\"B\":\"TWO\",\"C\":\"THREE\",\"D\":\"FOUR\"}" +
                "}";

        BeanWithBiMapTypes result = BeanWithBiMapTypesMapper.INSTANCE.read( input );
        assertNotNull( result );

        HashBiMap<String, Integer> expectedHashBiMap = HashBiMap.create();
        expectedHashBiMap.put( "one", 1 );
        expectedHashBiMap.put( "two", 2 );
        expectedHashBiMap.put( "three", 3 );
        expectedHashBiMap.put( "four", 4 );

        assertNotNull( result.biMap );
        assertEquals( expectedHashBiMap, result.biMap );
        assertEquals( expectedHashBiMap, result.hashBiMap );

        EnumHashBiMap<AlphaEnum, Integer> expectedEnumHashBiMap = EnumHashBiMap.create( AlphaEnum.class );
        expectedEnumHashBiMap.put( AlphaEnum.A, 1 );
        expectedEnumHashBiMap.put( AlphaEnum.D, 4 );
        expectedEnumHashBiMap.put( AlphaEnum.C, 3 );
        expectedEnumHashBiMap.put( AlphaEnum.B, 2 );
        assertEquals( expectedEnumHashBiMap, result.enumHashBiMap );

        EnumBiMap<AlphaEnum, NumericEnum> expectedEnumBiMap = EnumBiMap.create( AlphaEnum.class, NumericEnum.class );
        expectedEnumBiMap.put( AlphaEnum.A, NumericEnum.ONE );
        expectedEnumBiMap.put( AlphaEnum.D, NumericEnum.FOUR );
        expectedEnumBiMap.put( AlphaEnum.C, NumericEnum.THREE );
        expectedEnumBiMap.put( AlphaEnum.B, NumericEnum.TWO );
        assertEquals( expectedEnumBiMap, result.enumBiMap );
    }

}
