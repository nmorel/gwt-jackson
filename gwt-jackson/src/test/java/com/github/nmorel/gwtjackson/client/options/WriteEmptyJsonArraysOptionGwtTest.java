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

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext.Builder;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester.EmptyArrayBean;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester.EmptyListBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class WriteEmptyJsonArraysOptionGwtTest extends GwtJacksonTestCase {

    public interface EmptyListBeanWriter extends ObjectWriter<EmptyListBean> {

        static EmptyListBeanWriter INSTANCE = GWT.create( EmptyListBeanWriter.class );
    }

    public interface EmptyArrayBeanWriter extends ObjectWriter<EmptyArrayBean> {

        static EmptyArrayBeanWriter INSTANCE = GWT.create( EmptyArrayBeanWriter.class );
    }

    private WriteEmptyJsonArraysOptionTester tester = WriteEmptyJsonArraysOptionTester.INSTANCE;

    public void testWriteEmptyList() {
        tester.testWriteEmptyList( createWriter( EmptyListBeanWriter.INSTANCE ) );
    }

    public void testWriteEmptyArray() {
        tester.testWriteEmptyArray( createWriter( EmptyArrayBeanWriter.INSTANCE ) );
    }

    public void testWriteNonEmptyList() {
        tester.testWriteNonEmptyList( createWriter( EmptyListBeanWriter.INSTANCE, new Builder().writeEmptyJsonArrays( false ).build() ) );
    }

    public void testWriteNonEmptyArray() {
        tester.testWriteNonEmptyArray( createWriter( EmptyArrayBeanWriter.INSTANCE, new Builder().writeEmptyJsonArrays( false ).build() ) );
    }
}
