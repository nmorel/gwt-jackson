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

package com.github.nmorel.gwtjackson.shared.mapper;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

/**
 * @author Nicolas Morel
 */
public final class IgnoreStaticTester extends AbstractTester {

    public static class IgnoreStaticBean {

        public static String STATIC_FIELD = "static";

        public static String getSTATIC_FIELD() {
            return STATIC_FIELD;
        }

        public static void setSTATIC_FIELD( String STATIC_FIELD ) {
            IgnoreStaticBean.STATIC_FIELD = STATIC_FIELD;
        }

        private String string;

        public String getString() {
            return string;
        }

        public void setString( String string ) {
            this.string = string;
        }
    }

    public static final IgnoreStaticTester INSTANCE = new IgnoreStaticTester();

    private IgnoreStaticTester() {
    }

    public void testDeserializeValue( ObjectReaderTester<IgnoreStaticBean> reader ) {

        String input = "{" +
                "\"string\":\"aString\"," +
                "\"STATIC_FIELD\":\"shouldBeIgnored\"" +
                "}";

        assertEquals( "static", IgnoreStaticBean.STATIC_FIELD );
        assertEquals( "static", IgnoreStaticBean.getSTATIC_FIELD() );

        IgnoreStaticBean bean = reader.read( input );
        assertNotNull( bean );

        assertEquals( "aString", bean.getString() );
        assertEquals( "static", IgnoreStaticBean.STATIC_FIELD );
        assertEquals( "static", IgnoreStaticBean.getSTATIC_FIELD() );
    }

    public void testSerializeValue( ObjectWriterTester<IgnoreStaticBean> writer ) {
        IgnoreStaticBean bean = new IgnoreStaticBean();
        bean.setString( "aString" );

        String expected = "{" +
                "\"string\":\"aString\"" +
                "}";

        assertEquals( expected, writer.write( bean ) );
    }
}
