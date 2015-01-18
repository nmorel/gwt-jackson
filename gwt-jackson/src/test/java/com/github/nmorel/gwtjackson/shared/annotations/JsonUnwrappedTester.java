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

package com.github.nmorel.gwtjackson.shared.annotations;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class JsonUnwrappedTester extends AbstractTester {

    public static class BeanWrapper {

        public String name;

        @JsonUnwrapped
        public BeanUnwrapped<String> unwrapped;
    }

    public static class BeanUnwrapped<T> {

        public List<T> list;

        public int value;

    }

    public static final JsonUnwrappedTester INSTANCE = new JsonUnwrappedTester();

    private JsonUnwrappedTester() {
    }

    public void testSerialize( ObjectWriterTester<BeanWrapper> writer ) {
        BeanWrapper bean = new BeanWrapper();
        bean.name = "wrapper";
        bean.unwrapped = new BeanUnwrapped<String>();
        bean.unwrapped.list = Arrays.asList( "Hello", "World", "!" );
        bean.unwrapped.value = 78451;

        String expected = "{\"name\":\"wrapper\",\"list\":[\"Hello\",\"World\",\"!\"],\"value\":78451}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserialize( ObjectReaderTester<BeanWrapper> reader ) {
        String input = "{\"name\":\"wrapper\",\"list\":[\"Hello\",\"World\",\"!\"],\"value\":78451}";

        BeanWrapper result = reader.read( input );

        assertEquals( "wrapper", result.name );
        assertNotNull( result.unwrapped );
        assertEquals( Arrays.asList( "Hello", "World", "!" ), result.unwrapped.list );
        assertEquals( 78451, result.unwrapped.value );
    }

}
