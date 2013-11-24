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

package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
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
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class VisibleTypeIdJacksonTest extends AbstractJacksonTest {

    @Test
    public void testVisibleWithProperty() {
        VisibleTypeIdTester.INSTANCE.testVisibleWithProperty( createMapper( PropertyBean.class ) );
    }

    @Test
    public void testVisibleWithWrapperArray() {
        VisibleTypeIdTester.INSTANCE.testVisibleWithWrapperArray( createMapper( WrapperArrayBean.class ) );
    }

    @Test
    public void testVisibleWithWrapperObject() {
        VisibleTypeIdTester.INSTANCE.testVisibleWithWrapperObject( createMapper( WrapperObjectBean.class ) );
    }

    @Test
    public void testVisibleWithExternalId() {
        VisibleTypeIdTester.INSTANCE.testVisibleWithExternalId( createMapper( ExternalIdWrapper.class ) );
    }

    @Test
    public void testTypeIdFromProperty() {
        VisibleTypeIdTester.INSTANCE.testTypeIdFromProperty( createMapper( TypeIdFromFieldProperty.class ) );
    }

    @Test
    public void testTypeIdFromArray() {
        VisibleTypeIdTester.INSTANCE.testTypeIdFromArray( createMapper( TypeIdFromFieldArray.class ) );
    }

    @Test
    public void testTypeIdFromObject() {
        VisibleTypeIdTester.INSTANCE.testTypeIdFromObject( createMapper( TypeIdFromMethodObject.class ) );
    }

    @Test
    public void testTypeIdFromExternal() {
        VisibleTypeIdTester.INSTANCE.testTypeIdFromExternal( createMapper( ExternalIdWrapper2.class ) );
    }

    @Test
    public void testIssue263() {
        VisibleTypeIdTester.INSTANCE.testIssue263( createMapper( I263Base.class ) );
    }

    @Test
    public void testInvalidMultipleTypeIds() {
        VisibleTypeIdTester.INSTANCE.testInvalidMultipleTypeIds( createMapper( MultipleIds.class ) );
    }
}
