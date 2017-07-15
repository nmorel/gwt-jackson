package com.github.nmorel.gwtjackson.objectify.shared;

import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.googlecode.objectify.Ref;

public final class BeanRefTester extends ObjectifyAbstractTester {

    public static final BeanRefTester INSTANCE = new BeanRefTester();

    private static final String BEAN_REF_JSON = "" +
            "{" +
            "\"key\":" +
            "{\"raw\":" +
            "{\"parent\":null," +
            "\"kind\":\"Bean\"," +
            "\"id\":1," +
            "\"name\":null" +
            "}" +
            "}," +
            "\"value\":" +
            "{" +
            "\"id\":1," +
            "\"string\":\"string1\"," +
            "\"set\":[1]" +
            "}" +
            "}";

    private static final String BEAN_REF_NON_NULL_JSON = "" +
            "{" +
            "\"key\":" +
            "{" +
            "\"raw\":" +
            "{" +
            "\"kind\":\"Bean\"," +
            "\"id\":1" +
            "}" +
            "}," +
            "\"value\":" +
            "{" +
            "\"id\":1," +
            "\"string\":\"string1\"," +
            "\"set\":[1]" +
            "}" +
            "}";

    private BeanRefTester() {
    }

    public void testSerialize( ObjectWriterTester<Ref<Bean>> writer ) {
        assertEquals( BEAN_REF_JSON, writer.write( BEAN_REF ) );
    }

    public void testDeserialize( ObjectReaderTester<Ref<Bean>> reader ) {
        Ref<Bean> result = reader.read( BEAN_REF_JSON );
        assertEquals( BEAN_REF, result );
        assertEquals( BEAN_REF.getValue(), result.getValue() );
    }

    public void testSerializeNonNull( ObjectWriterTester<Ref<Bean>> writer ) {
        assertEquals( BEAN_REF_NON_NULL_JSON, writer.write( BEAN_REF ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Ref<Bean>> reader ) {
        Ref<Bean> result = reader.read( BEAN_REF_NON_NULL_JSON );
        assertEquals( BEAN_REF, result );
        assertEquals( BEAN_REF.getValue(), result.getValue() );
    }
}
