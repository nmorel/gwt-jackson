package com.github.nmorel.gwtjackson.jackson.annotations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonAutoDetectTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonAutoDetectJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializeAutoDetection() {
        JsonAutoDetectTester.INSTANCE.testSerializeAutoDetection( createEncoder( JsonAutoDetectTester.BeanOne.class ) );
    }

    @Test
    public void testDeserializeAutoDetection() {
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        JsonAutoDetectTester.INSTANCE.testDeserializeAutoDetection( createDecoder( JsonAutoDetectTester.BeanOne.class ) );
    }
}
