package com.github.nmorel.gwtjackson.client;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.client.ser.BooleanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.CharacterJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.DateJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.NumberJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.UUIDJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.ArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveBooleanArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveByteArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveCharacterArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveDoubleArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveFloatArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveIntegerArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveLongArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveShortArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.ObjectIdSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.logging.client.LogConfiguration;

/**
 * Context for the serialization process.
 *
 * @author Nicolas Morel
 */
public class JsonSerializationContext extends JsonMappingContext {

    public static class Builder {

        private boolean useEqualityForObjectId = false;

        private boolean serializeNulls = true;

        /**
         * Determines whether Object Identity is compared using
         * true JVM-level identity of Object (false); or, <code>equals()</code> method.
         * Latter is sometimes useful when dealing with Database-bound objects with
         * ORM libraries (like Hibernate).
         * <p/>
         * Feature is disabled by default; meaning that strict identity is used, not
         * <code>equals()</code>
         */
        public Builder useEqualityForObjectId( boolean useEqualityForObjectId ) {
            this.useEqualityForObjectId = useEqualityForObjectId;
            return this;
        }

        /**
         * Sets whether object members are serialized when their value is null.
         * This has no impact on array elements. The default is true.
         */
        public Builder serializeNulls( boolean serializeNulls ) {
            this.serializeNulls = serializeNulls;
            return this;
        }

        public JsonSerializationContext build() {
            return new JsonSerializationContext( useEqualityForObjectId, serializeNulls );
        }
    }

    private static final Logger logger = Logger.getLogger( "JsonSerialization" );

    private Map<Object, ObjectIdSerializer<?>> mapObjectId;

    private List<ObjectIdGenerator<?>> generators;

    /*
     * Serialization options
     */
    private final boolean useEqualityForObjectId;

    private final boolean serializeNulls;

    private JsonSerializationContext( boolean useEqualityForObjectId, boolean serializeNulls ) {
        this.useEqualityForObjectId = useEqualityForObjectId;
        this.serializeNulls = serializeNulls;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    public JsonWriter newJsonWriter() {
        JsonWriter writer = new JsonWriter( new StringBuilder() );
        writer.setSerializeNulls( serializeNulls );
        return writer;
    }

    public JsonSerializer<boolean[]> getPrimitiveBooleanArrayJsonSerializer() {
        return PrimitiveBooleanArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<byte[]> getPrimitiveByteArrayJsonSerializer() {
        return PrimitiveByteArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<char[]> getPrimitiveCharacterArrayJsonSerializer() {
        return PrimitiveCharacterArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<double[]> getPrimitiveDoubleArrayJsonSerializer() {
        return PrimitiveDoubleArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<float[]> getPrimitiveFloatArrayJsonSerializer() {
        return PrimitiveFloatArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<int[]> getPrimitiveIntegerArrayJsonSerializer() {
        return PrimitiveIntegerArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<long[]> getPrimitiveLongArrayJsonSerializer() {
        return PrimitiveLongArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<short[]> getPrimitiveShortArrayJsonSerializer() {
        return PrimitiveShortArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<BigDecimal> getBigDecimalJsonSerializer() {
        return NumberJsonSerializer.getBigDecimalInstance();
    }

    public JsonSerializer<BigInteger> getBigIntegerJsonSerializer() {
        return NumberJsonSerializer.getBigIntegerInstance();
    }

    public JsonSerializer<Boolean> getBooleanJsonSerializer() {
        return BooleanJsonSerializer.getInstance();
    }

    public JsonSerializer<Byte> getByteJsonSerializer() {
        return NumberJsonSerializer.getByteInstance();
    }

    public JsonSerializer<Character> getCharacterJsonSerializer() {
        return CharacterJsonSerializer.getInstance();
    }

    public JsonSerializer<Date> getDateJsonSerializer() {
        return DateJsonSerializer.getDateInstance();
    }

    public JsonSerializer<Double> getDoubleJsonSerializer() {
        return NumberJsonSerializer.getDoubleInstance();
    }

    public JsonSerializer<Float> getFloatJsonSerializer() {
        return NumberJsonSerializer.getFloatInstance();
    }

    public JsonSerializer<Integer> getIntegerJsonSerializer() {
        return NumberJsonSerializer.getIntegerInstance();
    }

    public JsonSerializer<Long> getLongJsonSerializer() {
        return NumberJsonSerializer.getLongInstance();
    }

    public JsonSerializer<Short> getShortJsonSerializer() {
        return NumberJsonSerializer.getShortInstance();
    }

    public JsonSerializer<java.sql.Date> getSqlDateJsonSerializer() {
        return DateJsonSerializer.getSqlDateInstance();
    }

    public JsonSerializer<Time> getSqlTimeJsonSerializer() {
        return DateJsonSerializer.getSqlTimeInstance();
    }

    public JsonSerializer<Timestamp> getSqlTimestampJsonSerializer() {
        return DateJsonSerializer.getSqlTimestampInstance();
    }

    public JsonSerializer<String> getStringJsonSerializer() {
        return StringJsonSerializer.getInstance();
    }

    public JsonSerializer<UUID> getUUIDJsonSerializer() {
        return UUIDJsonSerializer.getInstance();
    }

    public <E extends Enum<E>> JsonSerializer<E> getEnumJsonSerializer() {
        return EnumJsonSerializer.getInstance();
    }

    public <T> JsonSerializer<T[]> newArrayJsonSerializer( JsonSerializer<T> serializer ) {
        return ArrayJsonSerializer.newInstance( serializer );
    }

    public <I extends Iterable<T>, T> JsonSerializer<I> newIterableJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    /**
     * Trace an error and returns a corresponding exception.
     *
     * @param value current value
     * @param message error message
     *
     * @return a {@link JsonSerializationException} with the given message
     */
    public JsonSerializationException traceError( Object value, String message ) {
        getLogger().log( Level.SEVERE, message );
        return new JsonSerializationException( message );
    }

    /**
     * Trace an error with current writer state and returns a corresponding exception.
     *
     * @param value current value
     * @param message error message
     * @param writer current writer
     *
     * @return a {@link JsonSerializationException} with the given message
     */
    public JsonSerializationException traceError( Object value, String message, JsonWriter writer ) {
        JsonSerializationException exception = traceError( value, message );
        traceWriterInfo( value, writer );
        return exception;
    }

    /**
     * Trace an error and returns a corresponding exception.
     *
     * @param value current value
     * @param cause cause of the error
     *
     * @return a {@link JsonSerializationException} with the given cause
     */
    public JsonSerializationException traceError( Object value, Exception cause ) {
        getLogger().log( Level.SEVERE, "Error during serialization", cause );
        return new JsonSerializationException( cause );
    }

    /**
     * Trace an error with current writer state and returns a corresponding exception.
     *
     * @param value current value
     * @param cause cause of the error
     * @param writer current writer
     *
     * @return a {@link JsonSerializationException} with the given cause
     */
    public JsonSerializationException traceError( Object value, Exception cause, JsonWriter writer ) {
        JsonSerializationException exception = traceError( value, cause );
        traceWriterInfo( value, writer );
        return exception;
    }

    /**
     * Trace the current writer state
     *
     * @param value current value
     */
    private void traceWriterInfo( Object value, JsonWriter writer ) {
        if ( LogConfiguration.loggingIsEnabled( Level.INFO ) ) {
            getLogger().log( Level.INFO, "Error on value <" + value + ">. Current output : <" + writer.getOutput() + ">" );
        }
    }

    public void addObjectId( Object object, ObjectIdSerializer<?> id ) {
        if ( null == mapObjectId ) {
            if ( useEqualityForObjectId ) {
                mapObjectId = new HashMap<Object, ObjectIdSerializer<?>>();
            } else {
                mapObjectId = new IdentityHashMap<Object, ObjectIdSerializer<?>>();
            }
        }
        mapObjectId.put( object, id );
    }

    public ObjectIdSerializer<?> getObjectId( Object object ) {
        if ( null != mapObjectId ) {
            return mapObjectId.get( object );
        }
        return null;
    }

    public void addGenerator( ObjectIdGenerator<?> generator ) {
        if ( null == generators ) {
            generators = new ArrayList<ObjectIdGenerator<?>>();
        }
        generators.add( generator );
    }

    public <T> ObjectIdGenerator<T> findObjectIdGenerator( ObjectIdGenerator<T> gen ) {
        if ( null != generators ) {
            for ( ObjectIdGenerator<?> generator : generators ) {
                if ( generator.canUseFor( gen ) ) {
                    return (ObjectIdGenerator<T>) generator;
                }
            }
        }
        return null;
    }
}
