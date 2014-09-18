/*
 * Copyright 2014 Nicolas Morel
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

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns;
import com.github.nmorel.gwtjackson.client.annotation.JsonMixIns.JsonMixIn;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.google.gwt.core.client.GWT;

/**
 * A test that forces errors in order to test if gwt-jackson gracefully fallback and does not generate compilation error.
 *
 * @author Nicolas Morel
 */
public class ErrorGwtTest extends GwtJacksonTestCase {

    /**
     * A private subtype of comparator to be sure it is ignored.
     *
     * @param <T>
     */
    private static class PrivateComparator<T> implements Comparator<T> {

        @Override
        public int compare( T o1, T o2 ) {
            return 1;
        }
    }

    @JsonMixIns( @JsonMixIn( target = Comparator.class, mixIn = MixinCom.class ) )
    public interface ComparatorIntegerMapper extends ObjectMapper<Comparator<Integer>>, ObjectMapperTester<Comparator<Integer>> {

        static ComparatorIntegerMapper INSTANCE = GWT.create( ComparatorIntegerMapper.class );
    }

    @JsonTypeInfo( use = Id.CLASS )
    public static class MixinCom {}

    public void testSerialization() {
        // Using a class inside java.* package to test we don't generate the mapper inside it because it is prohibited by java.
        ComparatorIntegerMapper.INSTANCE.write( new Comparator<Integer>() {
            @Override
            public int compare( Integer o1, Integer o2 ) {
                return 0;
            }
        } );
    }
}
