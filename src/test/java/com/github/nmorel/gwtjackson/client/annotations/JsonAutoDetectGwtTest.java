package com.github.nmorel.gwtjackson.client.annotations;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.shared.ObjectMapperTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonAutoDetectTester;
import com.github.nmorel.gwtjackson.shared.annotations.JsonAutoDetectTester.BeanOne;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class JsonAutoDetectGwtTest extends GwtJacksonTestCase {

    public interface JsonAutoDetectMapper extends ObjectMapper<BeanOne>, ObjectMapperTester<BeanOne> {

        static JsonAutoDetectMapper INSTANCE = GWT.create( JsonAutoDetectMapper.class );
    }

    private JsonAutoDetectTester tester = JsonAutoDetectTester.INSTANCE;

    public void testSerializeAutoDetection() {
        tester.testSerializeAutoDetection( JsonAutoDetectMapper.INSTANCE );
    }

    public void testDeserializeAutoDetection() {
        tester.testDeserializeAutoDetection( createMapper( JsonAutoDetectMapper.INSTANCE, new JsonDeserializationContext.Builder()
            .failOnUnknownProperties( false ).build(), newDefaultSerializationContext() ) );
    }
}
