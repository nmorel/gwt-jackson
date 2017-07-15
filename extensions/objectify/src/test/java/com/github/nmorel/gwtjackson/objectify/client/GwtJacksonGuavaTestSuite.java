package com.github.nmorel.gwtjackson.objectify.client;

import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

public class GwtJacksonGuavaTestSuite extends TestCase {

    public static Test suite() {
        GWTTestSuite suite = new GWTTestSuite();
        suite.addTestSuite( RawKeyTestCase.class );
        suite.addTestSuite( RawKeyKeyTestCase.class );
        suite.addTestSuite( KeyTestCase.class );
        suite.addTestSuite( KeyKeyTestCase.class );
        suite.addTestSuite( RefTestCase.class );
        suite.addTestSuite( BeanTestCase.class );
        suite.addTestSuite( BeanRefTestCase.class );
        return suite;
    }
}
