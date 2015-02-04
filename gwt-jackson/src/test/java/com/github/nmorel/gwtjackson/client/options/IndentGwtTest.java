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

package com.github.nmorel.gwtjackson.client.options;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class IndentGwtTest extends GwtJacksonTestCase {

    public interface IndentMapper extends ObjectWriter<Bean> {

        static IndentMapper INSTANCE = GWT.create( IndentMapper.class );
    }

    public static class Bean {

        public String string;

        public Set<Integer> set;

    }

    public void testSerialize() {
        Bean bean = new Bean();
        bean.string = "aString";
        bean.set = new LinkedHashSet<Integer>( Arrays.asList( 1, 2, 3, 4 ) );

        String expected = "{\"string\":\"aString\",\"set\":[1,2,3,4]}";

        // no indent by default
        String result = IndentMapper.INSTANCE.write( bean );
        assertEquals( expected, result );

        expected = "{\n" +
                "  \"string\": \"aString\",\n" +
                "  \"set\": [\n" +
                "    1,\n" +
                "    2,\n" +
                "    3,\n" +
                "    4\n" +
                "  ]\n" +
                "}";

        // activating the indentation
        result = IndentMapper.INSTANCE.write( bean, JsonSerializationContext.builder().indent( true ).build() );
        assertEquals( expected, result );
    }
}
