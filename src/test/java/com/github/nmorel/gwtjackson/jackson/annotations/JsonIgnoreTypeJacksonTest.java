package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTypeTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class JsonIgnoreTypeJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testDecode()
    {
        JsonIgnoreTypeTester.INSTANCE.testDecode( createDecoder( JsonIgnoreTypeTester.NonIgnoredType.class ) );
    }

    @Test
    public void testEncode()
    {
        JsonIgnoreTypeTester.INSTANCE.testEncode( createEncoder( JsonIgnoreTypeTester.NonIgnoredType.class ) );
    }
}
