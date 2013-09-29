package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.JsonEncoderTester;
import com.github.nmorel.gwtjackson.shared.mapper.SimpleBeanJsonMapperTester;
import com.github.nmorel.gwtjackson.shared.model.SimpleBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class SimpleBeanObjectWriterTest extends GwtJacksonTestCase {

    public static interface SimpleBeanMapper extends ObjectWriter<SimpleBean>, JsonEncoderTester<SimpleBean> {

        static SimpleBeanMapper INSTANCE = GWT.create( SimpleBeanMapper.class );
    }

    private SimpleBeanJsonMapperTester tester = SimpleBeanJsonMapperTester.INSTANCE;

    public void testEncodeValue() {
        tester.testEncodeValue( SimpleBeanMapper.INSTANCE );
    }

    public void testWriteBeanWithNullProperties() {
        tester.testWriteWithNullProperties( SimpleBeanMapper.INSTANCE );
    }
}
