package com.github.nmorel.gwtjackson.remotelogging.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

public class GwtJacksonRemoteLoggingTestSuite extends TestCase {

    public static Test suite() {
        GWTTestSuite suite = new GWTTestSuite();
        suite.addTestSuite( ThrowableTestCase.class );
        return suite;
    }
}
