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
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.options.DateOptionsTester;
import com.github.nmorel.gwtjackson.shared.options.DateOptionsTester.BeanWithDates;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class DateOptionsGwtTest extends GwtJacksonTestCase {

    public interface BeanWithDatesMapper extends ObjectMapper<BeanWithDates> {

        static BeanWithDatesMapper INSTANCE = GWT.create( BeanWithDatesMapper.class );
    }

    private DateOptionsTester tester = DateOptionsTester.INSTANCE;

    public void testSerializeDatesAsTimestamps() {
        tester.testSerializeDatesAsTimestamps( createWriter( BeanWithDatesMapper.INSTANCE, JsonSerializationContext.builder()
                .writeDatesAsTimestamps( true ).writeDateKeysAsTimestamps( true ).build() ) );
    }

    public void testDeserializeDatesAsTimestamps() {
        tester.testDeserializeDatesAsTimestamps( createReader( BeanWithDatesMapper.INSTANCE ) );
    }

    public void testSerializeDatesNotAsTimestamps() {
        tester.testSerializeDatesNotAsTimestamps( createWriter( BeanWithDatesMapper.INSTANCE, JsonSerializationContext.builder()
                .writeDatesAsTimestamps( false ).writeDateKeysAsTimestamps( false ).build() ) );
    }

    public void testDeserializeDatesNotAsTimestamps() {
        tester.testDeserializeDatesNotAsTimestamps( createReader( BeanWithDatesMapper.INSTANCE ) );
    }
}
