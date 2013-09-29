package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTypeTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonIgnoreTypeTester.NonIgnoredType;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonIgnoreTypeGwtTest extends GwtJacksonTestCase {

    public interface JsonIgnoreTypeMapper extends ObjectMapper<NonIgnoredType>, JsonMapperTester<NonIgnoredType> {

        static JsonIgnoreTypeMapper INSTANCE = GWT.create( JsonIgnoreTypeMapper.class );
    }

    private JsonIgnoreTypeTester tester = JsonIgnoreTypeTester.INSTANCE;

    public void testEncode() {
        tester.testEncode( JsonIgnoreTypeMapper.INSTANCE );
    }

    public void testDecode() {
        tester.testDecode( JsonIgnoreTypeMapper.INSTANCE );
    }
}
