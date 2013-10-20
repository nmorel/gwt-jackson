package com.github.nmorel.gwtjackson.jackson.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.mapper.SimpleBeanJsonMapperTester;
import com.github.nmorel.gwtjackson.shared.model.SimpleBean;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class SimpleBeanJacksonTest extends AbstractJacksonTest {

    @Test
    public void testSerializeValue() {
        SimpleBeanJsonMapperTester.INSTANCE.testSerializeValue( createWriter( SimpleBean.class ) );
    }

    @Test
    public void testDeserializeValue() {
        SimpleBeanJsonMapperTester.INSTANCE.testDeserializeValue( createReader( SimpleBean.class ) );
    }

    @Test
    public void testWriteBeanWithNullProperties() {
        objectMapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );
        SimpleBeanJsonMapperTester.INSTANCE.testWriteWithNullProperties( createWriter( SimpleBean.class ) );
    }
}
