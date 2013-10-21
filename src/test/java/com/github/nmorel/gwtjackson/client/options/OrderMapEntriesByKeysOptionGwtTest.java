package com.github.nmorel.gwtjackson.client.options;

import java.util.LinkedHashMap;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.options.OrderMapEntriesByKeysOptionTester;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class OrderMapEntriesByKeysOptionGwtTest extends GwtJacksonTestCase {

    public interface LinkedHashMapStringIntegerWriter extends ObjectWriter<LinkedHashMap<String, Integer>> {

        static LinkedHashMapStringIntegerWriter INSTANCE = GWT.create( LinkedHashMapStringIntegerWriter.class );
    }

    public void testWriteUnordered() {
        OrderMapEntriesByKeysOptionTester.INSTANCE.testWriteUnordered( createWriter( LinkedHashMapStringIntegerWriter.INSTANCE ) );
    }

    public void testWriteOrdered() {
        OrderMapEntriesByKeysOptionTester.INSTANCE
            .testWriteOrdered( createWriter( LinkedHashMapStringIntegerWriter.INSTANCE, new JsonSerializationContext.Builder()
                .orderMapEntriesByKeys( true ).build() ) );
    }
}
