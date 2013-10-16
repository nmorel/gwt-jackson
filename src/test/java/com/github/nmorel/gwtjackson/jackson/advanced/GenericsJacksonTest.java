package com.github.nmorel.gwtjackson.jackson.advanced;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester.GenericOneType;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester.GenericTwoType;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class GenericsJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializeString() {
        GenericsTester.INSTANCE.testSerializeString( createEncoder( new TypeReference<GenericOneType<String>>() {} ) );
    }

    @Test
    public void testDeserializeString() {
        GenericsTester.INSTANCE.testDeserializeString( createDecoder( new TypeReference<GenericOneType<String>>() {} ) );
    }

    @Test
    public void testSerializeStringString() {
        GenericsTester.INSTANCE.testSerializeStringString( createEncoder( new TypeReference<GenericTwoType<String, String>>() {} ) );
    }

    @Test
    public void testDeserializeStringString() {
        GenericsTester.INSTANCE.testDeserializeStringString( createDecoder( new TypeReference<GenericTwoType<String, String>>() {} ) );
    }

    @Test
    public void testSerializeIntegerString() {
        GenericsTester.INSTANCE.testSerializeIntegerString( createEncoder( new TypeReference<GenericTwoType<Integer, String>>() {} ) );
    }

    @Test
    public void testDeserializeIntegerString() {
        GenericsTester.INSTANCE.testDeserializeIntegerString( createDecoder( new TypeReference<GenericTwoType<Integer, String>>() {} ) );
    }

    @Test
    public void testSerializeIntegerGenericString() {
        GenericsTester.INSTANCE
            .testSerializeIntegerGenericString( createEncoder( new TypeReference<GenericTwoType<Integer, GenericOneType<String>>>() {} ) );
    }

    @Test
    public void testDeserializeIntegerGenericString() {
        GenericsTester.INSTANCE
            .testDeserializeIntegerGenericString( createDecoder( new TypeReference<GenericTwoType<Integer,
                GenericOneType<String>>>() {} ) );
    }
}
