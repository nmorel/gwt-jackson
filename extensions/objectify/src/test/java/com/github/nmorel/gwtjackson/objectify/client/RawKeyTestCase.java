package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.RawKeyTester;
import com.google.appengine.api.datastore.Key;
import com.google.gwt.core.client.GWT;
import org.junit.Test;

public class RawKeyTestCase extends GwtJacksonObjectifyTestCase {

    public interface TestObjectMapper extends ObjectMapper<Key> {

        TestObjectMapper INSTANCE = GWT.create( TestObjectMapper.class );
    }

    @Test
    public void test() throws Exception {
        RawKeyTester.INSTANCE.testSerialize( createWriter( TestObjectMapper.INSTANCE ) );
        RawKeyTester.INSTANCE.testDeserialize( createReader( TestObjectMapper.INSTANCE ) );

        RawKeyTester.INSTANCE.testSerializeNonNull( createWriter( TestObjectMapper.INSTANCE, createNonNullContext() ) );
        RawKeyTester.INSTANCE.testDeserializeNonNull( createReader( TestObjectMapper.INSTANCE ) );

    }
}
