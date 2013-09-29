package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTypeTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTypeTester.NonIgnoredType;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonIgnoreTypeGwtTest extends GwtJacksonTestCase {

    public interface JsonIgnoreTypeMapper extends ObjectMapper<NonIgnoredType>, ObjectMapperTester<NonIgnoredType> {

        static JsonIgnoreTypeMapper INSTANCE = GWT.create( JsonIgnoreTypeMapper.class );
    }

    private JsonIgnoreTypeTester tester = JsonIgnoreTypeTester.INSTANCE;

    public void testSerialize() {
        tester.testSerialize( JsonIgnoreTypeMapper.INSTANCE );
    }

    public void testDeserialize() {
        tester.testDeserialize( JsonIgnoreTypeMapper.INSTANCE );
    }
}
