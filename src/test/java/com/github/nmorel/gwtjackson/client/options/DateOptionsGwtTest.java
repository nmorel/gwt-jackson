package com.github.nmorel.gwtjackson.client.options;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext.Builder;
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
        tester.testSerializeDatesAsTimestamps( createWriter( BeanWithDatesMapper.INSTANCE, new Builder().writeDatesAsTimestamps( true )
            .writeDateKeysAsTimestamps( true ).build() ) );
    }

    public void testDeserializeDatesAsTimestamps() {
        tester.testDeserializeDatesAsTimestamps( createReader( BeanWithDatesMapper.INSTANCE ) );
    }

    public void testSerializeDatesNotAsTimestamps() {
        tester.testSerializeDatesNotAsTimestamps( createWriter( BeanWithDatesMapper.INSTANCE, new Builder().writeDatesAsTimestamps( false )
            .writeDateKeysAsTimestamps( false ).build() ) );
    }

    public void testDeserializeDatesNotAsTimestamps() {
        tester.testDeserializeDatesNotAsTimestamps( createReader( BeanWithDatesMapper.INSTANCE ) );
    }
}
