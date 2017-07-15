package com.github.nmorel.gwtjackson.objectify.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.objectify.shared.BeanRefTester;
import com.github.nmorel.gwtjackson.objectify.shared.ObjectifyAbstractTester.Bean;
import com.google.gwt.core.client.GWT;
import com.googlecode.objectify.Ref;
import org.junit.Test;

public class BeanRefTestCase extends GwtJacksonObjectifyTestCase {

    @Test
    public void test() throws Exception {
        BeanRefTester.INSTANCE.testSerialize( createWriter( TestObjectMapper.INSTANCE ) );
        BeanRefTester.INSTANCE.testDeserialize( createReader( TestObjectMapper.INSTANCE ) );

        BeanRefTester.INSTANCE.testSerializeNonNull( createWriter( TestObjectMapper.INSTANCE, createNonNullContext() ) );
        BeanRefTester.INSTANCE.testDeserializeNonNull( createReader( TestObjectMapper.INSTANCE ) );
    }

    public interface TestObjectMapper extends ObjectMapper<Ref<Bean>> {

        TestObjectMapper INSTANCE = GWT.create( TestObjectMapper.class );
    }

}
