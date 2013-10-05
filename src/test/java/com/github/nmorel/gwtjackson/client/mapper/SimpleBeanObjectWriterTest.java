package com.github.nmorel.gwtjackson.client.mapper;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.mapper.SimpleBeanJsonMapperTester;
import com.github.nmorel.gwtjackson.shared.model.SimpleBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class SimpleBeanObjectWriterTest extends GwtJacksonTestCase {

    public static interface SimpleBeanMapper extends ObjectWriter<SimpleBean> {

        static SimpleBeanMapper INSTANCE = GWT.create( SimpleBeanMapper.class );
    }

    private SimpleBeanJsonMapperTester tester = SimpleBeanJsonMapperTester.INSTANCE;

    public void testSerializeValue() {
        tester.testSerializeValue( createWriter( SimpleBeanMapper.INSTANCE ) );
    }

    public void testWriteBeanWithNullProperties() {
        tester.testWriteWithNullProperties( createWriter( SimpleBeanMapper.INSTANCE, new JsonSerializationContext.Builder()
            .serializeNulls( false ).build() ) );
    }
}
