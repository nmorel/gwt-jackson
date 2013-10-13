package com.github.nmorel.gwtjackson.client.ser;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Base implementation of {@link JsonSerializer} for dates.
 *
 * @author Nicolas Morel
 */
public abstract class BaseDateJsonSerializer<D extends Date> extends JsonSerializer<D> {

    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 );

    /**
     * Default implementation of {@link BaseDateJsonSerializer} for {@link Date}
     */
    public static final class DateJsonSerializer extends BaseDateJsonSerializer<Date> {

        private static final DateJsonSerializer INSTANCE = new DateJsonSerializer();

        /**
         * @return an instance of {@link DateJsonSerializer}
         */
        public static DateJsonSerializer getInstance() {
            return INSTANCE;
        }

        private DateJsonSerializer() { }

        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull Date value, JsonSerializationContext ctx ) throws IOException {
            if ( ctx.isWriteDatesAsTimestamps() ) {
                writer.value( value.getTime() );
            } else {
                writer.value( DATE_FORMAT.format( value ) );
            }
        }
    }

    /**
     * Default implementation of {@link BaseDateJsonSerializer} for {@link java.sql.Date}
     */
    public static final class SqlDateJsonSerializer extends BaseDateJsonSerializer<java.sql.Date> {

        private static final SqlDateJsonSerializer INSTANCE = new SqlDateJsonSerializer();

        /**
         * @return an instance of {@link SqlDateJsonSerializer}
         */
        public static SqlDateJsonSerializer getInstance() {
            return INSTANCE;
        }

        private SqlDateJsonSerializer() { }

        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull java.sql.Date value, JsonSerializationContext ctx ) throws IOException {
            writer.value( value.toString() );
        }
    }

    private static final BaseDateJsonSerializer<Time> SQL_TIME_INSTANCE = new BaseDateJsonSerializer<Time>() {
        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull Time value, JsonSerializationContext ctx ) throws IOException {
            writer.value( value.toString() );
        }
    };

    /**
     * Default implementation of {@link BaseDateJsonSerializer} for {@link Date}
     */
    public static final class SqlTimeJsonSerializer extends BaseDateJsonSerializer<Time> {

        private static final SqlTimeJsonSerializer INSTANCE = new SqlTimeJsonSerializer();

        /**
         * @return an instance of {@link SqlTimeJsonSerializer}
         */
        public static SqlTimeJsonSerializer getInstance() {
            return INSTANCE;
        }

        private SqlTimeJsonSerializer() { }

        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull Time value, JsonSerializationContext ctx ) throws IOException {
            writer.value( value.toString() );
        }
    }

    /**
     * Default implementation of {@link BaseDateJsonSerializer} for {@link Timestamp}
     */
    public static final class SqlTimestampJsonSerializer extends BaseDateJsonSerializer<Timestamp> {

        private static final SqlTimestampJsonSerializer INSTANCE = new SqlTimestampJsonSerializer();

        /**
         * @return an instance of {@link SqlTimestampJsonSerializer}
         */
        public static SqlTimestampJsonSerializer getInstance() {
            return INSTANCE;
        }

        private SqlTimestampJsonSerializer() { }

        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull Timestamp value, JsonSerializationContext ctx ) throws IOException {
            if ( ctx.isWriteDatesAsTimestamps() ) {
                writer.value( value.getTime() );
            } else {
                writer.value( DATE_FORMAT.format( value ) );
            }
        }
    }
}
