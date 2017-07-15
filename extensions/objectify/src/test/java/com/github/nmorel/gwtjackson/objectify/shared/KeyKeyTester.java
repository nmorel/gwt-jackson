package com.github.nmorel.gwtjackson.objectify.shared;

import java.util.Map;

import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.googlecode.objectify.Key;

public final class KeyKeyTester extends ObjectifyAbstractTester {

    public static final KeyKeyTester INSTANCE = new KeyKeyTester();

    private static final String KEY_MAP1_JSON = "" +
            "{" +
            "\"{\\\"raw\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1,\\\"name\\\":null}}\":null" +
            "}";

    private static final String KEY_MAP1_NON_NULL_JSON = "" +
            "{" +
            "\"{\\\"raw\\\":{\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1}}\":null" +
            "}";

    private static final String KEY_MAP2_JSON = "" +
            "{\"{\\\"raw\\\":{\\\"parent\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1,\\\"name\\\":null}," +
            "\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":0,\\\"name\\\":\\\"foo\\\"}}\":\"value2\"" +
            "}";

    private static final String KEY_MAP2_NON_NULL_JSON = "" +
            "{\"{\\\"raw\\\":{\\\"parent\\\":{\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1},\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":0," +
            "\\\"name\\\":\\\"foo\\\"}}\":\"value2\"" +
            "}";

    private KeyKeyTester() {
    }

    public void testSerialize( ObjectWriterTester<Map<Key<Object>, Object>> writer ) {
        assertEquals( KEY_MAP1_JSON, writer.write( KEY_MAP1 ) );
        assertEquals( KEY_MAP2_JSON, writer.write( KEY_MAP2 ) );
    }

    public void testDeserialize( ObjectReaderTester<Map<Key<Object>, Object>> reader ) {
        assertEquals( KEY_MAP1, reader.read( KEY_MAP1_JSON ) );
        assertEquals( KEY_MAP2, reader.read( KEY_MAP2_JSON ) );
    }

    public void testSerializeNonNull( ObjectWriterTester<Map<Key<Object>, Object>> writer ) {
        assertEquals( KEY_MAP1_NON_NULL_JSON, writer.write( KEY_MAP1 ) );
        assertEquals( KEY_MAP2_NON_NULL_JSON, writer.write( KEY_MAP2 ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Map<Key<Object>, Object>> reader ) {
        assertEquals( KEY_MAP1, reader.read( KEY_MAP1_NON_NULL_JSON ) );
        assertEquals( KEY_MAP2, reader.read( KEY_MAP2_NON_NULL_JSON ) );
    }
}
