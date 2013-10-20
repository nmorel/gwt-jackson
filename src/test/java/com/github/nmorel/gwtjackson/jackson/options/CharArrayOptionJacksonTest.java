package com.github.nmorel.gwtjackson.jackson.options;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.CharArrayOptionTester;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class CharArrayOptionJacksonTest extends AbstractJacksonTest {

    @Test
    public void testCharArraysAsString() {
        CharArrayOptionTester.INSTANCE.testCharArraysAsString( createMapper( char[].class ) );
    }

    @Test
    public void testCharArraysAsArray() {
        objectMapper.configure( SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true );
        CharArrayOptionTester.INSTANCE.testCharArraysAsArray( createMapper( char[].class ) );
    }
}
