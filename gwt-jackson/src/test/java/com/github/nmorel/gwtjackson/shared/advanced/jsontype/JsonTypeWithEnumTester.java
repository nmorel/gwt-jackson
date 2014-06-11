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

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

public final class JsonTypeWithEnumTester extends AbstractTester {
    /*
    /**********************************************************
    /* Helper types
    /**********************************************************
     */

    public static interface HasCode {

        String getCode();

    }

    @JsonTypeInfo( use = Id.CLASS, include = As.PROPERTY, property = "class" )
    public static interface HasCodeAsProperty extends HasCode {

    }

    @JsonTypeInfo( use = Id.CLASS, include = As.WRAPPER_ARRAY, property = "class" )
    public static interface HasCodeAsWrapperArray extends HasCode {

    }

    @JsonTypeInfo( use = Id.CLASS, include = As.WRAPPER_OBJECT, property = "class" )
    public static interface HasCodeAsWrapperObject extends HasCode {

    }

    public static enum HasCodeEnum implements HasCodeAsProperty, HasCodeAsWrapperArray, HasCodeAsWrapperObject {

        CODE_ONE( "code one" ),
        CODE_TWO( "code two" ),
        CODE_THREE( "code three" );

        private String code;

        private HasCodeEnum( String code ) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }

    }

    public static class HasCodeBean implements HasCodeAsProperty, HasCodeAsWrapperArray, HasCodeAsWrapperObject {

        private final String code;

        @JsonCreator()
        public HasCodeBean( @JsonProperty( "code" ) String code ) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }

            HasCodeBean that = (HasCodeBean) o;

            if ( code != null ? !code.equals( that.code ) : that.code != null ) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return code != null ? code.hashCode() : 0;
        }
    }

    public static class HasCodeWrapperBean {

        public List<HasCodeAsProperty> hasCodeAsPropertyList;

        public List<HasCodeAsWrapperArray> hasCodeAsWrapperArrayList;

        public List<HasCodeAsWrapperObject> hasCodeAsWrapperObjectList;

    }

    public static final JsonTypeWithEnumTester INSTANCE = new JsonTypeWithEnumTester();

    private JsonTypeWithEnumTester() {
    }

    /*
    /**********************************************************
    /* Unit tests
    /**********************************************************
     */

    public void testWithJsonTypeInfo( ObjectMapperTester<HasCodeWrapperBean> mapper ) {

        HasCodeWrapperBean bean = new HasCodeWrapperBean();
        bean.hasCodeAsPropertyList = Arrays.<HasCodeAsProperty>asList( HasCodeEnum.CODE_ONE, new HasCodeBean( "bean code one" ) );
        bean.hasCodeAsWrapperArrayList = Arrays.<HasCodeAsWrapperArray>asList( HasCodeEnum.CODE_TWO, new HasCodeBean( "bean code two" ) );
        bean.hasCodeAsWrapperObjectList = Arrays
                .<HasCodeAsWrapperObject>asList( HasCodeEnum.CODE_THREE, new HasCodeBean( "bean code three" ) );

        String json = mapper.write( bean );
        String expected = "{\"hasCodeAsPropertyList\":" +
                "[" +
                "[" +
                "\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithEnumTester$HasCodeEnum\"," +
                "\"CODE_ONE\"" +
                "]," +
                "{" +
                "\"class\":\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithEnumTester$HasCodeBean\"," +
                "\"code\":\"bean code one\"" +
                "}" +
                "]," +
                "\"hasCodeAsWrapperArrayList\":" +
                "[" +
                "[" +
                "\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithEnumTester$HasCodeEnum\"," +
                "\"CODE_TWO\"" +
                "]," +
                "[" +
                "\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithEnumTester$HasCodeBean\"," +
                "{" +
                "\"code\":\"bean code two\"" +
                "}" +
                "]" +
                "]," +
                "\"hasCodeAsWrapperObjectList\":" +
                "[" +
                "{" +
                "\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithEnumTester$HasCodeEnum\":\"CODE_THREE\"" +
                "}," +
                "{" +
                "\"com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeWithEnumTester$HasCodeBean\":" +
                "{" +
                "\"code\":\"bean code three\"" +
                "}" +
                "}" +
                "]" +
                "}";
        assertEquals( expected, json );

        bean = mapper.read( json );
        assertEquals( bean.hasCodeAsPropertyList, Arrays
                .<HasCodeAsProperty>asList( HasCodeEnum.CODE_ONE, new HasCodeBean( "bean code one" ) ) );
        assertEquals( bean.hasCodeAsWrapperArrayList, Arrays
                .<HasCodeAsWrapperArray>asList( HasCodeEnum.CODE_TWO, new HasCodeBean( "bean code two" ) ) );
        assertEquals( bean.hasCodeAsWrapperObjectList, Arrays
                .<HasCodeAsWrapperObject>asList( HasCodeEnum.CODE_THREE, new HasCodeBean( "bean code three" ) ) );
    }

    public void testSerializeWithoutJsonTypeInfo( ObjectWriterTester<List<HasCode>> writer ) {
        String json = writer.write( Arrays.<HasCode>asList( HasCodeEnum.CODE_ONE, new HasCodeBean( "bean code one" ) ) );
        String expected = "[\"CODE_ONE\",{\"code\":\"bean code one\"}]";
        assertEquals( expected, json );
    }

}
