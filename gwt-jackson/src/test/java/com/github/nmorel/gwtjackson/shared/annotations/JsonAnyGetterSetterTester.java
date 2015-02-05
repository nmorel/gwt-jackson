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

package com.github.nmorel.gwtjackson.shared.annotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class JsonAnyGetterSetterTester extends AbstractTester {

    public static class BeanWithAnyGetterAndAnySetter {

        private String aString;

        private int anInt;

        private Double notVisibleDouble;

        private List<String> notVisibleList;

        public String getaString() {
            return aString;
        }

        public void setaString( String aString ) {
            this.aString = aString;
        }

        public int getAnInt() {
            return anInt;
        }

        public void setAnInt( int anInt ) {
            this.anInt = anInt;
        }

        @JsonAnyGetter
        private Map other() {
            Map map = new LinkedHashMap();
            map.put( "notVisibleDouble", notVisibleDouble );
            map.put( "notVisibleList", notVisibleList );
            return map;
        }

        @JsonAnySetter
        private void other( String property, Object value ) {
            if ( "notVisibleDouble".equals( property ) ) {
                notVisibleDouble = (Double) value;
            } else if ( "notVisibleList".equals( property ) ) {
                notVisibleList = (List<String>) value;
            }
        }

    }

    public static final JsonAnyGetterSetterTester INSTANCE = new JsonAnyGetterSetterTester();

    private JsonAnyGetterSetterTester() {
    }

    public void testSerialize( ObjectWriterTester<BeanWithAnyGetterAndAnySetter> writer ) {
        BeanWithAnyGetterAndAnySetter bean = new BeanWithAnyGetterAndAnySetter();
        bean.aString = "visibleString";
        bean.anInt = 478552;
        bean.notVisibleDouble = 85.24d;
        bean.notVisibleList = new ArrayList<String>( Arrays.asList( "Hello", "World", "!" ) );

        String expected = "{\"aString\":\"visibleString\"," +
                "\"anInt\":478552," +
                "\"notVisibleDouble\":85.24," +
                "\"notVisibleList\":[\"Hello\",\"World\",\"!\"]" +
                "}";
        String result = writer.write( bean );

        assertEquals( expected, result );
    }

    public void testDeserialize( ObjectReaderTester<BeanWithAnyGetterAndAnySetter> reader ) {
        String input = "{\"aString\":\"visibleString\"," +
                "\"anInt\":478552," +
                "\"notVisibleDouble\":85.24," +
                "\"notVisibleList\":[\"Hello\",\"World\",\"!\"]" +
                "}";

        BeanWithAnyGetterAndAnySetter result = reader.read( input );

        assertEquals( "visibleString", result.aString );
        assertEquals( 478552, result.anInt );
        assertEquals( 85.24d, result.notVisibleDouble );
        assertEquals( Arrays.asList( "Hello", "World", "!" ), result.notVisibleList );
    }

}
