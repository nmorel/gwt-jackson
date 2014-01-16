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

package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnParametersTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnParametersTester.FieldWrapperBean;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.JsonTypeOnParametersTester.FieldWrapperBeanList;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonTypeOnParametersJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSimpleField() {
        JsonTypeOnParametersTester.INSTANCE.testSimpleField( createMapper( FieldWrapperBean.class ) );
    }

    @Test
    public void testSimpleListField() {
        JsonTypeOnParametersTester.INSTANCE.testSimpleListField( createMapper( FieldWrapperBeanList.class ) );
    }
}
