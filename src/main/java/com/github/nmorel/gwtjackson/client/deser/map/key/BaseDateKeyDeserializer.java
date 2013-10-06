package com.github.nmorel.gwtjackson.client.deser.map.key;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

/**
 * Base implementation of {@link KeyDeserializer} for dates. It uses both ISO-8601 and RFC-2822 for string-based key and milliseconds
 * for number-based key.
 *
 * @author Nicolas Morel
 */
public abstract class BaseDateKeyDeserializer<D extends Date> extends KeyDeserializer<D> {

    /**
     * Default implementation of {@link BaseDateKeyDeserializer} for {@link Date}
     */
    public static final class DateKeyDeserializer extends BaseDateKeyDeserializer<Date> {

        private static final DateKeyDeserializer INSTANCE = new DateKeyDeserializer();

        /**
         * @return an instance of {@link DateKeyDeserializer}
         */
        public static DateKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private DateKeyDeserializer() { }

        @Override
        protected Date deserializeMillis( long millis ) {
            return new Date( millis );
        }

        @Override
        protected Date deserializeDate( Date date ) {
            return date;
        }
    }

    /**
     * Default implementation of {@link BaseDateKeyDeserializer} for {@link java.sql.Date}
     */
    public static final class SqlDateKeyDeserializer extends BaseDateKeyDeserializer<java.sql.Date> {

        private static final SqlDateKeyDeserializer INSTANCE = new SqlDateKeyDeserializer();

        /**
         * @return an instance of {@link SqlDateKeyDeserializer}
         */
        public static SqlDateKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlDateKeyDeserializer() { }

        @Override
        protected java.sql.Date deserializeMillis( long millis ) {
            return new java.sql.Date( millis );
        }

        @Override
        protected java.sql.Date deserializeDate( Date date ) {
            return deserializeMillis( date.getTime() );
        }
    }

    /**
     * Default implementation of {@link BaseDateKeyDeserializer} for {@link Time}
     */
    public static final class SqlTimeKeyDeserializer extends BaseDateKeyDeserializer<Time> {

        private static final SqlTimeKeyDeserializer INSTANCE = new SqlTimeKeyDeserializer();

        /**
         * @return an instance of {@link SqlTimeKeyDeserializer}
         */
        public static SqlTimeKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlTimeKeyDeserializer() { }

        @Override
        protected Time deserializeMillis( long millis ) {
            return new Time( millis );
        }

        @Override
        protected Time deserializeDate( Date date ) {
            return deserializeMillis( date.getTime() );
        }
    }

    /**
     * Default implementation of {@link BaseDateKeyDeserializer} for {@link Timestamp}
     */
    public static final class SqlTimestampKeyDeserializer extends BaseDateKeyDeserializer<Timestamp> {

        private static final SqlTimestampKeyDeserializer INSTANCE = new SqlTimestampKeyDeserializer();

        /**
         * @return an instance of {@link SqlTimestampKeyDeserializer}
         */
        public static SqlTimestampKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlTimestampKeyDeserializer() { }

        @Override
        protected Timestamp deserializeMillis( long millis ) {
            return new Timestamp( millis );
        }

        @Override
        protected Timestamp deserializeDate( Date date ) {
            return deserializeMillis( date.getTime() );
        }
    }

    private static final DateTimeFormat ISO_8601_FORMAT = DateTimeFormat.getFormat( PredefinedFormat.ISO_8601 );

    private static final DateTimeFormat RFC_2822_FORMAT = DateTimeFormat.getFormat( PredefinedFormat.RFC_2822 );

    @Override
    protected D doDeserialize( String key, JsonDeserializationContext ctx ) {
        // TODO could probably find a better way to handle the parsing without try/catch

        // Default configuration for serializing keys is using ISO-8601, we try that one first

        // in ISO-8601
        try {
            return deserializeDate( ISO_8601_FORMAT.parse( key ) );
        } catch ( IllegalArgumentException e ) {
            // can happen if it's not the correct format
        }

        // maybe it's in milliseconds
        try {
            return deserializeMillis( Long.parseLong( key ) );
        } catch ( NumberFormatException e ) {
            // can happen if the key is string-based like an ISO-8601 format
        }

        // or in RFC-2822
        try {
            return deserializeDate( RFC_2822_FORMAT.parse( key ) );
        } catch ( IllegalArgumentException e ) {
            // can happen if it's not the correct format
        }

        throw new JsonDeserializationException( "Cannot parse the key '" + key + "' as a date" );
    }

    protected abstract D deserializeMillis( long millis );

    protected abstract D deserializeDate( Date date );
}
