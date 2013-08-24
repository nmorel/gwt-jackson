package com.github.nmorel.gwtjackson.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.github.nmorel.gwtjackson.client.mapper.ArrayJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.BigDecimalJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.BigIntegerJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.BooleanJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.ByteJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.CharacterJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.CollectionJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.DateJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.DoubleJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.EnumJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.FloatJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.IntegerJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.IterableJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.ListJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.LongJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.SetJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.ShortJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.SqlDateJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.SqlTimeJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.SqlTimestampJsonMapper;
import com.github.nmorel.gwtjackson.client.mapper.StringJsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/**
 * Gives default method implementation for {@link JsonMapper} and utility methods for children.
 *
 * @author Nicolas Morel
 */
public abstract class AbstractJsonMapper<T> implements JsonMapper<T>
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

    protected <T> JsonMapper<T[]> createArrayJsonMapper( JsonMapper<T> mapper, ArrayJsonMapper.ArrayCreator<T> arrayCreator )
    {
        return new ArrayJsonMapper<T>( mapper, arrayCreator );
    }

    protected <T> JsonMapper<Iterable<T>> createIterableJsonMapper( JsonMapper<T> mapper )
    {
        return new IterableJsonMapper<Iterable<T>, T>( mapper );
    }

    protected <T> JsonMapper<Collection<T>> createCollectionJsonMapper( JsonMapper<T> mapper )
    {
        return new CollectionJsonMapper<Collection<T>, T>( mapper );
    }

    protected <T> JsonMapper<List<T>> createListJsonMapper( JsonMapper<T> mapper )
    {
        return new ListJsonMapper<T>( mapper );
    }

    protected <T> JsonMapper<Set<T>> createSetJsonMapper( JsonMapper<T> mapper )
    {
        return new SetJsonMapper<T>( mapper );
    }

    protected <T extends Enum<T>> JsonMapper<T> createEnumJsonMapper( Class<T> enumClass )
    {
        return new EnumJsonMapper<T>( enumClass );
    }

    @Override
    public T decode( String in ) throws IOException
    {
        JsonReader reader = new JsonReader( in );
        reader.setLenient( true );
        return decode( reader, new JsonDecodingContext() );
    }

    @Override
    public String encode( T value ) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        encode( new JsonWriter( builder ), value, new JsonEncodingContext() );
        return builder.toString();
    }
}
