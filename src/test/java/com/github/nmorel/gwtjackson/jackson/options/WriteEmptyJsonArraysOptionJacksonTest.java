package com.github.nmorel.gwtjackson.jackson.options;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester.EmptyArrayBean;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester.EmptyListBean;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class WriteEmptyJsonArraysOptionJacksonTest extends AbstractJacksonTest {

    @Test
    public void testWriteEmptyList() {
        WriteEmptyJsonArraysOptionTester.INSTANCE.testWriteEmptyList( createWriter( EmptyListBean.class ) );
    }

    @Test
    public void testWriteEmptyArray() {
        WriteEmptyJsonArraysOptionTester.INSTANCE.testWriteEmptyArray( createWriter( EmptyArrayBean.class ) );
    }

    @Test
    public void testWriteNonEmptyList() {
        objectMapper.configure( SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false );
        WriteEmptyJsonArraysOptionTester.INSTANCE.testWriteNonEmptyList( createWriter( EmptyListBean.class ) );
    }

    @Test
    public void testWriteNonEmptyArray() {
        objectMapper.configure( SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false );
        WriteEmptyJsonArraysOptionTester.INSTANCE.testWriteNonEmptyArray( createWriter( EmptyArrayBean.class ) );
    }
}
