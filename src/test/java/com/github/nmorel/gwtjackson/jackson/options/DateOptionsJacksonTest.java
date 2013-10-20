package com.github.nmorel.gwtjackson.jackson.options;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.DateOptionsTester;
import com.github.nmorel.gwtjackson.shared.options.DateOptionsTester.BeanWithDates;
import org.junit.Test;

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
}
