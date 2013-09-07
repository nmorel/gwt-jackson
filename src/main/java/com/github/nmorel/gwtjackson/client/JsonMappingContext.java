package com.github.nmorel.gwtjackson.client;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.github.nmorel.gwtjackson.client.mapper.BooleanJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.CharacterJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.EnumJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.StringJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.ArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.PrimitiveBooleanArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.PrimitiveByteArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.PrimitiveCharacterArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.PrimitiveDoubleArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.PrimitiveFloatArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.PrimitiveIntegerArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.PrimitiveLongArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.array.PrimitiveShortArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.collection.CollectionJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.collection.IterableJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.collection.ListJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.collection.SetJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.date.DateJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.date.SqlDateJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.date.SqlTimeJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.date.SqlTimestampJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.number.BigDecimalJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.number.BigIntegerJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.number.ByteJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.number.DoubleJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.number.FloatJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.number.IntegerJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.number.LongJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.number.ShortJsonMapper;

/** @author Nicolas Morel */
public abstract class JsonMappingContext
{
    protected static final JsonMapper<BigDecimal> DEFAULT_BIGDECIMAL_MAPPER = new BigDecimalJsonMapper();
    protected static final JsonMapper<BigInteger> DEFAULT_BIGINTEGER_MAPPER = new BigIntegerJsonMapper();
    protected static final JsonMapper<Boolean> DEFAULT_BOOLEAN_MAPPER = new BooleanJsonMapper();
    protected static final JsonMapper<Byte> DEFAULT_BYTE_MAPPER = new ByteJsonMapper();
    protected static final JsonMapper<Character> DEFAULT_CHARACTER_MAPPER = new CharacterJsonMapper();
    protected static final JsonMapper<Date> DEFAULT_DATE_MAPPER = new DateJsonMapper();
    protected static final JsonMapper<Double> DEFAULT_DOUBLE_MAPPER = new DoubleJsonMapper();
    protected static final JsonMapper<Float> DEFAULT_FLOAT_MAPPER = new FloatJsonMapper();
    protected static final JsonMapper<Integer> DEFAULT_INTEGER_MAPPER = new IntegerJsonMapper();
    protected static final JsonMapper<Long> DEFAULT_LONG_MAPPER = new LongJsonMapper();
    protected static final JsonMapper<Short> DEFAULT_SHORT_MAPPER = new ShortJsonMapper();
    protected static final JsonMapper<java.sql.Date> DEFAULT_SQL_DATE_MAPPER = new SqlDateJsonMapper();
    protected static final JsonMapper<Time> DEFAULT_SQL_TIME_MAPPER = new SqlTimeJsonMapper();
    protected static final JsonMapper<Timestamp> DEFAULT_SQL_TIMESTAMP_MAPPER = new SqlTimestampJsonMapper();
    protected static final JsonMapper<String> DEFAULT_STRING_MAPPER = new StringJsonMapper();
    protected static final JsonMapper<boolean[]> DEFAULT_PRIMITIVE_BOOLEAN_ARRAY_MAPPER = new PrimitiveBooleanArrayJsonMapper(
        DEFAULT_BOOLEAN_MAPPER );
    protected static final JsonMapper<byte[]> DEFAULT_PRIMITIVE_BYTE_ARRAY_MAPPER = new PrimitiveByteArrayJsonMapper();
    protected static final JsonMapper<char[]> DEFAULT_PRIMITIVE_CHARACTER_ARRAY_MAPPER = new PrimitiveCharacterArrayJsonMapper();
    protected static final JsonMapper<double[]> DEFAULT_PRIMITIVE_DOUBLE_ARRAY_MAPPER = new PrimitiveDoubleArrayJsonMapper(
        DEFAULT_DOUBLE_MAPPER );
    protected static final JsonMapper<float[]> DEFAULT_PRIMITIVE_FLOAT_ARRAY_MAPPER = new PrimitiveFloatArrayJsonMapper(
        DEFAULT_FLOAT_MAPPER );
    protected static final JsonMapper<int[]> DEFAULT_PRIMITIVE_INTEGER_ARRAY_MAPPER = new PrimitiveIntegerArrayJsonMapper(
        DEFAULT_INTEGER_MAPPER );
    protected static final JsonMapper<long[]> DEFAULT_PRIMITIVE_LONG_ARRAY_MAPPER = new PrimitiveLongArrayJsonMapper( DEFAULT_LONG_MAPPER );
    protected static final JsonMapper<short[]> DEFAULT_PRIMITIVE_SHORT_ARRAY_MAPPER = new PrimitiveShortArrayJsonMapper(
        DEFAULT_SHORT_MAPPER );

    public static JsonMapper<boolean[]> getPrimitiveBooleanArrayJsonMapper()
    {
        return DEFAULT_PRIMITIVE_BOOLEAN_ARRAY_MAPPER;
    }

    public static JsonMapper<byte[]> getPrimitiveByteArrayJsonMapper()
    {
        return DEFAULT_PRIMITIVE_BYTE_ARRAY_MAPPER;
    }

    public static JsonMapper<char[]> getPrimitiveCharacterArrayJsonMapper()
    {
        return DEFAULT_PRIMITIVE_CHARACTER_ARRAY_MAPPER;
    }

    public static JsonMapper<double[]> getPrimitiveDoubleArrayJsonMapper()
    {
        return DEFAULT_PRIMITIVE_DOUBLE_ARRAY_MAPPER;
    }

    public static JsonMapper<float[]> getPrimitiveFloatArrayJsonMapper()
    {
        return DEFAULT_PRIMITIVE_FLOAT_ARRAY_MAPPER;
    }

    public static JsonMapper<int[]> getPrimitiveIntegerArrayJsonMapper()
    {
        return DEFAULT_PRIMITIVE_INTEGER_ARRAY_MAPPER;
    }

    public static JsonMapper<long[]> getPrimitiveLongArrayJsonMapper()
    {
        return DEFAULT_PRIMITIVE_LONG_ARRAY_MAPPER;
    }

    public static JsonMapper<short[]> getPrimitiveShortArrayJsonMapper()
    {
        return DEFAULT_PRIMITIVE_SHORT_ARRAY_MAPPER;
    }

    public abstract Logger getLogger();

    public JsonMapper<BigDecimal> getBigDecimalJsonMapper()
    {
        return DEFAULT_BIGDECIMAL_MAPPER;
    }

    public JsonMapper<BigInteger> getBigIntegerJsonMapper()
    {
        return DEFAULT_BIGINTEGER_MAPPER;
    }

    public JsonMapper<Boolean> getBooleanJsonMapper()
    {
        return DEFAULT_BOOLEAN_MAPPER;
    }

    public JsonMapper<Byte> getByteJsonMapper()
    {
        return DEFAULT_BYTE_MAPPER;
    }

    public JsonMapper<Character> getCharacterJsonMapper()
    {
        return DEFAULT_CHARACTER_MAPPER;
    }

    public JsonMapper<Date> getDateJsonMapper()
    {
        return DEFAULT_DATE_MAPPER;
    }

    public JsonMapper<Double> getDoubleJsonMapper()
    {
        return DEFAULT_DOUBLE_MAPPER;
    }

    public JsonMapper<Float> getFloatJsonMapper()
    {
        return DEFAULT_FLOAT_MAPPER;
    }

    public JsonMapper<Integer> getIntegerJsonMapper()
    {
        return DEFAULT_INTEGER_MAPPER;
    }

    public JsonMapper<Long> getLongJsonMapper()
    {
        return DEFAULT_LONG_MAPPER;
    }

    public JsonMapper<Short> getShortJsonMapper()
    {
        return DEFAULT_SHORT_MAPPER;
    }

    public JsonMapper<java.sql.Date> getSqlDateJsonMapper()
    {
        return DEFAULT_SQL_DATE_MAPPER;
    }

    public JsonMapper<Time> getSqlTimeJsonMapper()
    {
        return DEFAULT_SQL_TIME_MAPPER;
    }

    public JsonMapper<Timestamp> getSqlTimestampJsonMapper()
    {
        return DEFAULT_SQL_TIMESTAMP_MAPPER;
    }

    public JsonMapper<String> getStringJsonMapper()
    {
        return DEFAULT_STRING_MAPPER;
    }

    public <T> JsonMapper<T[]> createArrayJsonMapper( JsonMapper<T> mapper, ArrayJsonMapper.ArrayCreator<T> arrayCreator )
    {
        return new ArrayJsonMapper<T>( mapper, arrayCreator );
    }

    public <T> JsonMapper<Iterable<T>> createIterableJsonMapper( JsonMapper<T> mapper )
    {
        return new IterableJsonMapper<T>( mapper );
    }

    public <T> JsonMapper<Collection<T>> createCollectionJsonMapper( JsonMapper<T> mapper )
    {
        return new CollectionJsonMapper<T>( mapper );
    }

    public <T> JsonMapper<List<T>> createListJsonMapper( JsonMapper<T> mapper )
    {
        return new ListJsonMapper<T>( mapper );
    }

    public <T> JsonMapper<Set<T>> createSetJsonMapper( JsonMapper<T> mapper )
    {
        return new SetJsonMapper<T>( mapper );
    }

    public <T extends Enum<T>> JsonMapper<T> createEnumJsonMapper( Class<T> enumClass )
    {
        return new EnumJsonMapper<T>( enumClass );
    }
}
