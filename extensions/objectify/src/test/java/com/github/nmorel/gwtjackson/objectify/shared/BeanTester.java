package com.github.nmorel.gwtjackson.objectify.shared;

import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

public final class BeanTester extends ObjectifyAbstractTester {

    public static final BeanTester INSTANCE = new BeanTester();

    private static final String BEAN_JSON = "" +
            "{" +
            "\"id\":1," +
            "\"string\":\"string1\"," +
            "\"set\":[1]" +
            "}";

    private static final String BEAN_NON_NULL_JSON = BEAN_JSON;

    private BeanTester() {
    }

    public void testSerialize( ObjectWriterTester<Bean> writer ) {
        assertEquals( BEAN_JSON, writer.write( BEAN ) );
    }

    public void testDeserialize( ObjectReaderTester<Bean> reader ) {
        assertEquals( BEAN, reader.read( BEAN_JSON ) );
    }

    public void testSerializeNonNull( ObjectWriterTester<Bean> writer ) {
        assertEquals( BEAN_NON_NULL_JSON, writer.write( BEAN ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Bean> reader ) {
        assertEquals( BEAN, reader.read( BEAN_NON_NULL_JSON ) );
    }
}
