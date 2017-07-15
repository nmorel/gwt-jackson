package com.github.nmorel.gwtjackson.objectify.shared;

import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.impl.ref.DeadRef;

public final class RefTester extends ObjectifyAbstractTester {

    public static final RefTester INSTANCE = new RefTester();

    private static final String REF1_JSON = "" +
            "{" +
            "\"key\":" +
            "{" +
            "\"raw\":" +
            "{" +
            "\"parent\":null," +
            "\"kind\":\"Foo\"," +
            "\"id\":1," +
            "\"name\":null" +
            "}" +
            "}," +
            "\"value\":null" +
            "}";

    private static final String REF1_NON_NULL_JSON = "" +
            "{" +
            "\"key\":" +
            "{" +
            "\"raw\":" +
            "{" +
            "\"kind\":\"Foo\"," +
            "\"id\":1" +
            "}" +
            "}" +
            "}";

    private static final String REF2_JSON = "" +
            "{" +
            "\"key\":" +
            "{\"raw\":" +
            "{" +
            "\"parent\":" +
            "{\"parent\":null," +
            "\"kind\":\"Foo\"," +
            "\"id\":1," +
            "\"name\":null" +
            "}," +
            "\"kind\":\"Foo\"," +
            "\"id\":0," +
            "\"name\":\"foo\"" +
            "}" +
            "}," +
            "\"value\":null" +
            "}";

    private static final String REF2_NON_NULL_JSON = "" +
            "{\"key\":" +
            "{" +
            "\"raw\":" +
            "{" +
            "\"parent\":" +
            "{" +
            "\"kind\":\"Foo\"," +
            "\"id\":1}," +
            "\"kind\":\"Foo\"," +
            "\"id\":0," +
            "\"name\":\"foo\"" +
            "}" +
            "}" +
            "}";

    private RefTester() {
    }

    public void testSerialize( ObjectWriterTester<Ref<Object>> writer ) {
        assertEquals( REF1_JSON, writer.write( REF1 ) );
        assertEquals( REF2_JSON, writer.write( REF2 ) );
    }

    public void testDeserialize( ObjectReaderTester<Ref<Object>> reader ) {
        Ref<Object> result1 = reader.read( REF1_JSON );
        assertEquals( REF1, result1 );
        assertTrue( result1 instanceof DeadRef );
        assertNull( result1.getValue() );

        Ref<Object> result2 = reader.read( REF2_JSON );
        assertEquals( REF2, result2 );
        assertTrue( result2 instanceof DeadRef );
        assertNull( result2.getValue() );
    }

    public void testSerializeNonNull( ObjectWriterTester<Ref<Object>> writer ) {
        assertEquals( REF1_NON_NULL_JSON, writer.write( REF1 ) );
        assertEquals( REF2_NON_NULL_JSON, writer.write( REF2 ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Ref<Object>> reader ) {
        assertEquals( REF1, reader.read( REF1_NON_NULL_JSON ) );
        assertEquals( REF2, reader.read( REF2_NON_NULL_JSON ) );
    }
}
