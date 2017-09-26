package com.github.nmorel.gwtjackson.remotelogging.client;

import java.io.IOException;

import com.github.nmorel.gwtjackson.client.GwtJacksonTestCase;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.ObjectWriter;
import com.github.nmorel.gwtjackson.remotelogging.shared.ThrowableTester;
import com.google.gwt.core.client.GWT;
import org.junit.Test;

public class ThrowableTestCase extends GwtJacksonTestCase {

    private ThrowableTester tester = ThrowableTester.INSTANCE;

    @Override
    public String getModuleName() {
        return "com.github.nmorel.gwtjackson.remotelogging.GwtJacksonRemoteLoggingTest";
    }

    @Test
    public void testThrowable() throws IOException {
        tester.testSerialize( createWriter( ThrowableWriter.INSTANCE ) );

        tester.testSerializeNonNull( createWriter( ThrowableWriter.INSTANCE, createNonNullContext() ) );
    }

    public interface ThrowableWriter extends ObjectWriter<Throwable> {

        ThrowableWriter INSTANCE = GWT.create( ThrowableWriter.class );
    }

    private JsonSerializationContext createNonNullContext() {
        return JsonSerializationContext.builder().serializeNulls( false ).build();
    }
}
