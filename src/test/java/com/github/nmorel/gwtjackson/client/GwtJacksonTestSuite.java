package com.github.nmorel.gwtjackson.client;

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

        // Codex
        suite.addTestSuite( MapperTestGwt.class );

        return suite;
    }
}
