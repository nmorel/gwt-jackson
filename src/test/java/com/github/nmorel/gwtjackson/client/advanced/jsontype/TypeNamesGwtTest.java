package com.github.nmorel.gwtjackson.client.advanced.jsontype;

import java.util.LinkedHashMap;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.TypeNamesTester;
import com.github.nmorel.gwtjackson.shared.advanced.jsontype.TypeNamesTester.Animal;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class TypeNamesGwtTest extends GwtJacksonTestCase {

    public interface AnimalArrayMapper extends ObjectMapper<Animal[]>, ObjectMapperTester<Animal[]> {

        static AnimalArrayMapper INSTANCE = GWT.create( AnimalArrayMapper.class );
    }

    public interface AnimalMapMapper extends ObjectMapper<LinkedHashMap<String, Animal>>, ObjectMapperTester<LinkedHashMap<String,
        Animal>> {

        static AnimalMapMapper INSTANCE = GWT.create( AnimalMapMapper.class );
    }

    private TypeNamesTester tester = TypeNamesTester.INSTANCE;

    public void testSerialization() {
        tester.testSerialization( AnimalArrayMapper.INSTANCE );
    }

    public void testRoundTrip() {
        tester.testRoundTrip( AnimalArrayMapper.INSTANCE );
    }

    public void testRoundTripMap() {
        tester.testRoundTripMap( AnimalMapMapper.INSTANCE );
    }
}
