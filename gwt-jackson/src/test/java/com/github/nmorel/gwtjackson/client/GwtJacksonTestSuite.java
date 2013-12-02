/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client;

import com.github.nmorel.gwtjackson.client.advanced.GenericsGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.InheritanceGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.PrivateAccessGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdDeserializationGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdSerializationGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdWithEqualsGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.identity.ObjectIdWithPolymorphicGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.jsontype.JsonTypeOnPropertiesGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.jsontype.JsonTypeWithGenericsGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.jsontype.PolymorphismIdClassAsPropertyGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.jsontype.PolymorphismIdMinimalClassAsWrapperArrayGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.jsontype.PolymorphismIdNameAsWrapperObjectGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.jsontype.PolymorphismNoTypeInfoGwtTest;
import com.github.nmorel.gwtjackson.client.advanced.jsontype.TypeNamesGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonAutoDetectGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonCreatorGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonIgnoreGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonIgnoreTypeGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonManagedAndBackReferenceGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonPropertyOrderGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonRawValueGwtTest;
import com.github.nmorel.gwtjackson.client.annotations.JsonRootNameGwtTest;
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
import com.github.nmorel.gwtjackson.client.deser.map.key.BigDecimalKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.BigIntegerKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.BooleanKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.ByteKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.CharacterKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.DateKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.DoubleKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.EnumKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.FloatKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.IntegerKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.LongKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.ShortKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.SqlDateKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.SqlTimeKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.SqlTimestampKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.StringKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.map.key.UUIDKeyDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.BigDecimalJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.BigIntegerJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.ByteJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.DoubleJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.FloatJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.IntegerJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.LongJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.deser.number.ShortJsonDeserializerTest;
import com.github.nmorel.gwtjackson.client.mapper.AllCollectionsObjectMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.AllMapsObjectMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.CommonJsonMapperTest;
import com.github.nmorel.gwtjackson.client.mapper.IgnoreStaticGwtTest;
import com.github.nmorel.gwtjackson.client.mapper.SimpleBeanObjectReaderTest;
import com.github.nmorel.gwtjackson.client.mapper.SimpleBeanObjectWriterTest;
import com.github.nmorel.gwtjackson.client.options.CharArrayOptionGwtTest;
import com.github.nmorel.gwtjackson.client.options.DateOptionsGwtTest;
import com.github.nmorel.gwtjackson.client.options.IndentGwtTest;
import com.github.nmorel.gwtjackson.client.options.SingleArrayOptionGwtTest;
import com.github.nmorel.gwtjackson.client.options.WriteEmptyJsonArraysOptionGwtTest;
import com.github.nmorel.gwtjackson.client.options.WriteNullMapValuesOptionGwtTest;
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
import com.github.nmorel.gwtjackson.client.ser.map.key.BigDecimalKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.BigIntegerKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.BooleanKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.ByteKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.CharacterKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.DateKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.DoubleKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.EnumKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.FloatKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.IntegerKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.LongKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.ShortKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.SqlDateKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.SqlTimeKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.SqlTimestampKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.StringKeySerializerTest;
import com.github.nmorel.gwtjackson.client.ser.map.key.UUIDKeySerializerTest;
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

        // Default json serializers
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

        // Default key serializers
        suite.addTestSuite( BooleanKeySerializerTest.class );
        suite.addTestSuite( CharacterKeySerializerTest.class );
        suite.addTestSuite( DateKeySerializerTest.class );
        suite.addTestSuite( EnumKeySerializerTest.class );
        suite.addTestSuite( SqlDateKeySerializerTest.class );
        suite.addTestSuite( SqlTimeKeySerializerTest.class );
        suite.addTestSuite( SqlTimestampKeySerializerTest.class );
        suite.addTestSuite( StringKeySerializerTest.class );
        suite.addTestSuite( UUIDKeySerializerTest.class );
        suite.addTestSuite( BigDecimalKeySerializerTest.class );
        suite.addTestSuite( BigIntegerKeySerializerTest.class );
        suite.addTestSuite( ByteKeySerializerTest.class );
        suite.addTestSuite( DoubleKeySerializerTest.class );
        suite.addTestSuite( FloatKeySerializerTest.class );
        suite.addTestSuite( IntegerKeySerializerTest.class );
        suite.addTestSuite( LongKeySerializerTest.class );
        suite.addTestSuite( ShortKeySerializerTest.class );

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

        // Default key deserializers
        suite.addTestSuite( BooleanKeyDeserializerTest.class );
        suite.addTestSuite( CharacterKeyDeserializerTest.class );
        suite.addTestSuite( DateKeyDeserializerTest.class );
        suite.addTestSuite( EnumKeyDeserializerTest.class );
        suite.addTestSuite( SqlDateKeyDeserializerTest.class );
        suite.addTestSuite( SqlTimeKeyDeserializerTest.class );
        suite.addTestSuite( SqlTimestampKeyDeserializerTest.class );
        suite.addTestSuite( StringKeyDeserializerTest.class );
        suite.addTestSuite( UUIDKeyDeserializerTest.class );
        suite.addTestSuite( BigDecimalKeyDeserializerTest.class );
        suite.addTestSuite( BigIntegerKeyDeserializerTest.class );
        suite.addTestSuite( ByteKeyDeserializerTest.class );
        suite.addTestSuite( DoubleKeyDeserializerTest.class );
        suite.addTestSuite( FloatKeyDeserializerTest.class );
        suite.addTestSuite( IntegerKeyDeserializerTest.class );
        suite.addTestSuite( LongKeyDeserializerTest.class );
        suite.addTestSuite( ShortKeyDeserializerTest.class );

        // Mappers
        suite.addTestSuite( SimpleBeanObjectReaderTest.class );
        suite.addTestSuite( SimpleBeanObjectWriterTest.class );
        suite.addTestSuite( AllCollectionsObjectMapperTest.class );
        suite.addTestSuite( AllMapsObjectMapperTest.class );
        suite.addTestSuite( CommonJsonMapperTest.class );
        suite.addTestSuite( IgnoreStaticGwtTest.class );

        // Annotations test
        suite.addTestSuite( JsonAutoDetectGwtTest.class );
        suite.addTestSuite( JsonPropertyOrderGwtTest.class );
        suite.addTestSuite( JsonIgnoreGwtTest.class );
        suite.addTestSuite( JsonCreatorGwtTest.class );
        suite.addTestSuite( JsonIgnoreTypeGwtTest.class );
        suite.addTestSuite( JsonManagedAndBackReferenceGwtTest.class );
        suite.addTestSuite( JsonRootNameGwtTest.class );
        suite.addTestSuite( JsonRawValueGwtTest.class );

        // Advanced use cases
        suite.addTestSuite( PrivateAccessGwtTest.class );
        suite.addTestSuite( InheritanceGwtTest.class );
        suite.addTestSuite( GenericsGwtTest.class );

        // Polymorphism
        suite.addTestSuite( PolymorphismNoTypeInfoGwtTest.class );
        suite.addTestSuite( PolymorphismIdClassAsPropertyGwtTest.class );
        suite.addTestSuite( PolymorphismIdMinimalClassAsWrapperArrayGwtTest.class );
        suite.addTestSuite( PolymorphismIdNameAsWrapperObjectGwtTest.class );
        suite.addTestSuite( JsonTypeWithGenericsGwtTest.class );
        suite.addTestSuite( JsonTypeOnPropertiesGwtTest.class );
        suite.addTestSuite( TypeNamesGwtTest.class );
        // TODO this class isn't working at all, the current implementation don't support JsonTypeInfo on a single type without any subtype
        // suite.addTestSuite( VisibleTypeIdGwtTest.class );

        // Identity
        suite.addTestSuite( ObjectIdGwtTest.class );
        suite.addTestSuite( ObjectIdDeserializationGwtTest.class );
        suite.addTestSuite( ObjectIdSerializationGwtTest.class );
        suite.addTestSuite( ObjectIdWithPolymorphicGwtTest.class );
        suite.addTestSuite( ObjectIdWithEqualsGwtTest.class );

        // Options
        suite.addTestSuite( IndentGwtTest.class );
        suite.addTestSuite( DateOptionsGwtTest.class );
        suite.addTestSuite( CharArrayOptionGwtTest.class );
        suite.addTestSuite( WriteNullMapValuesOptionGwtTest.class );
        suite.addTestSuite( WriteEmptyJsonArraysOptionGwtTest.class );
        suite.addTestSuite( SingleArrayOptionGwtTest.class );

        return suite;
    }
}
