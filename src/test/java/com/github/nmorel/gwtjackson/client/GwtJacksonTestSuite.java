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
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdWithEqualsGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdWithPolymorphicGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonAutoDetectGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonCreatorGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonIgnoreGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonIgnoreTypeGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonManagedAndBackReferenceGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonPropertyOrderGwtTest;
import com.github.nmorel.gwtjackson.client.deser.BooleanJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.CharacterJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.UUIDJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.collection.CollectionJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.collection.IterableJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.collection.ListJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.collection.SetJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.date.DateJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.date.SqlDateJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.date.SqlTimeJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.date.SqlTimestampJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.BigDecimalJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.BigIntegerJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.ByteJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.DoubleJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.FloatJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.IntegerJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.LongJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.ShortJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.mapper.AllCollectionsObjectMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.CommonJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.SimpleBeanObjectReaderTest;
import com.github.nmorel.gwtjackson.client.mapper.SimpleBeanObjectWriterTest;
import com.github.nmorel.gwtjackson.client.ser.BooleanJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.CharacterJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.UUIDJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.array.ArrayJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.collection.CollectionJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.collection.IterableJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.collection.ListJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.collection.SetJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.date.DateJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.date.SqlDateJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.date.SqlTimeJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.date.SqlTimestampJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.number.BigDecimalJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.number.BigIntegerJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.number.ByteJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.number.DoubleJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.number.FloatJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.number.IntegerJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.number.LongJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.ser.number.ShortJsonSerializerTest;
import com.github.nmorel.gwtjackson.client.stream.JsonReaderTest;
import com.github.nmorel.gwtjackson.client.stream.JsonWriterTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * @author Nicolas Morel
 */
public class GwtJacksonTestSuite extends TestCase {

    public static Test suite() {
        GWTTestSuite suite = new GWTTestSuite();

        // Stream - tests from gson
        suite.addTestSuite( JsonReaderTest.class );
        suite.addTestSuite( JsonWriterTest.class );

        // Default serializers
        suite.addTestSuite( ArrayJsonSerializerTest.class );
        suite.addTestSuite( BigDecimalJsonSerializerTest.class );
        suite.addTestSuite( BigIntegerJsonSerializerTest.class );
        suite.addTestSuite( BooleanJsonSerializerTest.class );
        suite.addTestSuite( ByteJsonSerializerTest.class );
        suite.addTestSuite( CharacterJsonSerializerTest.class );
        suite.addTestSuite( CollectionJsonSerializerTest.class );
        suite.addTestSuite( DateJsonSerializerTest.class );
        suite.addTestSuite( DoubleJsonSerializerTest.class );
        suite.addTestSuite( EnumJsonSerializerTest.class );
        suite.addTestSuite( FloatJsonSerializerTest.class );
        suite.addTestSuite( IntegerJsonSerializerTest.class );
        suite.addTestSuite( IterableJsonSerializerTest.class );
        suite.addTestSuite( ListJsonSerializerTest.class );
        suite.addTestSuite( LongJsonSerializerTest.class );
        suite.addTestSuite( SetJsonSerializerTest.class );
        suite.addTestSuite( ShortJsonSerializerTest.class );
        suite.addTestSuite( SqlDateJsonSerializerTest.class );
        suite.addTestSuite( SqlTimeJsonSerializerTest.class );
        suite.addTestSuite( SqlTimestampJsonSerializerTest.class );
        suite.addTestSuite( StringJsonSerializerTest.class );
        suite.addTestSuite( UUIDJsonSerializerTest.class );

        // Default deserializers
        suite.addTestSuite( ArrayJsonDeserializerTest.class );
        suite.addTestSuite( BigDecimalJsonDeserializerTest.class );
        suite.addTestSuite( BigIntegerJsonDeserializerTest.class );
        suite.addTestSuite( BooleanJsonDeserializerTest.class );
        suite.addTestSuite( ByteJsonDeserializerTest.class );
        suite.addTestSuite( CharacterJsonDeserializerTest.class );
        suite.addTestSuite( CollectionJsonDeserializerTest.class );
        suite.addTestSuite( DateJsonDeserializerTest.class );
        suite.addTestSuite( DoubleJsonDeserializerTest.class );
        suite.addTestSuite( EnumJsonDeserializerTest.class );
        suite.addTestSuite( FloatJsonDeserializerTest.class );
        suite.addTestSuite( IntegerJsonDeserializerTest.class );
        suite.addTestSuite( IterableJsonDeserializerTest.class );
        suite.addTestSuite( ListJsonDeserializerTest.class );
        suite.addTestSuite( LongJsonDeserializerTest.class );
        suite.addTestSuite( SetJsonDeserializerTest.class );
        suite.addTestSuite( ShortJsonDeserializerTest.class );
        suite.addTestSuite( SqlDateJsonDeserializerTest.class );
        suite.addTestSuite( SqlTimeJsonDeserializerTest.class );
        suite.addTestSuite( SqlTimestampJsonDeserializerTest.class );
        suite.addTestSuite( StringJsonDeserializerTest.class );
        suite.addTestSuite( UUIDJsonDeserializerTest.class );

        // Mappers
        suite.addTestSuite( SimpleBeanObjectReaderTest.class );
        suite.addTestSuite( SimpleBeanObjectWriterTest.class );
        suite.addTestSuite( AllCollectionsObjectMapperTest.class );
        suite.addTestSuite( CommonJsonMapperTest.class );

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
        suite.addTestSuite( ObjectIdWithEqualsGwtTest.class );

        return suite;
    }
}
