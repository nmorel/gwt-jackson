package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonAutoDetectTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class JsonAutoDetectJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncodingAutoDetection()
    {
        JsonAutoDetectTester.INSTANCE.testEncodingAutoDetection( createEncoder( JsonAutoDetectTester.BeanOne.class ) );
    }

    @Test
    public void testDecodingAutoDetection()
    {
        JsonAutoDetectTester.INSTANCE.testDecodingAutoDetection( createDecoder( JsonAutoDetectTester.BeanOne.class ) );
    }
}
