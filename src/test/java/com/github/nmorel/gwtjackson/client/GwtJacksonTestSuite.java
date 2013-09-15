package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.advanced.InheritanceGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.PolymorphismIdClassAsPropertyGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.PolymorphismIdMinimalClassAsWrapperArrayGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.PolymorphismIdNameAsWrapperObjectGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.PolymorphismNoTypeInfoGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.PrivateAccessGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdDeserializationGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdSerializationGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdWithPolymorphicGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonAutoDetectGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonCreatorGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonIgnoreGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonIgnoreTypeGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonManagedAndBackReferenceGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonPropertyOrderGwtTest;
import com.github.nmorel.gwtjackson.client.mapper.BooleanJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.CharacterJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.CommonJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.EnumJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.SimpleBeanJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.StringJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.UUIDJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.array.ArrayJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.collection.AllCollectionsJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.collection.CollectionJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.collection.IterableJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.collection.ListJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.collection.SetJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.date.DateJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.date.SqlDateJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.date.SqlTimeJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.date.SqlTimestampJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.number.BigDecimalJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.number.BigIntegerJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.number.ByteJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.number.DoubleJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.number.FloatJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.number.IntegerJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.number.LongJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.number.ShortJsonMapperTest;
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
        suite.addTestSuite( UUIDJsonMapperTest.class );
        suite.addTestSuite( SimpleBeanJsonMapperTest.class );
        suite.addTestSuite( AllCollectionsJsonMapperTest.class );

        // Annotations test
        suite.addTestSuite( JsonAutoDetectGwtTest.class );
        suite.addTestSuite( JsonPropertyOrderGwtTest.class );
        suite.addTestSuite( JsonIgnoreGwtTest.class );
        suite.addTestSuite( JsonCreatorGwtTest.class );
        suite.addTestSuite( JsonIgnoreTypeGwtTest.class );
        suite.addTestSuite( JsonManagedAndBackReferenceGwtTest.class );

        // Advanced use cases
        suite.addTestSuite( PrivateAccessGwtTest.class );
        suite.addTestSuite( InheritanceGwtTest.class );
        // Polymorphism
        suite.addTestSuite( PolymorphismNoTypeInfoGwtTest.class );
        suite.addTestSuite( PolymorphismIdClassAsPropertyGwtTest.class );
        suite.addTestSuite( PolymorphismIdMinimalClassAsWrapperArrayGwtTest.class );
        suite.addTestSuite( PolymorphismIdNameAsWrapperObjectGwtTest.class );
        // Identity
        suite.addTestSuite( ObjectIdGwtTest.class );
        suite.addTestSuite( ObjectIdDeserializationGwtTest.class );
        suite.addTestSuite( ObjectIdSerializationGwtTest.class );
        suite.addTestSuite( ObjectIdWithPolymorphicGwtTest.class );

        return suite;
    }
}
