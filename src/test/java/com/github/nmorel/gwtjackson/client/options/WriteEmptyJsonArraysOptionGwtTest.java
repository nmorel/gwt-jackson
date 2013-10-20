package com.github.nmorel.gwtjackson.client.options;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext.Builder;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester.EmptyArrayBean;
import com.github.nmorel.gwtjackson.shared.options.WriteEmptyJsonArraysOptionTester.EmptyListBean;
import com.google.gwt.core.client.GWT;

/**
 * @author Nicolas Morel
 */
public class WriteEmptyJsonArraysOptionGwtTest extends GwtJacksonTestCase {

    public interface EmptyListBeanWriter extends ObjectWriter<EmptyListBean> {

        static EmptyListBeanWriter INSTANCE = GWT.create( EmptyListBeanWriter.class );
    }

    public interface EmptyArrayBeanWriter extends ObjectWriter<EmptyArrayBean> {

        static EmptyArrayBeanWriter INSTANCE = GWT.create( EmptyArrayBeanWriter.class );
    }

    private WriteEmptyJsonArraysOptionTester tester = WriteEmptyJsonArraysOptionTester.INSTANCE;

    public void testWriteEmptyList() {
        tester.testWriteEmptyList( createWriter( EmptyListBeanWriter.INSTANCE ) );
    }

    public void testWriteEmptyArray() {
        tester.testWriteEmptyArray( createWriter( EmptyArrayBeanWriter.INSTANCE ) );
    }

    public void testWriteNonEmptyList() {
        tester.testWriteNonEmptyList( createWriter( EmptyListBeanWriter.INSTANCE, new Builder().writeEmptyJsonArrays( false ).build() ) );
    }

    public void testWriteNonEmptyArray() {
        tester.testWriteNonEmptyArray( createWriter( EmptyArrayBeanWriter.INSTANCE, new Builder().writeEmptyJsonArrays( false ).build() ) );
    }
}
