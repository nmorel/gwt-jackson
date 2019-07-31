package com.github.nmorel.gwtjackson.remotelogging.server;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
import com.github.nmorel.gwtjackson.remotelogging.shared.RemoteThrowable;
import com.github.nmorel.gwtjackson.remotelogging.shared.ThrowableTester;
import org.junit.Before;
import org.junit.Test;

public class RemoteLoggingJacksonModuleTest extends AbstractJacksonTest {

    @Before
    public void setUp() {
        super.setUp();
        objectMapper.registerModule( new RemoteLoggingJacksonModule() );
    }

    @Test
    public void testThrowable() throws IOException {
        ThrowableTester.INSTANCE.testSerializeIllegalArgumentException( createWriter( RemoteThrowable.class ) );
        ThrowableTester.INSTANCE.testDeserializeIllegalArgumentException( createReader( RemoteThrowable.class ) );

        ThrowableTester.INSTANCE.testSerializeCustomException( createWriter( RemoteThrowable.class ) );
        ThrowableTester.INSTANCE.testDeserializeCustomException( createReader( RemoteThrowable.class ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        ThrowableTester.INSTANCE.testSerializeIllegalArgumentExceptionNonNull( createWriter( RemoteThrowable.class ) );

        ThrowableTester.INSTANCE.testSerializeCustomExceptionNonNull( createWriter( RemoteThrowable.class ) );
    }
}
