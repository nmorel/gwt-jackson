package com.github.nmorel.gwtjackson.objectify.shared;

import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.google.appengine.api.datastore.Key;

public final class RawKeyTester extends ObjectifyAbstractTester {

    public static final RawKeyTester INSTANCE = new RawKeyTester();

    private static final String RAW_KEY1_JSON = "" +
            "{" +
            "\"parent\":null," +
            "\"kind\":\"Foo\"," +
            "\"id\":1," +
            "\"name\":null" +
            "}";

    private static final String RAW_KEY1_NON_NULL_JSON = "" +
            "{" +
            "\"kind\":\"Foo\"," +
            "\"id\":1" +
            "}";

    private static final String RAW_KEY2_JSON = "" +
            "{" +
            "\"parent\":" +
            "{" +
            "\"parent\":null," +
            "\"kind\":\"Foo\"," +
            "\"id\":1," +
            "\"name\":null" +
            "}," +
            "\"kind\":\"Foo\"," +
            "\"id\":0," +
            "\"name\":\"foo\"" +
            "}";

    private static final String RAW_KEY2_NON_NULL_JSON = "" +
            "{" +
            "\"parent\":" +
            "{" +
            "\"kind\":\"Foo\"," +
            "\"id\":1" +
            "}," +
            "\"kind\":\"Foo\"," +
            "\"id\":0," +
            "\"name\":\"foo\"" +
            "}";

    private RawKeyTester() {
    }

    public void testSerialize( ObjectWriterTester<Key> writer ) {
        assertEquals( RAW_KEY1_JSON, writer.write( RAW_KEY1 ) );
        assertEquals( RAW_KEY2_JSON, writer.write( RAW_KEY2 ) );
    }

    public void testDeserialize( ObjectReaderTester<Key> reader ) {
        assertEquals( RAW_KEY1, reader.read( RAW_KEY1_JSON ) );
        assertEquals( RAW_KEY2, reader.read( RAW_KEY2_JSON ) );
    }

    public void testSerializeNonNull( ObjectWriterTester<Key> writer ) {
        assertEquals( RAW_KEY1_NON_NULL_JSON, writer.write( RAW_KEY1 ) );
        assertEquals( RAW_KEY2_NON_NULL_JSON, writer.write( RAW_KEY2 ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Key> reader ) {
        assertEquals( RAW_KEY1, reader.read( RAW_KEY1_NON_NULL_JSON ) );
        assertEquals( RAW_KEY2, reader.read( RAW_KEY2_NON_NULL_JSON ) );
    }
}
