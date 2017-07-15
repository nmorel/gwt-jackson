package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.RefTester;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Ref;
import org.junit.Test;

public class RefTestCase extends GwtJacksonObjectifyTestCase {

    @Test
    public void test() throws Exception {
        RefTester.INSTANCE.testSerialize( createWriter( TestObjectMapper.INSTANCE ) );
        RefTester.INSTANCE.testDeserialize( createReader( TestObjectMapper.INSTANCE ) );

        RefTester.INSTANCE.testSerializeNonNull( createWriter( TestObjectMapper.INSTANCE, createNonNullContext() ) );
        RefTester.INSTANCE.testDeserializeNonNull( createReader( TestObjectMapper.INSTANCE ) );
    }

    public interface TestObjectMapper extends ObjectMapper<Ref<Object>> {

        TestObjectMapper INSTANCE = GWT.create( TestObjectMapper.class );
    }
}
