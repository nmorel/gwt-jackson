package com.github.nmorel.gwtjackson.jackson.advanced;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester.GenericType;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class GenericsJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializePrivateField() {
        GenericsTester.INSTANCE.testSerializeString( createEncoder( new TypeReference<GenericType<String>>() {} ) );
    }

    @Test
    public void testDeserializePrivateField() {
        GenericsTester.INSTANCE.testDeserializeString( createDecoder( new TypeReference<GenericType<String>>() {} ) );
    }
}
