package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.annotations.InheritanceGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonAutoDetectGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonPropertyOrderGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.PrivateAccessGwtTest;
import com.github.nmorel.gwtjackson.client.mapper.ArrayJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.BigDecimalJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.BigIntegerJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.BooleanJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.ByteJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.CharacterJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.CollectionJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.CommonJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.DateJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.DoubleJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.EnumJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.FloatJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.IntegerJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.IterableJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.ListJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.LongJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.SetJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.ShortJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.SimpleBeanJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.SqlDateJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.SqlTimeJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.SqlTimestampJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.StringJsonMapperTest;
import com.github.nmorel.gwtjackson.client.stream.JsonReaderTest;
import com.github.nmorel.gwtjackson.client.stream.JsonWriterTest;
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
        suite.addTestSuite( ArrayJsonMapperTest.class );
        suite.addTestSuite( BigDecimalJsonMapperTest.class );
        suite.addTestSuite( BigIntegerJsonMapperTest.class );
        suite.addTestSuite( BooleanJsonMapperTest.class );
        suite.addTestSuite( ByteJsonMapperTest.class );
        suite.addTestSuite( CharacterJsonMapperTest.class );
        suite.addTestSuite( CollectionJsonMapperTest.class );
        suite.addTestSuite( CommonJsonMapperTest.class );
        suite.addTestSuite( DateJsonMapperTest.class );
        suite.addTestSuite( DoubleJsonMapperTest.class );
        suite.addTestSuite( EnumJsonMapperTest.class );
        suite.addTestSuite( FloatJsonMapperTest.class );
        suite.addTestSuite( IntegerJsonMapperTest.class );
        suite.addTestSuite( IterableJsonMapperTest.class );
        suite.addTestSuite( ListJsonMapperTest.class );
        suite.addTestSuite( LongJsonMapperTest.class );
        suite.addTestSuite( SetJsonMapperTest.class );
        suite.addTestSuite( ShortJsonMapperTest.class );
        suite.addTestSuite( SqlDateJsonMapperTest.class );
        suite.addTestSuite( SqlTimeJsonMapperTest.class );
        suite.addTestSuite( SqlTimestampJsonMapperTest.class );
        suite.addTestSuite( StringJsonMapperTest.class );
        suite.addTestSuite( SimpleBeanJsonMapperTest.class );

        // Annotations test
        suite.addTestSuite( JsonAutoDetectGwtTest.class );
        suite.addTestSuite( JsonPropertyOrderGwtTest.class );

        suite.addTestSuite( PrivateAccessGwtTest.class );
        suite.addTestSuite( InheritanceGwtTest.class );

        return suite;
    }
}
