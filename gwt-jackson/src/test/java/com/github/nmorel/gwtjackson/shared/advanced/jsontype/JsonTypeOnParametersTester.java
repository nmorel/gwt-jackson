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

package com.github.nmorel.gwtjackson.shared.advanced.jsontype;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;

/**
 * Testing to verify that {@link JsonTypeInfo} works
 * for properties as well as types (see [JACKSON-280] for details)
 */
public final class JsonTypeOnParametersTester extends AbstractTester {
    /*
    /**********************************************************
    /* Helper types
    /**********************************************************
     */

    public static class FieldWrapperBean {

        private final StringWrapper value;

        public FieldWrapperBean( @JsonProperty( "value" ) @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS,
                include = JsonTypeInfo.As.WRAPPER_ARRAY ) StringWrapper value ) {
            this.value = value;
        }

        public StringWrapper getValue() {
            return value;
        }
    }

    public static class FieldWrapperBeanList {

        private final ArrayList<FieldWrapperBean> beans;

        public FieldWrapperBeanList( @JsonProperty( "beans" ) @JsonTypeInfo( use = JsonTypeInfo.Id.CLASS,
                include = JsonTypeInfo.As.WRAPPER_ARRAY ) ArrayList<FieldWrapperBean> beans ) {
            this.beans = beans;
        }

        public ArrayList<FieldWrapperBean> getBeans() {
            return beans;
        }
    }

    public static final JsonTypeOnParametersTester INSTANCE = new JsonTypeOnParametersTester();

    private JsonTypeOnParametersTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testSimpleField( ObjectMapperTester<FieldWrapperBean> mapper ) {
        String json = mapper.write( new FieldWrapperBean( new StringWrapper( "foo" ) ) );
        String expected = "{" +
                "\"value\":" +
                "[" +
                "\"com.github.nmorel.gwtjackson.shared.AbstractTester$StringWrapper\"" +
                "," +
                "{\"str\":\"foo\"}" +
                "]" +
                "}";
        assertEquals( expected, json );

        FieldWrapperBean bean = mapper.read( json );
        assertNotNull( bean.value );
        assertEquals( bean.value.str, "foo" );
    }

    public void testSimpleListField( ObjectMapperTester<FieldWrapperBeanList> mapper ) {
        ArrayList<FieldWrapperBean> list = new ArrayList<FieldWrapperBean>();
        list.add( new FieldWrapperBean( new StringWrapper( "foo" ) ) );
        FieldWrapperBeanList beanList = new FieldWrapperBeanList( list );

        String json = mapper.write( beanList );
        String expected = "{" +
                "\"beans\":" +
                "[" +
                "[" +
                "\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnParametersTester$FieldWrapperBean\"" +
                "," +
                "{" +
                "\"value\":" +
                "[" +
                "\"com.github.nmorel.gwtjackson.shared.AbstractTester$StringWrapper\"" +
                "," +
                "{\"str\":\"foo\"}" +
                "]" +
                "}" +
                "]" +
                "]" +
                "}";
        assertEquals( expected, json );

        FieldWrapperBeanList result = mapper.read( json );
        assertNotNull( result );
        assertEquals( 1, result.beans.size() );
        FieldWrapperBean bean = result.beans.get( 0 );
        assertEquals( bean.value.str, "foo" );
    }
}
