package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.github.nmorel.gwtjackson.shared.JsonDecoderTester;
import com.github.nmorel.gwtjackson.shared.mapper.SimpleBeanJsonMapperTester;
import com.github.nmorel.gwtjackson.shared.model.SimpleBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class SimpleBeanObjectReaderTest extends GwtJacksonTestCase {

    public static interface SimpleBeanMapper extends ObjectReader<SimpleBean>, JsonDecoderTester<SimpleBean> {

        static SimpleBeanMapper INSTANCE = GWT.create( SimpleBeanMapper.class );
    }

    private SimpleBeanJsonMapperTester tester = SimpleBeanJsonMapperTester.INSTANCE;

    public void testDecodeValue() {
        tester.testDecodeValue( SimpleBeanMapper.INSTANCE );
    }
}
