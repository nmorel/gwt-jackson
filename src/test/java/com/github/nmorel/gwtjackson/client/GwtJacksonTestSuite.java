package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.mapper.BigDecimalJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.BigIntegerJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.BooleanJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.ByteJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.DoubleJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.FloatJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.IntegerJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.LongJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.ShortJsonMapperTest;
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

        // Default mappers
        suite.addTestSuite( BigDecimalJsonMapperTest.class );
        suite.addTestSuite( BigIntegerJsonMapperTest.class );
        suite.addTestSuite( BooleanJsonMapperTest.class );
        suite.addTestSuite( ByteJsonMapperTest.class );
        suite.addTestSuite( DoubleJsonMapperTest.class );
        suite.addTestSuite( FloatJsonMapperTest.class );
        suite.addTestSuite( IntegerJsonMapperTest.class );
        suite.addTestSuite( LongJsonMapperTest.class );
        suite.addTestSuite( ShortJsonMapperTest.class );

        suite.addTestSuite( MapperTest.class );
        suite.addTestSuite( BaseTypeTest.class );

        return suite;
    }
}
