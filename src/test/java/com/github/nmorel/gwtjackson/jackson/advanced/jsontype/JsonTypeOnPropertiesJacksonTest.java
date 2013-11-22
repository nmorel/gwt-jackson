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
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanArray;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanList;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.FieldWrapperBeanMap;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanArray;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanList;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnPropertiesTester.MethodWrapperBeanMap;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonTypeOnPropertiesJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSimpleField() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleField( createMapper( FieldWrapperBean.class ) );
    }

    @Test
    public void testSimpleMethod() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleMethod( createMapper( MethodWrapperBean.class ) );
    }

    @Test
    public void testSimpleListField() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleListField( createMapper( FieldWrapperBeanList.class ) );
    }

    @Test
    public void testSimpleListMethod() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleListMethod( createMapper( MethodWrapperBeanList.class ) );
    }

    @Test
    public void testSimpleArrayField() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleArrayField( createMapper( FieldWrapperBeanArray.class ) );
    }

    @Test
    public void testSimpleArrayMethod() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleArrayMethod( createMapper( MethodWrapperBeanArray.class ) );
    }

    @Test
    public void testSimpleMapField() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleMapField( createMapper( FieldWrapperBeanMap.class ) );
    }

    @Test
    public void testSimpleMapMethod() {
        JsonTypeOnPropertiesTester.INSTANCE.testSimpleMapMethod( createMapper( MethodWrapperBeanMap.class ) );
    }
}
