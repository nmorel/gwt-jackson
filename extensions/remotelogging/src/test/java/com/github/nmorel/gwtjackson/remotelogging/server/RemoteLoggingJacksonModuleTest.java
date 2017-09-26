package com.github.nmorel.gwtjackson.remotelogging.server;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.jackson.AbstractJacksonTest;
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
        ThrowableTester.INSTANCE.testSerialize( createWriter( Throwable.class ) );
        ThrowableTester.INSTANCE.testDeserialize( createReader( Throwable.class ) );

        objectMapper.setSerializationInclusion( Include.NON_NULL );
        ThrowableTester.INSTANCE.testSerializeNonNull( createWriter( Throwable.class ) );
        ThrowableTester.INSTANCE.testDeserializeNonNull( createReader( Throwable.class ) );
    }
}
