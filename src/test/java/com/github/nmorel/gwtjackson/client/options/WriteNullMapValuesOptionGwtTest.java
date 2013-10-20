package com.github.nmorel.gwtjackson.client.options;

import java.util.Map;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.options.WriteNullMapValuesOptionTester;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class WriteNullMapValuesOptionGwtTest extends GwtJacksonTestCase {

    public interface MapStringStringWriter extends ObjectWriter<Map<String, String>> {

        static MapStringStringWriter INSTANCE = GWT.create( MapStringStringWriter.class );
    }

    public void testWriteNullValues() {
        WriteNullMapValuesOptionTester.INSTANCE.testWriteNullValues( createWriter( MapStringStringWriter.INSTANCE ) );
    }

    public void testWriteNonNullValues() {
        WriteNullMapValuesOptionTester.INSTANCE
            .testWriteNonNullValues( createWriter( MapStringStringWriter.INSTANCE, new JsonSerializationContext.Builder()
                .writeNullMapValues( false ).build() ) );
    }
}
