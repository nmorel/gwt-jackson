package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.JsonMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonAutoDetectTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonAutoDetectTester.BeanOne;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonAutoDetectGwtTest extends GwtJacksonTestCase {

    public interface JsonAutoDetectMapper extends ObjectMapper<BeanOne>, JsonMapperTester<BeanOne> {

        static JsonAutoDetectMapper INSTANCE = GWT.create( JsonAutoDetectMapper.class );
    }

    private JsonAutoDetectTester tester = JsonAutoDetectTester.INSTANCE;

    public void testEncodingAutoDetection() {
        tester.testEncodingAutoDetection( JsonAutoDetectMapper.INSTANCE );
    }

    public void testDecodingAutoDetection() {
        tester.testDecodingAutoDetection( JsonAutoDetectMapper.INSTANCE );
    }
}
