package com.github.nmorel.gwtjackson.remotelogging.shared;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;

public final class ThrowableTester extends AbstractTester {

    public static final ThrowableTester INSTANCE = new ThrowableTester();

    public static final String THROWABLE_JSON = "" +
            "{" +
            "\"@class\":\"java.lang.IllegalArgumentException\"," +
            "\"cause\":" +
            "{" +
            "\"@class\":\"java.lang.NullPointerException\"," +
            "\"cause\":null," +
            "\"stackTrace\":[]," +
            "\"message\":null," +
            "\"localizedMessage\":null" +
            "}," +
            "\"stackTrace\":" +
            "[" +
            "{" +
            "\"methodName\":\"method0\"," +
            "\"fileName\":\"Class0.java\"," +
            "\"lineNumber\":0," +
            "\"className\":\"com.Class0\"" +
            "}," +
            "{" +
            "\"methodName\":\"method1\"," +
            "\"fileName\":\"Class1.java\"," +
            "\"lineNumber\":1," +
            "\"className\":\"com.Class1\"" +
            "}" +
            "]," +
            "\"message\":\"Var cannot be null.\"," +
            "\"localizedMessage\":\"Var cannot be null.\"" +
            "}";

    public static final String THROWABLE_NON_NULL_JSON = THROWABLE_JSON;

    public static final IllegalArgumentException THROWABLE;

    static {
        NullPointerException cause = new NullPointerException();
        cause.setStackTrace( new StackTraceElement[]{} );
        THROWABLE = new IllegalArgumentException( "Var cannot be null.", cause );
        StackTraceElement elt0 = new StackTraceElement( "com.Class0", "method0", "Class0.java", 0 );
        StackTraceElement elt1 = new StackTraceElement( "com.Class1", "method1", "Class1.java", 1 );
        THROWABLE.setStackTrace( new StackTraceElement[]{elt0, elt1} );
    }

    private ThrowableTester() {
    }

    public void testSerialize( ObjectWriterTester<Throwable> writer ) {
        assertEquals( THROWABLE_JSON, writer.write( THROWABLE ) );
    }

    public void testDeserialize( ObjectReaderTester<Throwable> reader ) {
        Throwable throwable = reader.read( THROWABLE_JSON );
        assertTrue( throwable instanceof IllegalArgumentException );
        assertEquals( "Var cannot be null.", throwable.getMessage() );
    }

    public void testSerializeNonNull( ObjectWriterTester<Throwable> writer ) {
        assertEquals( THROWABLE_NON_NULL_JSON, writer.write( THROWABLE ) );
    }

    public void testDeserializeNonNull( ObjectReaderTester<Throwable> reader ) {
        Throwable throwable = reader.read( THROWABLE_NON_NULL_JSON );
        assertTrue( throwable instanceof IllegalArgumentException );
        assertEquals( "Var cannot be null.", throwable.getMessage() );
    }
}
