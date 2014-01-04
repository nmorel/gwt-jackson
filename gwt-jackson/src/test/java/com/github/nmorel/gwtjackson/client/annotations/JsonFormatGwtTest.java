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

package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.annotations.JsonFormatTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonFormatTester.FormatDateBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonFormatGwtTest extends GwtJacksonTestCase {

    public interface JsonFormatDateMapper extends ObjectMapper<FormatDateBean> {

        static JsonFormatDateMapper INSTANCE = GWT.create( JsonFormatDateMapper.class );
    }

    private JsonFormatTester tester = JsonFormatTester.INSTANCE;

    public void testFormatDate() {
        tester.testFormatDate( createMapper( JsonFormatDateMapper.INSTANCE, newDefaultDeserializationContext(),
                new JsonSerializationContext.Builder()
                .writeDatesAsTimestamps( false ).build() ) );
    }
}
