package com.github.nmorel.gwtjackson.objectify.client;

import java.util.Map;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.RefKeyTester;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Ref;
import org.junit.Test;

public class RefKeyTestCase extends GwtJacksonObjectifyTestCase {

    @Test
    public void test() throws Exception {
        RefKeyTester.INSTANCE.testSerialize( createWriter( TestObjectMapper.INSTANCE ) );
        RefKeyTester.INSTANCE.testDeserialize( createReader( TestObjectMapper.INSTANCE ) );

        RefKeyTester.INSTANCE.testSerializeNonNull( createWriter( TestObjectMapper.INSTANCE, createNonNullContext() ) );
        RefKeyTester.INSTANCE.testDeserializeNonNull( createReader( TestObjectMapper.INSTANCE ) );
    }

    public interface TestObjectMapper extends ObjectMapper<Map<Ref<Object>, Object>> {

        TestObjectMapper INSTANCE = GWT.create( TestObjectMapper.class );
    }
}
