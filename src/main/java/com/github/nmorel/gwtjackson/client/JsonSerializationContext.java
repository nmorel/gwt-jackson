package com.github.nmorel.gwtjackson.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.github.nmorel.gwtjackson.client.ser.bean.AbstractBeanJsonSerializer;
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

        private boolean writeDatesAsTimestamps = true;

        private boolean writeDateKeysAsTimestamps = false;

        private boolean indent = false;

        private boolean wrapRootValue = false;

        private boolean writeCharArraysAsJsonArrays = false;

        private boolean writeNullMapValues = true;

        private boolean writeEmptyJsonArrays = true;

        private boolean orderMapEntriesByKeys = false;

        /**
         * Determines whether Object Identity is compared using
         * true JVM-level identity of Object (false); or, <code>equals()</code> method.
         * Latter is sometimes useful when dealing with Database-bound objects with
         * ORM libraries (like Hibernate).
         * <p/>
         * Option is disabled by default; meaning that strict identity is used, not
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

        /**
         * Determines whether {@link java.util.Date} and {@link java.sql.Timestamp} values are to be serialized as numeric timestamps
         * (true; the default), or as textual representation.
         * <p>If textual representation is used, the actual format is
         * {@link com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat#ISO_8601}</p>
         * Option is enabled by default.
         */
        public Builder writeDatesAsTimestamps( boolean writeDatesAsTimestamps ) {
            this.writeDatesAsTimestamps = writeDatesAsTimestamps;
            return this;
        }

        /**
         * Feature that determines whether {@link java.util.Date}s and {@link java.sql.Timestamp}s used as {@link java.util.Map} keys are
         * serialized as timestamps or as textual values.
         * <p>If textual representation is used, the actual format is
         * {@link com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat#ISO_8601}</p>
         * Option is disabled by default.
         */
        public Builder writeDateKeysAsTimestamps( boolean writeDateKeysAsTimestamps ) {
            this.writeDateKeysAsTimestamps = writeDateKeysAsTimestamps;
            return this;
        }

        /**
         * Feature that allows enabling (or disabling) indentation
         * for the underlying writer.
         * <p>Feature is disabled by default.</p>
         */
        public Builder indent( boolean indent ) {
            this.indent = indent;
            return this;
        }

        /**
         * Feature that can be enabled to make root value (usually JSON
         * Object but can be any type) wrapped within a single property
         * JSON object, where key as the "root name", as determined by
         * annotation introspector or fallback (non-qualified
         * class name).
         * <p>Feature is disabled by default.</p>
         */
        public Builder wrapRootValue( boolean wrapRootValue ) {
            this.wrapRootValue = wrapRootValue;
            return this;
        }

        /**
         * Feature that determines how type <code>char[]</code> is serialized:
         * when enabled, will be serialized as an explict JSON array (with
         * single-character Strings as values); when disabled, defaults to
         * serializing them as Strings (which is more compact).
         * <p/>
         * Feature is disabled by default.
         */
        public Builder writeCharArraysAsJsonArrays( boolean writeCharArraysAsJsonArrays ) {
            this.writeCharArraysAsJsonArrays = writeCharArraysAsJsonArrays;
            return this;
        }

        /**
         * Feature that determines whether Map entries with null values are
         * to be serialized (true) or not (false).
         * <p/>
         * Feature is enabled by default.
         */
        public Builder writeNullMapValues( boolean writeNullMapValues ) {
            this.writeNullMapValues = writeNullMapValues;
            return this;
        }

        /**
         * Feature that determines whether Container properties (POJO properties
         * with declared value of Collection or array; i.e. things that produce JSON
         * arrays) that are empty (have no elements)
         * will be serialized as empty JSON arrays (true), or suppressed from output (false).
         * <p/>
         * Note that this does not change behavior of {@link java.util.Map}s, or
         * "Collection-like" types.
         * <p/>
         * Feature is enabled by default.
         */
        public Builder writeEmptyJsonArrays( boolean writeEmptyJsonArrays ) {
            this.writeEmptyJsonArrays = writeEmptyJsonArrays;
            return this;
        }

        /**
         * Feature that determines whether {@link java.util.Map} entries are first
         * sorted by key before serialization or not: if enabled, additional sorting
         * step is performed if necessary (not necessary for {@link java.util.SortedMap}s),
         * if disabled, no additional sorting is needed.
         * <p/>
         * Feature is disabled by default.
         */
        public Builder orderMapEntriesByKeys( boolean orderMapEntriesByKeys ) {
            this.orderMapEntriesByKeys = orderMapEntriesByKeys;
            return this;
        }

        public JsonSerializationContext build() {
            return new JsonSerializationContext( useEqualityForObjectId, serializeNulls, writeDatesAsTimestamps,
                writeDateKeysAsTimestamps, indent, wrapRootValue, writeCharArraysAsJsonArrays, writeNullMapValues, writeEmptyJsonArrays,
                orderMapEntriesByKeys );
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

    private final boolean writeDatesAsTimestamps;

    private final boolean writeDateKeysAsTimestamps;

    private final boolean indent;

    private final boolean wrapRootValue;

    private final boolean writeCharArraysAsJsonArrays;

    private final boolean writeNullMapValues;

    private final boolean writeEmptyJsonArrays;

    private final boolean orderMapEntriesByKeys;

    private JsonSerializationContext( boolean useEqualityForObjectId, boolean serializeNulls, boolean writeDatesAsTimestamps,
                                      boolean writeDateKeysAsTimestamps, boolean indent, boolean wrapRootValue,
                                      boolean writeCharArraysAsJsonArrays, boolean writeNullMapValues, boolean writeEmptyJsonArrays,
                                      boolean orderMapEntriesByKeys ) {
        this.useEqualityForObjectId = useEqualityForObjectId;
        this.serializeNulls = serializeNulls;
        this.writeDatesAsTimestamps = writeDatesAsTimestamps;
        this.writeDateKeysAsTimestamps = writeDateKeysAsTimestamps;
        this.indent = indent;
        this.wrapRootValue = wrapRootValue;
        this.writeCharArraysAsJsonArrays = writeCharArraysAsJsonArrays;
        this.writeNullMapValues = writeNullMapValues;
        this.writeEmptyJsonArrays = writeEmptyJsonArrays;
        this.orderMapEntriesByKeys = orderMapEntriesByKeys;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * @see Builder#writeDatesAsTimestamps(boolean)
     */
    public boolean isWriteDatesAsTimestamps() {
        return writeDatesAsTimestamps;
    }

    /**
     * @see Builder#writeDateKeysAsTimestamps(boolean)
     */
    public boolean isWriteDateKeysAsTimestamps() {
        return writeDateKeysAsTimestamps;
    }

    /**
     * @see Builder#wrapRootValue(boolean)
     */
    public boolean isWrapRootValue() {
        return wrapRootValue;
    }

    /**
     * @see Builder#writeCharArraysAsJsonArrays(boolean)
     */
    public boolean isWriteCharArraysAsJsonArrays() {
        return writeCharArraysAsJsonArrays;
    }

    /**
     * @see Builder#writeNullMapValues(boolean)
     */
    public boolean isWriteNullMapValues() {
        return writeNullMapValues;
    }

    /**
     * @see Builder#writeEmptyJsonArrays(boolean)
     */
    public boolean isWriteEmptyJsonArrays() {
        return writeEmptyJsonArrays;
    }

    /**
     * @see Builder#orderMapEntriesByKeys(boolean)
     */
    public boolean isOrderMapEntriesByKeys() {
        return orderMapEntriesByKeys;
    }

    public JsonWriter newJsonWriter() {
        JsonWriter writer = new JsonWriter( new StringBuilder() );
        writer.setLenient( true );
        writer.setSerializeNulls( serializeNulls );
        if ( indent ) {
            writer.setIndent( "  " );
        }
        return writer;
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

    /**
     * Used by generated {@link AbstractBeanJsonSerializer}
     *
     * @param generator instance of generator to add
     */
    @SuppressWarnings("UnusedDeclaration")
    public void addGenerator( ObjectIdGenerator<?> generator ) {
        if ( null == generators ) {
            generators = new ArrayList<ObjectIdGenerator<?>>();
        }
        generators.add( generator );
    }

    /**
     * Used by generated {@link AbstractBeanJsonSerializer}
     *
     * @param gen generator used to find equivalent generator
     */
    @SuppressWarnings({"UnusedDeclaration", "unchecked"})
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
