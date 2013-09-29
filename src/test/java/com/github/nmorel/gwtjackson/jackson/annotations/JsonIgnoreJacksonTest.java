package com.github.nmorel.gwtjackson.jackson.annotations;

import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class JsonIgnoreJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialize() {
        JsonIgnoreTester.INSTANCE.testSerialize( createEncoder( JsonIgnoreTester.BeanWithIgnoredProperties.class ) );
    }

    @Test
    public void testDeserialize() {
        JsonIgnoreTester.INSTANCE.testDeserialize( createDecoder( JsonIgnoreTester.BeanWithIgnoredProperties.class ) );
    }
}
