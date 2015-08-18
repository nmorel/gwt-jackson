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

import java.util.TimeZone;

import org.junit.Test;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.DateOptionsTester;
import com.github.nmorel.gwtjackson.shared.options.DateOptionsTester.BeanWithDates;

/**
 * @author Nicolas Morel
 */
public class DateOptionsJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializeDatesAsTimestamps() {
        objectMapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true );
        objectMapper.configure( SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true );
        DateOptionsTester.INSTANCE.testSerializeDatesAsTimestamps( createWriter( BeanWithDates.class ) );
    }

    @Test
    public void testDeserializeDatesAsTimestamps() {
        DateOptionsTester.INSTANCE.testDeserializeDatesAsTimestamps( createReader( BeanWithDates.class ) );
    }

    @Test
    public void testSerializeDatesNotAsTimestamps() {
        objectMapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false );
        objectMapper.configure( SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false );
        DateOptionsTester.INSTANCE.testSerializeDatesNotAsTimestamps( createWriter( BeanWithDates.class ) );
    }

    @Test
    public void testDeserializeDatesNotAsTimestamps() {
        DateOptionsTester.INSTANCE.testDeserializeDatesNotAsTimestamps( createReader( BeanWithDates.class ) );
    }

    @Test
    public void testDeserializeDatesNotAsTimestampsAndNotUseBrowserTimezone() {
        objectMapper.setTimeZone(TimeZone.getDefault());
        DateOptionsTester.INSTANCE.testDeserializeDatesNotAsTimestampsAndUseBrowserTimezone( createReader( BeanWithDates.class ) );
    }

}
