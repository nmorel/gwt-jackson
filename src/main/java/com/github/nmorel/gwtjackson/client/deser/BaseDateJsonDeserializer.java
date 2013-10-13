package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Base implementation of {@link JsonDeserializer} for dates.
 *
 * @author Nicolas Morel
 */
public abstract class BaseDateJsonDeserializer<D extends Date> extends JsonDeserializer<D> {

    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 );

    private static final DateTimeFormat SQL_DATE_FORMAT = DateTimeFormat.getFormat( "yyyy-MM-dd Z" );

    /**
     * Default implementation of {@link BaseDateJsonDeserializer} for {@link Date}
     */
    public static final class DateJsonDeserializer extends BaseDateJsonDeserializer<Date> {

        private static final DateJsonDeserializer INSTANCE = new DateJsonDeserializer();

        /**
         * @return an instance of {@link DateJsonDeserializer}
         */
        public static DateJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private DateJsonDeserializer() { }

        @Override
        protected Date deserializeNumber( long millis ) {
            return new Date( millis );
        }

        @Override
        protected Date deserializeString( String date ) {
            return DATE_FORMAT.parseStrict( date );
        }
    }

    /**
     * Default implementation of {@link BaseDateJsonDeserializer} for {@link java.sql.Date}
     */
    public static final class SqlDateJsonDeserializer extends BaseDateJsonDeserializer<java.sql.Date> {

        private static final SqlDateJsonDeserializer INSTANCE = new SqlDateJsonDeserializer();

        /**
         * @return an instance of {@link SqlDateJsonDeserializer}
         */
        public static SqlDateJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlDateJsonDeserializer() { }

        @Override
        protected java.sql.Date deserializeNumber( long millis ) {
            return new java.sql.Date( millis );
        }

        @Override
        protected java.sql.Date deserializeString( String date ) {
            Date d = SQL_DATE_FORMAT.parse( date + " +0000" );
            return new java.sql.Date( d.getTime() );
        }
    }

    /**
     * Default implementation of {@link BaseDateJsonDeserializer} for {@link Time}
     */
    public static final class SqlTimeJsonDeserializer extends BaseDateJsonDeserializer<Time> {

        private static final SqlTimeJsonDeserializer INSTANCE = new SqlTimeJsonDeserializer();

        /**
         * @return an instance of {@link SqlTimeJsonDeserializer}
         */
        public static SqlTimeJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlTimeJsonDeserializer() { }

        @Override
        protected Time deserializeNumber( long millis ) {
            return new Time( millis );
        }

        @Override
        protected Time deserializeString( String date ) {
            return Time.valueOf( date );
        }
    }

    /**
     * Default implementation of {@link BaseDateJsonDeserializer} for {@link Timestamp}
     */
    public static final class SqlTimestampJsonDeserializer extends BaseDateJsonDeserializer<Timestamp> {

        private static final SqlTimestampJsonDeserializer INSTANCE = new SqlTimestampJsonDeserializer();

        /**
         * @return an instance of {@link SqlTimestampJsonDeserializer}
         */
        public static SqlTimestampJsonDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlTimestampJsonDeserializer() { }

        @Override
        protected Timestamp deserializeNumber( long millis ) {
            return new Timestamp( millis );
        }

        @Override
        protected Timestamp deserializeString( String date ) {
            return Timestamp.valueOf( date );
        }
    }

    @Override
    public D doDeserialize( JsonReader reader, JsonDeserializationContext ctx ) throws IOException {
        if ( JsonToken.NUMBER.equals( reader.peek() ) ) {
            return deserializeNumber( reader.nextLong() );
        } else {
            return deserializeString( reader.nextString() );
        }
    }

    protected abstract D deserializeNumber( long millis );

    protected abstract D deserializeString( String date );
}
