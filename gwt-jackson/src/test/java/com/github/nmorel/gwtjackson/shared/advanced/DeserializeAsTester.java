/*
 * Copyright 2015 Nicolas Morel
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

package com.github.nmorel.gwtjackson.shared.advanced;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;

/**
 * @author Nicolas Morel
 */
public final class DeserializeAsTester extends AbstractTester {

    public static enum MapKeyEnum {
        TEST1, TEST2
    }

    public static class DeserializeAsWrapper {

        @JsonDeserialize( as = DeserializeAsImpl.class )
        public DeserializeAs as;

        @JsonDeserialize( contentAs = DeserializeAsImpl.class )
        public List<DeserializeAs> asList;

        @JsonDeserialize( contentAs = DeserializeAsImpl.class )
        public Map<String, DeserializeAs> asMap;

        @JsonDeserialize( as = EnumSet.class, contentAs = MapKeyEnum.class )
        public Iterable<Object> asIterable;

        @JsonDeserialize( as = EnumMap.class, keyAs = MapKeyEnum.class, contentAs = DeserializeAsImpl.class )
        public Map<Object, DeserializeAs> asMapKeyAndContent;
    }

    public static interface DeserializeAs {}

    public static class DeserializeAsImpl implements DeserializeAs {

        private final String name;

        @JsonCreator
        public DeserializeAsImpl( @JsonProperty( "name" ) String name ) {
            super();
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static final DeserializeAsTester INSTANCE = new DeserializeAsTester();

    private DeserializeAsTester() {
    }

    public void testDeserializeAs( ObjectReaderTester<DeserializeAsWrapper> reader ) {
        DeserializeAsWrapper wrapper = reader.read( "{" +
                "\"as\":{" +
                "\"name\":\"Nicolas\"" +
                "}," +
                "\"asList\":[{" +
                "\"name\":\"Nicolas\"" +
                "}]," +
                "\"asIterable\":[\"TEST1\",\"TEST2\"]," +
                "\"asMap\":{" +
                "\"map\":{" +
                "\"name\":\"Nicolas\"" +
                "}" +
                "}," +
                "\"asMapKeyAndContent\":{" +
                "\"TEST1\":{" +
                "\"name\":\"Nicolas\"" +
                "}" +
                "}" +
                "}" );

        assertNotNull( wrapper.as );
        assertTrue( wrapper.as instanceof DeserializeAsImpl );
        assertEquals( "Nicolas", ((DeserializeAsImpl) wrapper.as).getName() );

        assertEquals( 1, wrapper.asList.size() );
        assertTrue( wrapper.asList.get( 0 ) instanceof DeserializeAsImpl );
        assertEquals( "Nicolas", ((DeserializeAsImpl) wrapper.asList.get( 0 )).getName() );

        assertTrue( wrapper.asIterable instanceof EnumSet );
        Iterator enumSet = wrapper.asIterable.iterator();
        assertEquals( MapKeyEnum.TEST1, enumSet.next() );
        assertEquals( MapKeyEnum.TEST2, enumSet.next() );
        assertFalse( enumSet.hasNext() );

        assertEquals( 1, wrapper.asMap.size() );
        assertTrue( wrapper.asMap.get( "map" ) instanceof DeserializeAsImpl );
        assertEquals( "Nicolas", ((DeserializeAsImpl) wrapper.asMap.get( "map" )).getName() );

        assertTrue( wrapper.asMapKeyAndContent instanceof EnumMap );
        assertEquals( 1, wrapper.asMapKeyAndContent.size() );
        assertTrue( wrapper.asMapKeyAndContent.get( MapKeyEnum.TEST1 ) instanceof DeserializeAsImpl );
        assertEquals( "Nicolas", ((DeserializeAsImpl) wrapper.asMapKeyAndContent.get( MapKeyEnum.TEST1 )).getName() );
    }

}
