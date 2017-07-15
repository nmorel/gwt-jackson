package com.github.nmorel.gwtjackson.objectify.client;

import java.util.Map;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.KeyKeyTester;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Key;
import org.junit.Test;

public class KeyKeyTestCase extends GwtJacksonObjectifyTestCase {

    @Test
    public void test() throws Exception {
        KeyKeyTester.INSTANCE.testSerialize( createWriter( TestObjectMapper.INSTANCE ) );
        KeyKeyTester.INSTANCE.testDeserialize( createReader( TestObjectMapper.INSTANCE ) );

        KeyKeyTester.INSTANCE.testSerializeNonNull( createWriter( TestObjectMapper.INSTANCE, createNonNullContext() ) );
        KeyKeyTester.INSTANCE.testDeserializeNonNull( createReader( TestObjectMapper.INSTANCE ) );
    }

    public interface TestObjectMapper extends ObjectMapper<Map<Key<Object>, Object>> {

        TestObjectMapper INSTANCE = GWT.create( TestObjectMapper.class );
    }
}
