package com.github.nmorel.gwtjackson.objectify.shared;

import java.util.Map;

import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.google.appengine.api.datastore.Key;

public final class RawKeyKeyTester extends ObjectifyAbstractTester {

    public static final RawKeyKeyTester INSTANCE = new RawKeyKeyTester();

    private static final String RAW_KEY_MAP1_JSON = "" +
            "{\"{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1,\\\"name\\\":null}\":null" +
            "}";

    private static final String RAW_KEY_MAP1_NON_NULL_JSON = "" +
            "{\"{\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1}\":null" +
            "}";

    private static final String RAW_KEY_MAP2_JSON = "" +
            "{\"{\\\"parent\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1,\\\"name\\\":null},\\\"kind\\\":\\\"Foo\\\"," +
            "\\\"id\\\":0,\\\"name\\\":\\\"foo\\\"}\":\"value2\"" +
            "}";

    private static final String RAW_KEY_MAP2_NON_NULL_JSON = "" +
            "{\"{\\\"parent\\\":{\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1},\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":0," +
            "\\\"name\\\":\\\"foo\\\"}\":\"value2\"" +
            "}";

    private RawKeyKeyTester() {
    }

    public void testSerialize( ObjectWriterTester<Map<Key, Object>> writer ) {
        assertEquals( RAW_KEY_MAP1_JSON, writer.write( RAW_KEY_MAP1 ) );
        assertEquals( RAW_KEY_MAP2_JSON, writer.write( RAW_KEY_MAP2 ) );
    }

    public void testDeserialize( ObjectReaderTester<Map<Key, Object>> reader ) {
        assertEquals( RAW_KEY_MAP1, reader.read( RAW_KEY_MAP1_JSON ) );
        assertEquals( RAW_KEY_MAP2, reader.read( RAW_KEY_MAP2_JSON ) );
    }

    public void testSerializeNonNull( ObjectWriterTester<Map<Key, Object>> writer ) {
        assertEquals( RAW_KEY_MAP1_NON_NULL_JSON, writer.write( RAW_KEY_MAP1 ) );
        assertEquals( RAW_KEY_MAP2_NON_NULL_JSON, writer.write( RAW_KEY_MAP2 ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Map<Key, Object>> reader ) {
        assertEquals( RAW_KEY_MAP1, reader.read( RAW_KEY_MAP1_NON_NULL_JSON ) );
        assertEquals( RAW_KEY_MAP2, reader.read( RAW_KEY_MAP2_NON_NULL_JSON ) );
    }
}
