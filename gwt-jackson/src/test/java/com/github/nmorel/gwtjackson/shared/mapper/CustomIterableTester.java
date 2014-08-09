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

package com.github.nmorel.gwtjackson.shared.mapper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * @author Nicolas Morel
 */
public final class CustomIterableTester extends AbstractTester {

    public static class MyIntegerIterable implements Iterable<Integer> {

        @JsonProperty
        private final List<Integer> list;

        @JsonCreator
        public MyIntegerIterable( @JsonProperty( "list" ) List<Integer> list ) {
            this.list = list;
        }

        @Override
        public Iterator<Integer> iterator() {
            return list.iterator();
        }
    }

    public static class Wrapper {

        private MyIntegerIterable iterable;

        @JsonCreator
        public Wrapper( @JsonProperty( "iterable" ) MyIntegerIterable iterable ) {
            this.iterable = iterable;
        }

        public MyIntegerIterable getIterable() {
            return iterable;
        }
    }

    public static final CustomIterableTester INSTANCE = new CustomIterableTester();

    private CustomIterableTester() {
    }

    public void testCustomIntegerIterable( ObjectMapperTester<MyIntegerIterable> mapper ) {
        MyIntegerIterable iterable = new MyIntegerIterable( Arrays.asList( 1, 2, 3, 7, 8, 9 ) );

        String json = mapper.write( iterable );
        assertEquals( "{\"list\":[1,2,3,7,8,9]}", json );

        MyIntegerIterable result = mapper.read( json );
        Iterator<Integer> iterator = result.iterator();
        assertEquals( 1, iterator.next().intValue() );
        assertEquals( 2, iterator.next().intValue() );
        assertEquals( 3, iterator.next().intValue() );
        assertEquals( 7, iterator.next().intValue() );
        assertEquals( 8, iterator.next().intValue() );
        assertEquals( 9, iterator.next().intValue() );
        assertFalse( iterator.hasNext() );
    }

    public void testWrapper( ObjectMapperTester<Wrapper> mapper ) {
        Wrapper wrapper = new Wrapper( new MyIntegerIterable( Arrays.asList( 1, 2, 3, 7, 8, 9 ) ) );

        String json = mapper.write( wrapper );
        assertEquals( "{\"iterable\":{\"list\":[1,2,3,7,8,9]}}", json );

        Wrapper result = mapper.read( json );
        Iterator<Integer> iterator = result.getIterable().iterator();
        assertEquals( 1, iterator.next().intValue() );
        assertEquals( 2, iterator.next().intValue() );
        assertEquals( 3, iterator.next().intValue() );
        assertEquals( 7, iterator.next().intValue() );
        assertEquals( 8, iterator.next().intValue() );
        assertEquals( 9, iterator.next().intValue() );
        assertFalse( iterator.hasNext() );
    }

}
