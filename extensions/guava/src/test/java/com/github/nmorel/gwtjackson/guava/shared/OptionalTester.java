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

package com.github.nmorel.gwtjackson.guava.shared;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.google.common.base.Optional;

/**
 * @author Nicolas Morel
 */
public final class OptionalTester extends AbstractTester {

    public static class BeanWithOptional {

        public String string;

        public Optional<Integer> emptyOptional;

        public Optional<Integer> optional;
    }

    @JsonAutoDetect( fieldVisibility = Visibility.ANY )
    public static final class OptionalGenericData<T> {

        private Optional<T> myData;
    }

    public static final OptionalTester INSTANCE = new OptionalTester();

    private OptionalTester() {
    }

    public void testSerialize( ObjectWriterTester<BeanWithOptional> writer ) {
        BeanWithOptional bean = new BeanWithOptional();
        bean.string = null;
        bean.emptyOptional = Optional.absent();
        bean.optional = Optional.of( 145 );

        String expected = "{" +
            "\"string\":null," +
            "\"emptyOptional\":null," +
            "\"optional\":145" +
            "}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testSerializeWithNonNullSerialization( ObjectWriterTester<BeanWithOptional> writer ) {
        BeanWithOptional bean = new BeanWithOptional();
        bean.string = null;
        bean.emptyOptional = Optional.absent();
        bean.optional = Optional.of( 145 );

        String expected = "{" +
            "\"emptyOptional\":null," +
            "\"optional\":145" +
            "}";

        assertEquals( expected, writer.write( bean ) );
    }

    public void testDeserialize( ObjectReaderTester<BeanWithOptional> reader ) {

        String input = "{" +
            "\"string\":\"aString\"," +
            "\"emptyOptional\":null," +
            "\"optional\":145" +
            "}";

        BeanWithOptional bean = reader.read( input );
        assertEquals( "aString", bean.string );
        assertFalse( bean.emptyOptional.isPresent() );
        assertTrue( bean.optional.isPresent() );
        assertEquals( 145, bean.optional.get().intValue() );
    }

    public void testDeserializeGeneric( ObjectReaderTester<Optional<OptionalGenericData<String>>> reader ) {
        Optional<OptionalGenericData<String>> data = reader.read( "{\"myData\":\"simpleString\"}" );
        assertTrue( data.isPresent() );
        assertTrue( data.get().myData.isPresent() );
        assertEquals( "simpleString", data.get().myData.get() );
    }

    public void testSerializeGeneric( ObjectWriterTester<Optional<OptionalGenericData<String>>> writer ) {
        OptionalGenericData<String> data = new OptionalGenericData<String>();
        data.myData = Optional.of( "simpleString" );
        String value = writer.write( Optional.of( data ) );
        assertEquals( "{\"myData\":\"simpleString\"}", value );
    }

}
