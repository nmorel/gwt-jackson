package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.KeyTester;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Key;
import org.junit.Test;

public class KeyTestCase extends GwtJacksonObjectifyTestCase {

    @Test
    public void test() throws Exception {
        KeyTester.INSTANCE.testSerialize( createWriter( TestObjectMapper.INSTANCE ) );
        KeyTester.INSTANCE.testDeserialize( createReader( TestObjectMapper.INSTANCE ) );

        KeyTester.INSTANCE.testSerializeNonNull( createWriter( TestObjectMapper.INSTANCE, createNonNullContext() ) );
        KeyTester.INSTANCE.testDeserializeNonNull( createReader( TestObjectMapper.INSTANCE ) );
    }

    public interface TestObjectMapper extends ObjectMapper<Key<Object>> {

        TestObjectMapper INSTANCE = GWT.create( TestObjectMapper.class );
    }
}
