package com.github.nmorel.gwtjackson.jackson.options;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.OrderMapEntriesByKeysOptionTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class OrderMapEntriesByKeysOptionJacksonTest extends AbstractJacksonTest {

    @Test
    public void testWriteUnordered() {
        OrderMapEntriesByKeysOptionTester.INSTANCE
            .testWriteUnordered( createWriter( new TypeReference<LinkedHashMap<String, Integer>>() {} ) );
    }

    @Test
    public void testWriteOrdered() {
        objectMapper.configure( SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true );
        OrderMapEntriesByKeysOptionTester.INSTANCE
            .testWriteOrdered( createWriter( new TypeReference<LinkedHashMap<String, Integer>>() {} ) );
    }
}
