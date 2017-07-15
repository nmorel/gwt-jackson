package com.github.nmorel.gwtjackson.objectify.shared;

import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.googlecode.objectify.Key;

public final class KeyTester extends ObjectifyAbstractTester {

    public static final KeyTester INSTANCE = new KeyTester();

    private static final String KEY1_JSON = "" +
            "{" +
            "\"raw\":" +
            "{" +
            "\"parent\":null," +
            "\"kind\":\"Foo\"," +
            "\"id\":1,\"name\":null" +
            "}" +
            "}";

    private static final String KEY1_NON_NULL_JSON = "" +
            "{" +
            "\"raw\":" +
            "{" +
            "\"kind\":\"Foo\"" +
            ",\"id\":1" +
            "}" +
            "}";

    private static final String KEY2_JSON = "" +
            "{" +
            "\"raw\":" +
            "{" +
            "\"parent\":" +
            "{\"parent\":null," +
            "\"kind\":\"Foo\"," +
            "\"id\":1," +
            "\"name\":null" +
            "}," +
            "\"kind\":\"Foo\"," +
            "\"id\":0," +
            "\"name\":\"foo\"}" +
            "}";

    private static final String KEY2_NON_NULL_JSON = "" +
            "{" +
            "\"raw\":" +
            "{" +
            "\"parent\":" +
            "{\"kind\":\"Foo\"," +
            "\"id\":1}," +
            "\"kind\":\"Foo\"," +
            "\"id\":0," +
            "\"name\":\"foo\"" +
            "}" +
            "}";

    private KeyTester() {
    }

    public void testSerialize( ObjectWriterTester<Key<Object>> writer ) {
        assertEquals( KEY1_JSON, writer.write( KEY1 ) );
        assertEquals( KEY2_JSON, writer.write( KEY2 ) );
    }

    public void testDeserialize( ObjectReaderTester<Key<Object>> reader ) {
        assertEquals( KEY1, reader.read( KEY1_JSON ) );
        assertEquals( KEY2, reader.read( KEY2_JSON ) );
    }

    public void testSerializeNonNull( ObjectWriterTester<Key<Object>> writer ) {
        assertEquals( KEY1_NON_NULL_JSON, writer.write( KEY1 ) );
        assertEquals( KEY2_NON_NULL_JSON, writer.write( KEY2 ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Key<Object>> reader ) {
        assertEquals( KEY1, reader.read( KEY1_NON_NULL_JSON ) );
        assertEquals( KEY2, reader.read( KEY2_NON_NULL_JSON ) );
    }
}
