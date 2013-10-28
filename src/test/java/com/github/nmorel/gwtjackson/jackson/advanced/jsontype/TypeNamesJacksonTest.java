package com.github.nmorel.gwtjackson.jackson.advanced.jsontype;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.TypeNamesTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.TypeNamesTester.Animal;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class TypeNamesJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerialization() {
        TypeNamesTester.INSTANCE.testSerialization( createWriter( Animal[].class ) );
    }

    @Test
    public void testRoundTrip() {
        TypeNamesTester.INSTANCE.testRoundTrip( createMapper( Animal[].class ) );
    }

    @Test
    @Ignore( "for some reasons, jackson don't add type info. It works on original jackson test with a class extending LinkedHashMap" )
    public void testRoundTripMap() {
        TypeNamesTester.INSTANCE.testRoundTripMap( createMapper( new TypeReference<LinkedHashMap<String, Animal>>() {} ) );
    }
}
