package com.github.nmorel.gwtjackson.remotelogging.shared;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.github.nmorel.gwtjackson.shared.ObjectReaderTester;
import com.github.nmorel.gwtjackson.shared.ObjectWriterTester;
import com.google.gwt.core.shared.GwtIncompatible;
import org.apache.commons.lang3.exception.ExceptionUtils;

public final class ThrowableTester extends AbstractTester {

    public static final ThrowableTester INSTANCE = new ThrowableTester();

    public static final String ILLEGAL_ARGUMENT_EXCEPTION_JSON = "" +
            "{" +
            "\"message\":\"Var cannot be null.\"," +
            "\"cause\":" +
            "{" +
            "\"message\":null," +
            "\"cause\":null" +
            ",\"remoteClassName\":\"java.lang.NullPointerException\"," +
            "\"stackTrace\":[]" +
            "}," +
            "\"remoteClassName\":\"java.lang.IllegalArgumentException\"," +
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
            "]" +
            "}";

    public static final String CUSTOM_EXCEPTION_JSON = "" +
            "{" +
            "\"message\":\"My custom exception.\"," +
            "\"cause\":" +
            "{" +
            "\"message\":\"Var cannot be null.\"," +
            "\"cause\":{\"message\":null," +
            "\"cause\":null," +
            "\"remoteClassName\":\"java.lang.NullPointerException\"," +
            "\"stackTrace\":[]" +
            "}," +
            "\"remoteClassName\":\"java.lang.IllegalArgumentException\"," +
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
            "]" +
            "}," +
            "\"remoteClassName\":\"com.github.nmorel.gwtjackson.remotelogging.shared.CustomException\"," +
            "\"stackTrace\":" +
            "[" +
            "{" +
            "\"methodName\":\"method2\"," +
            "\"fileName\":\"Class2.java\"," +
            "\"lineNumber\":2," +
            "\"className\":\"com.Class2\"" +
            "}," +
            "{" +
            "\"methodName\":\"method3\"," +
            "\"fileName\":\"Class3.java\"," +
            "\"lineNumber\":3," +
            "\"className\":\"com.Class3\"" +
            "}" +
            "]" +
            "}";

    public static final IllegalArgumentException ILLEGAL_ARGUMENT_EXCEPTION;

    public static final CustomException CUSTOM_EXCEPTION;

    static {
        NullPointerException cause = new NullPointerException();
        cause.setStackTrace( new StackTraceElement[]{} );
        ILLEGAL_ARGUMENT_EXCEPTION = new IllegalArgumentException( "Var cannot be null.", cause );
        StackTraceElement elt0 = new StackTraceElement( "com.Class0", "method0", "Class0.java", 0 );
        StackTraceElement elt1 = new StackTraceElement( "com.Class1", "method1", "Class1.java", 1 );
        ILLEGAL_ARGUMENT_EXCEPTION.setStackTrace( new StackTraceElement[]{elt0, elt1} );

        CUSTOM_EXCEPTION = new CustomException( "My custom exception.", ILLEGAL_ARGUMENT_EXCEPTION, "object" );
        StackTraceElement elt2 = new StackTraceElement( "com.Class2", "method2", "Class2.java", 2 );
        StackTraceElement elt3 = new StackTraceElement( "com.Class3", "method3", "Class3.java", 3 );
        CUSTOM_EXCEPTION.setStackTrace( new StackTraceElement[]{elt2, elt3} );
    }

    private ThrowableTester() {
    }

    public void testSerializeIllegalArgumentException( ObjectWriterTester<RemoteThrowable> writer ) {
        assertEquals( ILLEGAL_ARGUMENT_EXCEPTION_JSON, writer.write( new RemoteThrowable( ILLEGAL_ARGUMENT_EXCEPTION ) ) );
    }

    @GwtIncompatible
    public void testDeserializeIllegalArgumentException( ObjectReaderTester<RemoteThrowable> reader ) {
        RemoteThrowable throwable = reader.read( ILLEGAL_ARGUMENT_EXCEPTION_JSON );
        assertEquals( "" +
                "java.lang.IllegalArgumentException: Var cannot be null.\n" +
                "\tat com.Class0.method0(Class0.java:0)\n" +
                "\tat com.Class1.method1(Class1.java:1)\n" +
                "Caused by: java.lang.NullPointerException\n", ExceptionUtils.getStackTrace( throwable ) );
    }

    public void testSerializeCustomException( ObjectWriterTester<RemoteThrowable> writer ) {
        assertEquals( CUSTOM_EXCEPTION_JSON, writer.write( new RemoteThrowable( CUSTOM_EXCEPTION ) ) );
    }

    @GwtIncompatible
    public void testDeserializeCustomException( ObjectReaderTester<RemoteThrowable> reader ) {
        RemoteThrowable throwable = reader.read( CUSTOM_EXCEPTION_JSON );
        assertEquals( "" +
                "com.github.nmorel.gwtjackson.remotelogging.shared.CustomException: My custom exception.\n" +
                "\tat com.Class2.method2(Class2.java:2)\n" +
                "\tat com.Class3.method3(Class3.java:3)\n" +
                "Caused by: java.lang.IllegalArgumentException: Var cannot be null.\n" +
                "\tat com.Class0.method0(Class0.java:0)\n" +
                "\tat com.Class1.method1(Class1.java:1)\n" +
                "Caused by: java.lang.NullPointerException\n", ExceptionUtils.getStackTrace( throwable ) );
    }

    public void testSerializeIllegalArgumentExceptionNonNull( ObjectWriterTester<RemoteThrowable> writer ) {
        assertEquals( ILLEGAL_ARGUMENT_EXCEPTION_JSON, writer.write( new RemoteThrowable( ILLEGAL_ARGUMENT_EXCEPTION ) ) );
    }

    public void testSerializeCustomExceptionNonNull( ObjectWriterTester<RemoteThrowable> writer ) {
        assertEquals( CUSTOM_EXCEPTION_JSON, writer.write( new RemoteThrowable( CUSTOM_EXCEPTION ) ) );
    }
}
