package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester.GenericOneType;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester.GenericTwoType;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class GenericsGwtTest extends GwtJacksonTestCase {

    public interface GenericTypeStringMapper extends ObjectMapper<GenericOneType<String>>, ObjectMapperTester<GenericOneType<String>> {

        static GenericTypeStringMapper INSTANCE = GWT.create( GenericTypeStringMapper.class );
    }

    public interface GenericTypeStringStringMapper extends ObjectMapper<GenericTwoType<String, String>>,
        ObjectMapperTester<GenericTwoType<String, String>> {

        static GenericTypeStringStringMapper INSTANCE = GWT.create( GenericTypeStringStringMapper.class );
    }

    public interface GenericTypeIntegerStringMapper extends ObjectMapper<GenericTwoType<Integer, String>>,
        ObjectMapperTester<GenericTwoType<Integer, String>> {

        static GenericTypeIntegerStringMapper INSTANCE = GWT.create( GenericTypeIntegerStringMapper.class );
    }

    public interface GenericTypeIntegerGenericStringMapper extends ObjectMapper<GenericTwoType<Integer, GenericOneType<String>>>,
        ObjectMapperTester<GenericTwoType<Integer, GenericOneType<String>>> {

        static GenericTypeIntegerGenericStringMapper INSTANCE = GWT.create( GenericTypeIntegerGenericStringMapper.class );
    }

    private GenericsTester tester = GenericsTester.INSTANCE;

    public void testSerializeString() {
        tester.testSerializeString( GenericTypeStringMapper.INSTANCE );
    }

    public void testDeserializeString() {
        tester.testDeserializeString( GenericTypeStringMapper.INSTANCE );
    }

    public void testSerializeStringString() {
        tester.testSerializeStringString( GenericTypeStringStringMapper.INSTANCE );
    }

    public void testDeserializeStringString() {
        tester.testDeserializeStringString( GenericTypeStringStringMapper.INSTANCE );
    }

    public void testSerializeIntegerString() {
        tester.testSerializeIntegerString( GenericTypeIntegerStringMapper.INSTANCE );
    }

    public void testDeserializeIntegerString() {
        tester.testDeserializeIntegerString( GenericTypeIntegerStringMapper.INSTANCE );
    }

    public void testSerializeIntegerGenericString() {
        tester.testSerializeIntegerGenericString( GenericTypeIntegerGenericStringMapper.INSTANCE );
    }

    public void testDeserializeIntegerGenericString() {
        tester.testDeserializeIntegerGenericString( GenericTypeIntegerGenericStringMapper.INSTANCE );
    }
}
