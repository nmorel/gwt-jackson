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

package com.github.nmorel.gwtjackson.shared.advanced.identity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * Test from jackson-databind and adapted for the project
 */
public final class ObjectIdWithEqualsTester extends AbstractTester {

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Foo.class)
    public static class Foo {

        public int id;

        public List<Bar> bars = new ArrayList<Bar>();

        public List<Bar> otherBars = new ArrayList<Bar>();

        public Foo() { }

        public Foo( int i ) { id = i; }
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Bar.class)
    static class Bar {

        public int id;

        public Bar() { }

        public Bar( int i ) {
            id = i;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals( Object obj ) {
            if ( !(obj instanceof Bar) ) {
                return false;
            }
            return ((Bar) obj).id == id;
        }
    }

    public static final ObjectIdWithEqualsTester INSTANCE = new ObjectIdWithEqualsTester();

    private ObjectIdWithEqualsTester() { }

    /*
    /******************************************************
    /* Test methods
    /******************************************************
     */

    public void testSimpleEquals( ObjectMapperTester<Foo> mapper ) {

        Foo foo = new Foo( 1 );

        Bar bar1 = new Bar( 1 );
        Bar bar2 = new Bar( 2 );
        // this is another bar which is supposed to be "equal" to bar1
        // due to the same ID and
        // Bar class' equals() method will return true.
        Bar anotherBar1 = new Bar( 1 );

        foo.bars.add( bar1 );
        foo.bars.add( bar2 );
        // this anotherBar1 object will confuse the serializer.
        foo.otherBars.add( anotherBar1 );
        foo.otherBars.add( bar2 );

        String json = mapper.write( foo );
        assertEquals( "{\"id\":1,\"bars\":[{\"id\":1},{\"id\":2}],\"otherBars\":[1,2]}", json );

        Foo foo2 = mapper.read( json );
        assertNotNull( foo2 );
        assertEquals( foo.id, foo2.id );
    }
}
