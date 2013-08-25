package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.stream.JsonReaderTest;
import com.github.nmorel.gwtjackson.client.stream.JsonWriterTest;
import com.github.nmorel.gwtjackson.client.test.BaseTypeTest;
import com.github.nmorel.gwtjackson.client.test.MapperTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/** @author Nicolas Morel */
public class GwtJacksonTestSuite extends TestCase
{
    public static Test suite()
    {
        GWTTestSuite suite = new GWTTestSuite();

        // Stream - tests from gson
        suite.addTestSuite( JsonReaderTest.class );
        suite.addTestSuite( JsonWriterTest.class );

        suite.addTestSuite( MapperTest.class );
        suite.addTestSuite( BaseTypeTest.class );

        return suite;
    }
}
