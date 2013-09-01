package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester;
import org.junit.Test;

/** @author Nicolas Morel */
public class JsonIgnoreJacksonTest extends AbstractJacksonTest
{
    @Test
    public void testEncoding()
    {
        JsonIgnoreTester.INSTANCE.testEncoding( createEncoder( JsonIgnoreTester.BeanWithIgnoredProperties.class ) );
    }

    @Test
    public void testDecoding()
    {
        JsonIgnoreTester.INSTANCE.testDecoding( createDecoder( JsonIgnoreTester.BeanWithIgnoredProperties.class ) );
    }
}
