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

package com.github.nmorel.gwtjackson.shared.annotations;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * Test for [JACKSON-429]
 */
public final class JsonIgnoreTypeTester extends AbstractTester {
    /*
    /**********************************************************
    /* Annotated helper classes
    /**********************************************************
     */

    @JsonIgnoreType(false)
    public static class NonIgnoredType {

        public int value;

        public IgnoredType ignored;
    }

    @JsonIgnoreType
    class IgnoredType { // note: non-static, can't be deserializer

        public IgnoredType( IgnoredType src ) {
        }
    }

    public static class Bean {
        public Superclass d;
    }

    public static class Superclass {

    }

    public static class FailingClass<T extends FailingClass> extends Superclass implements IgnoreType {
        public T test;
    }

    @JsonIgnoreType
    public interface IgnoreType {}

    public static final JsonIgnoreTypeTester INSTANCE = new JsonIgnoreTypeTester();

    private JsonIgnoreTypeTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testDeserialize( ObjectReaderTester<NonIgnoredType> reader ) {
        NonIgnoredType bean = reader.read( "{\"value\":13}" );
        assertNotNull( bean );
        assertEquals( 13, bean.value );
        assertNull( bean.ignored );

        // And also ok to see something with that value; will just get ignored
        bean = reader.read( "{ \"ignored\":[1,2,{}], \"value\":9 }" );
        assertNotNull( bean );
        assertEquals( 9, bean.value );
        assertNull( bean.ignored );
    }

    public void testSerialize( ObjectWriterTester<NonIgnoredType> writer ) {
        NonIgnoredType bean = new NonIgnoredType();
        bean.value = 13;
        bean.ignored = new IgnoredType( null );

        assertEquals( "{\"value\":13}", writer.write( bean ) );
    }

    /**
     * @see https://github.com/nmorel/gwt-jackson/issues/54
     */
    public void testWithIgnoredSubtype(ObjectMapperTester<Bean> mapper){
        // Without the annotation, the compilation failed with a stackoverflow so we just test that there is no error
        mapper.write( new Bean() );
        mapper.read( "{}" );
    }
}
