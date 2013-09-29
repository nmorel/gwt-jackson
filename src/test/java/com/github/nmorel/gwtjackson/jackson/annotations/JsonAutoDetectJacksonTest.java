package com.github.nmorel.gwtjackson.jackson.annotations;

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
        JsonAutoDetectTester.INSTANCE.testDeserializeAutoDetection( createDecoder( JsonAutoDetectTester.BeanOne.class ) );
    }
}
