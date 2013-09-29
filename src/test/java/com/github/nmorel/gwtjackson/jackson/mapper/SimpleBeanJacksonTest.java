package com.github.nmorel.gwtjackson.jackson.mapper;

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
        SimpleBeanJsonMapperTester.INSTANCE.testSerializeValue( createEncoder( SimpleBean.class ) );
    }

    @Test
    public void testDeserializeValue() {
        SimpleBeanJsonMapperTester.INSTANCE.testDeserializeValue( createDecoder( SimpleBean.class ) );
    }

    @Test
    public void testWriteBeanWithNullProperties() {
        SimpleBeanJsonMapperTester.INSTANCE.testWriteWithNullProperties( createEncoder( SimpleBean.class ) );
    }
}
