package com.github.nmorel.gwtjackson.jackson.options;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.WriteNullMapValuesOptionTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class WriteNullMapValuesOptionJacksonTest extends AbstractJacksonTest {

    @Test
    public void testWriteNullValues() {
        WriteNullMapValuesOptionTester.INSTANCE.testWriteNullValues( createWriter( new TypeReference<Map<String, String>>() {} ) );
    }

    @Test
    public void testWriteNonNullValues() {
        objectMapper.configure( SerializationFeature.WRITE_NULL_MAP_VALUES, false );
        WriteNullMapValuesOptionTester.INSTANCE.testWriteNonNullValues( createWriter( new TypeReference<Map<String, String>>() {} ) );
    }
}
