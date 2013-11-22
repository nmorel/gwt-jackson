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

package com.github.nmorel.gwtjackson.client.advanced.jsontype;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanArray;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanList;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanMap;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanArray;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanList;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanMap;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonTypeOnPropertiesGwtTest extends GwtJacksonTestCase {

    public interface FieldWrapperBeanMapper extends ObjectMapper<FieldWrapperBean>, ObjectMapperTester<FieldWrapperBean> {

        static FieldWrapperBeanMapper INSTANCE = GWT.create( FieldWrapperBeanMapper.class );
    }

    public interface MethodWrapperBeanMapper extends ObjectMapper<MethodWrapperBean>, ObjectMapperTester<MethodWrapperBean> {

        static MethodWrapperBeanMapper INSTANCE = GWT.create( MethodWrapperBeanMapper.class );
    }

    public interface FieldWrapperBeanListMapper extends ObjectMapper<FieldWrapperBeanList>, ObjectMapperTester<FieldWrapperBeanList> {

        static FieldWrapperBeanListMapper INSTANCE = GWT.create( FieldWrapperBeanListMapper.class );
    }

    public interface MethodWrapperBeanListMapper extends ObjectMapper<MethodWrapperBeanList>, ObjectMapperTester<MethodWrapperBeanList> {

        static MethodWrapperBeanListMapper INSTANCE = GWT.create( MethodWrapperBeanListMapper.class );
    }

    public interface FieldWrapperBeanArrayMapper extends ObjectMapper<FieldWrapperBeanArray>, ObjectMapperTester<FieldWrapperBeanArray> {

        static FieldWrapperBeanArrayMapper INSTANCE = GWT.create( FieldWrapperBeanArrayMapper.class );
    }

    public interface MethodWrapperBeanArrayMapper extends ObjectMapper<MethodWrapperBeanArray>, ObjectMapperTester<MethodWrapperBeanArray> {

        static MethodWrapperBeanArrayMapper INSTANCE = GWT.create( MethodWrapperBeanArrayMapper.class );
    }

    public interface FieldWrapperBeanMapMapper extends ObjectMapper<FieldWrapperBeanMap>, ObjectMapperTester<FieldWrapperBeanMap> {

        static FieldWrapperBeanMapMapper INSTANCE = GWT.create( FieldWrapperBeanMapMapper.class );
    }

    public interface MethodWrapperBeanMapMapper extends ObjectMapper<MethodWrapperBeanMap>, ObjectMapperTester<MethodWrapperBeanMap> {

        static MethodWrapperBeanMapMapper INSTANCE = GWT.create( MethodWrapperBeanMapMapper.class );
    }

    private JsonTypeOnPropertiesTester tester = JsonTypeOnPropertiesTester.INSTANCE;

    public void testSimpleField() {
        tester.testSimpleField( FieldWrapperBeanMapper.INSTANCE );
    }

    public void testSimpleMethod() {
        tester.testSimpleMethod( MethodWrapperBeanMapper.INSTANCE );
    }

    public void testSimpleListField() {
        tester.testSimpleListField( FieldWrapperBeanListMapper.INSTANCE );
    }

    public void testSimpleListMethod() {
        tester.testSimpleListMethod( MethodWrapperBeanListMapper.INSTANCE );
    }

    public void testSimpleArrayField() {
        tester.testSimpleArrayField( FieldWrapperBeanArrayMapper.INSTANCE );
    }

    public void testSimpleArrayMethod() {
        tester.testSimpleArrayMethod( MethodWrapperBeanArrayMapper.INSTANCE );
    }

    public void testSimpleMapField() {
        tester.testSimpleMapField( FieldWrapperBeanMapMapper.INSTANCE );
    }

    public void testSimpleMapMethod() {
        tester.testSimpleMapMethod( MethodWrapperBeanMapMapper.INSTANCE );
    }
}
