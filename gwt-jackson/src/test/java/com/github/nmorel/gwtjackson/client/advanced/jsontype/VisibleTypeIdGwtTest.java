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
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.ExternalIdWrapper;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.ExternalIdWrapper2;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.I263Base;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.MultipleIds;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.PropertyBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.TypeIdFromFieldArray;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.TypeIdFromFieldProperty;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.TypeIdFromMethodObject;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.WrapperArrayBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.VisibleTypeIdTester.WrapperObjectBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class VisibleTypeIdGwtTest extends GwtJacksonTestCase {

    public interface PropertyBeanMapper extends ObjectMapper<PropertyBean>, ObjectMapperTester<PropertyBean> {

        static PropertyBeanMapper INSTANCE = GWT.create( PropertyBeanMapper.class );
    }

    public interface WrapperArrayBeanMapper extends ObjectMapper<WrapperArrayBean>, ObjectMapperTester<WrapperArrayBean> {

        static WrapperArrayBeanMapper INSTANCE = GWT.create( WrapperArrayBeanMapper.class );
    }

    public interface WrapperObjectBeanMapper extends ObjectMapper<WrapperObjectBean>, ObjectMapperTester<WrapperObjectBean> {

        static WrapperObjectBeanMapper INSTANCE = GWT.create( WrapperObjectBeanMapper.class );
    }

    public interface ExternalIdWrapperMapper extends ObjectMapper<ExternalIdWrapper>, ObjectMapperTester<ExternalIdWrapper> {

        static ExternalIdWrapperMapper INSTANCE = GWT.create( ExternalIdWrapperMapper.class );
    }

    public interface TypeIdFromFieldPropertyMapper extends ObjectMapper<TypeIdFromFieldProperty>,
            ObjectMapperTester<TypeIdFromFieldProperty> {

        static TypeIdFromFieldPropertyMapper INSTANCE = GWT.create( TypeIdFromFieldPropertyMapper.class );
    }

    public interface TypeIdFromFieldArrayMapper extends ObjectMapper<TypeIdFromFieldArray>, ObjectMapperTester<TypeIdFromFieldArray> {

        static TypeIdFromFieldArrayMapper INSTANCE = GWT.create( TypeIdFromFieldArrayMapper.class );
    }

    public interface TypeIdFromMethodObjectMapper extends ObjectMapper<TypeIdFromMethodObject>, ObjectMapperTester<TypeIdFromMethodObject> {

        static TypeIdFromMethodObjectMapper INSTANCE = GWT.create( TypeIdFromMethodObjectMapper.class );
    }

    public interface ExternalIdWrapper2Mapper extends ObjectMapper<ExternalIdWrapper2>, ObjectMapperTester<ExternalIdWrapper2> {

        static ExternalIdWrapper2Mapper INSTANCE = GWT.create( ExternalIdWrapper2Mapper.class );
    }

    public interface I263BaseMapper extends ObjectMapper<I263Base>, ObjectMapperTester<I263Base> {

        static I263BaseMapper INSTANCE = GWT.create( I263BaseMapper.class );
    }

    public interface MultipleIdsMapper extends ObjectMapper<MultipleIds>, ObjectMapperTester<MultipleIds> {

        static MultipleIdsMapper INSTANCE = GWT.create( MultipleIdsMapper.class );
    }

    private VisibleTypeIdTester tester = VisibleTypeIdTester.INSTANCE;

    public void testVisibleWithProperty() {
        tester.testVisibleWithProperty( PropertyBeanMapper.INSTANCE );
    }

    public void testVisibleWithWrapperArray() {
        tester.testVisibleWithWrapperArray( WrapperArrayBeanMapper.INSTANCE );
    }

    public void testVisibleWithWrapperObject() {
        tester.testVisibleWithWrapperObject( WrapperObjectBeanMapper.INSTANCE );
    }

    public void testVisibleWithExternalId() {
        tester.testVisibleWithExternalId( ExternalIdWrapperMapper.INSTANCE );
    }

    public void testTypeIdFromProperty() {
        tester.testTypeIdFromProperty( TypeIdFromFieldPropertyMapper.INSTANCE );
    }

    public void testTypeIdFromArray() {
        tester.testTypeIdFromArray( TypeIdFromFieldArrayMapper.INSTANCE );
    }

    public void testTypeIdFromObject() {
        tester.testTypeIdFromObject( TypeIdFromMethodObjectMapper.INSTANCE );
    }

    public void testTypeIdFromExternal() {
        tester.testTypeIdFromExternal( ExternalIdWrapper2Mapper.INSTANCE );
    }

    public void testIssue263() {
        tester.testIssue263( I263BaseMapper.INSTANCE );
    }

    public void testInvalidMultipleTypeIds() {
        tester.testInvalidMultipleTypeIds( MultipleIdsMapper.INSTANCE );
    }
}
