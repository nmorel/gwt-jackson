package com.github.nmorel.gwtjackson.client.advanced;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester;
import com.github.nmorel.gwtjackson.shared.advanced.GenericsTester.GenericType;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class GenericsGwtTest extends GwtJacksonTestCase {

    public interface GenericTypeStringMapper extends ObjectMapper<GenericType<String>>, ObjectMapperTester<GenericType<String>> {

        static GenericTypeStringMapper INSTANCE = GWT.create( GenericTypeStringMapper.class );
    }

    private GenericsTester tester = GenericsTester.INSTANCE;

    public void testSerializePrivateField() {
        tester.testSerializeString( GenericTypeStringMapper.INSTANCE );
    }

    public void testDeserializePrivateField() {
        tester.testDeserializeString( GenericTypeStringMapper.INSTANCE );
    }
}
