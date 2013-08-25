package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.stream.JsonReaderTestGwt;
import com.github.nmorel.gwtjackson.client.stream.JsonWriterTestGwt;
import com.github.nmorel.gwtjackson.client.test.MapperTestGwt;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/** @author Nicolas Morel */
public class GwtJacksonTestSuite extends TestCase
{
    public static Test suite()
    {
        GWTTestSuite suite = new GWTTestSuite();

        // Mappers
        suite.addTestSuite( MapperTestGwt.class );

        // Stream - tests from gson
        suite.addTestSuite( JsonReaderTestGwt.class );
        suite.addTestSuite( JsonWriterTestGwt.class );

        return suite;
    }
}
