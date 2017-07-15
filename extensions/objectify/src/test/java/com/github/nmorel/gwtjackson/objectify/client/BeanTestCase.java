package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.BeanTester;
import com.github.nmorel.gwtjackson.objectify.shared.ObjectifyAbstractTester.Bean;
import com.google.gwt.core.client.GWT;
import org.junit.Test;

public class BeanTestCase extends GwtJacksonObjectifyTestCase {

    @Test
    public void test() throws Exception {
        BeanTester.INSTANCE.testSerialize( createWriter( TestObjectMapper.INSTANCE ) );
        BeanTester.INSTANCE.testDeserialize( createReader( TestObjectMapper.INSTANCE ) );

        BeanTester.INSTANCE.testSerializeNonNull( createWriter( TestObjectMapper.INSTANCE, createNonNullContext() ) );
        BeanTester.INSTANCE.testDeserializeNonNull( createReader( TestObjectMapper.INSTANCE ) );
    }

    public interface TestObjectMapper extends ObjectMapper<Bean> {

        TestObjectMapper INSTANCE = GWT.create( TestObjectMapper.class );
    }
}
