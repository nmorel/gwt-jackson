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
public abstract class DateJsonSerializer<D extends Date> extends JsonSerializer<D> {

    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 );

    private static final DateJsonSerializer<Date> DATE_INSTANCE = new DateJsonSerializer<Date>() {
        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull Date value, JsonSerializationContext ctx ) throws IOException {
            if ( ctx.isWriteDatesAsTimestamps() ) {
                writer.value( value.getTime() );
            } else {
                writer.value( DATE_FORMAT.format( value ) );
            }
        }
    };

    /**
     * @return an instance of {@link DateJsonSerializer} that serialize {@link Date}
     */
    public static DateJsonSerializer<Date> getDateInstance() {
        return DATE_INSTANCE;
    }

    private static final DateJsonSerializer<java.sql.Date> SQL_DATE_INSTANCE = new DateJsonSerializer<java.sql.Date>() {
        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull java.sql.Date value, JsonSerializationContext ctx ) throws IOException {
            writer.value( value.toString() );
        }
    };

    /**
     * @return an instance of {@link DateJsonSerializer} that serialize {@link java.sql.Date}
     */
    public static DateJsonSerializer<java.sql.Date> getSqlDateInstance() {
        return SQL_DATE_INSTANCE;
    }

    private static final DateJsonSerializer<Time> SQL_TIME_INSTANCE = new DateJsonSerializer<Time>() {
        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull Time value, JsonSerializationContext ctx ) throws IOException {
            writer.value( value.toString() );
        }
    };

    /**
     * @return an instance of {@link DateJsonSerializer} that serialize {@link Time}
     */
    public static DateJsonSerializer<Time> getSqlTimeInstance() {
        return SQL_TIME_INSTANCE;
    }

    private static final DateJsonSerializer<Timestamp> SQL_TIMESTAMP_INSTANCE = new DateJsonSerializer<Timestamp>() {
        @Override
        protected void doSerialize( JsonWriter writer, @Nonnull Timestamp value, JsonSerializationContext ctx ) throws IOException {
            if ( ctx.isWriteDatesAsTimestamps() ) {
                writer.value( value.getTime() );
            } else {
                writer.value( DATE_FORMAT.format( value ) );
            }
        }
    };

    /**
     * @return an instance of {@link DateJsonSerializer} that serialize {@link Timestamp}
     */
    public static DateJsonSerializer<Timestamp> getSqlTimestampInstance() {
        return SQL_TIMESTAMP_INSTANCE;
    }
}
