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

package com.github.nmorel.gwtjackson.jackson.options;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester.EmptyArrayBean;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester.EmptyListBean;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class WriteEmptyJsonArraysOptionJacksonTest extends AbstractJacksonTest {

    @Test
    public void testWriteEmptyList() {
        WriteEmptyJsonArraysOptionTester.INSTANCE.testWriteEmptyList( createWriter( EmptyListBean.class ) );
    }

    @Test
    public void testWriteEmptyArray() {
        WriteEmptyJsonArraysOptionTester.INSTANCE.testWriteEmptyArray( createWriter( EmptyArrayBean.class ) );
    }

    @Test
    public void testWriteNonEmptyList() {
        objectMapper.configure( SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false );
        WriteEmptyJsonArraysOptionTester.INSTANCE.testWriteNonEmptyList( createWriter( EmptyListBean.class ) );
    }

    @Test
    public void testWriteNonEmptyArray() {
        objectMapper.configure( SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false );
        WriteEmptyJsonArraysOptionTester.INSTANCE.testWriteNonEmptyArray( createWriter( EmptyArrayBean.class ) );
    }
}
