package com.github.nmorel.gwtjackson.objectify.client;

import java.util.Map;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyKeyTester;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.core.client.GWT;
import org.junit.Test;

public class RawKeyKeyTestCase extends GwtJacksonObjectifyTestCase {

    @Test
    public void test() throws Exception {
        RawKeyKeyTester.INSTANCE.testSerialize( createWriter( TestObjectMapper.INSTANCE ) );
        RawKeyKeyTester.INSTANCE.testDeserialize( createReader( TestObjectMapper.INSTANCE ) );

        RawKeyKeyTester.INSTANCE.testSerializeNonNull( createWriter( TestObjectMapper.INSTANCE, createNonNullContext() ) );
        RawKeyKeyTester.INSTANCE.testDeserializeNonNull( createReader( TestObjectMapper.INSTANCE ) );
    }

    public interface TestObjectMapper extends ObjectMapper<Map<Key, Object>> {

        TestObjectMapper INSTANCE = GWT.create( TestObjectMapper.class );
    }
}
