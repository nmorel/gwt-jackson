/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.mapper;

import java.util.AbstractMap;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.model.AnEnum;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class AllMapsObjectMapperTest extends GwtJacksonTestCase {

    public static interface BeanWithMapsTypeMapper extends ObjectMapper<BeanWithMapsType> {

        static BeanWithMapsTypeMapper INSTANCE = GWT.create( BeanWithMapsTypeMapper.class );
    }

    public static class BeanWithMapsType {

        public AbstractMap<String, Integer> abstractMap;

        public EnumMap<AnEnum, Integer> enumMap;

        public HashMap<String, Integer> hashMap;

        public IdentityHashMap<String, Integer> identityHashMap;

        public LinkedHashMap<String, Integer> linkedHashMap;

        public Map<String, Integer> map;

        public SortedMap<String, Integer> sortedMap;

        public TreeMap<String, Integer> treeMap;
    }

    public void testDeserializeValue() {
        String input = "{" +
                "\"abstractMap\":{\"one\":1,\"four\":2,\"two\":2,\"three\":3,\"four\":4}," +
                "\"enumMap\":{\"A\":1,\"C\":3,\"B\":2,\"D\":4,\"D\":5}," +
                "\"hashMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"identityHashMap\":{\"three\":3}," +
                "\"linkedHashMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"map\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"sortedMap\":{\"four\":4,\"two\":2,\"one\":1,\"three\":3}," +
                "\"treeMap\":{\"one\":1,\"three\":3,\"four\":4,\"two\":2}" +
                "}";

        BeanWithMapsType bean = BeanWithMapsTypeMapper.INSTANCE.read( input );
        assertNotNull( bean );

        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<String, Integer>();
        linkedHashMap.put( "one", 1 );
        linkedHashMap.put( "two", 2 );
        linkedHashMap.put( "three", 3 );
        linkedHashMap.put( "four", 4 );

        assertEquals( linkedHashMap, bean.abstractMap );
        assertEquals( linkedHashMap, bean.hashMap );
        assertEquals( linkedHashMap, bean.linkedHashMap );
        assertEquals( linkedHashMap, bean.map );

        Entry<String, Integer> entry = bean.identityHashMap.entrySet().iterator().next();
        assertEquals( "three", entry.getKey() );
        assertEquals( 3, (int) entry.getValue() );

        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
        treeMap.put( "one", 1 );
        treeMap.put( "two", 2 );
        treeMap.put( "three", 3 );
        treeMap.put( "four", 4 );
        assertEquals( treeMap, bean.treeMap );
        assertEquals( treeMap, bean.sortedMap );

        EnumMap<AnEnum, Integer> enumMap = new EnumMap<AnEnum, Integer>( AnEnum.class );
        enumMap.put( AnEnum.A, 1 );
        enumMap.put( AnEnum.D, 5 );
        enumMap.put( AnEnum.C, 3 );
        enumMap.put( AnEnum.B, 2 );
        assertEquals( enumMap, bean.enumMap );
    }

    public void testSerializeValue() {

        BeanWithMapsType bean = new BeanWithMapsType();

        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<String, Integer>();
        linkedHashMap.put( "one", 1 );
        linkedHashMap.put( "two", 2 );
        linkedHashMap.put( "three", 3 );
        linkedHashMap.put( "four", 4 );

        bean.abstractMap = linkedHashMap;
        bean.hashMap = linkedHashMap;
        bean.linkedHashMap = linkedHashMap;
        bean.map = linkedHashMap;

        IdentityHashMap<String, Integer> identityHashMap = new IdentityHashMap<String, Integer>();
        identityHashMap.put( "one", 1 );

        bean.identityHashMap = identityHashMap;

        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
        treeMap.put( "one", 1 );
        treeMap.put( "two", 2 );
        treeMap.put( "three", 3 );
        treeMap.put( "four", 4 );

        bean.sortedMap = treeMap;
        bean.treeMap = treeMap;

        EnumMap<AnEnum, Integer> enumMap = new EnumMap<AnEnum, Integer>( AnEnum.class );
        enumMap.put( AnEnum.A, 1 );
        enumMap.put( AnEnum.D, 4 );
        enumMap.put( AnEnum.C, 3 );
        enumMap.put( AnEnum.B, 2 );

        bean.enumMap = enumMap;

        String expected = "{" +
                "\"abstractMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"enumMap\":{\"A\":1,\"B\":2,\"C\":3,\"D\":4}," +
                "\"hashMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"identityHashMap\":{\"one\":1}," +
                "\"linkedHashMap\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"map\":{\"one\":1,\"two\":2,\"three\":3,\"four\":4}," +
                "\"sortedMap\":{\"four\":4,\"one\":1,\"three\":3,\"two\":2}," +
                "\"treeMap\":{\"four\":4,\"one\":1,\"three\":3,\"two\":2}" +
                "}";

        assertEquals( expected, BeanWithMapsTypeMapper.INSTANCE.write( bean ) );
    }
}
