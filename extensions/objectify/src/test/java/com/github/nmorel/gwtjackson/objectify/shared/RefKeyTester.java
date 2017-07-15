package com.github.nmorel.gwtjackson.objectify.shared;

import java.util.Map;

import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.googlecode.objectify.Ref;

public final class RefKeyTester extends ObjectifyAbstractTester {

    public static final RefKeyTester INSTANCE = new RefKeyTester();

    private static final String REF_MAP1_JSON = "" +
            "{" +
            "\"{\\\"key\\\":{\\\"raw\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1,\\\"name\\\":null}}," +
            "\\\"value\\\":null}\":null" +
            "}";

    private static final String REF_MAP1_NON_NULL_JSON = "" +
            "{" +
            "\"{\\\"key\\\":{\\\"raw\\\":{\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1}}}\":null" +
            "}";

    private static final String REF_MAP2_JSON = "" +
            "{" +
            "\"{\\\"key\\\":{\\\"raw\\\":{\\\"parent\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1,\\\"name\\\":null}," +
            "\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":0,\\\"name\\\":\\\"foo\\\"}}," +
            "\\\"value\\\":null}\":\"value2\"" +
            "}";

    private static final String REF_MAP2_NON_NULL_JSON = "" +
            "{" +
            "\"{\\\"key\\\":{\\\"raw\\\":{\\\"parent\\\":{\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1},\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":0," +
            "\\\"name\\\":\\\"foo\\\"}}}\":\"value2\"" +
            "}";

    private RefKeyTester() {
    }

    public void testSerialize( ObjectWriterTester<Map<Ref<Object>, Object>> writer ) {
        assertEquals( REF_MAP1_JSON, writer.write( REF_MAP1 ) );
        assertEquals( REF_MAP2_JSON, writer.write( REF_MAP2 ) );
    }

    public void testDeserialize( ObjectReaderTester<Map<Ref<Object>, Object>> reader ) {
        assertEquals( REF_MAP1, reader.read( REF_MAP1_JSON ) );
        assertEquals( REF_MAP2, reader.read( REF_MAP2_JSON ) );
    }

    public void testSerializeNonNull( ObjectWriterTester<Map<Ref<Object>, Object>> writer ) {
        assertEquals( REF_MAP1_NON_NULL_JSON, writer.write( REF_MAP1 ) );
        assertEquals( REF_MAP2_NON_NULL_JSON, writer.write( REF_MAP2 ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Map<Ref<Object>, Object>> reader ) {
        assertEquals( REF_MAP1, reader.read( REF_MAP1_NON_NULL_JSON ) );
        assertEquals( REF_MAP2, reader.read( REF_MAP2_NON_NULL_JSON ) );
    }
}
