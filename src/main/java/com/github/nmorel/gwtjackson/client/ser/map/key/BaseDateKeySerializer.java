package com.github.nmorel.gwtjackson.client.ser.map.key;

import javax.annotation.Nonnull;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.utils.DateFormat;

/**
 * Base implementation of {@link KeySerializer} for dates.
 *
 * @author Nicolas Morel
 */
public abstract class BaseDateKeySerializer<D extends Date> extends KeySerializer<D> {

    /**
     * Default implementation of {@link BaseDateKeySerializer} for {@link Date}
     */
    public static final class DateKeySerializer extends BaseDateKeySerializer<Date> {

        private static final DateKeySerializer INSTANCE = new DateKeySerializer();

        /**
         * @return an instance of {@link DateKeySerializer}
         */
        public static DateKeySerializer getInstance() {
            return INSTANCE;
        }

        private DateKeySerializer() { }
    }

    /**
     * Default implementation of {@link BaseDateKeySerializer} for {@link java.sql.Date}
     */
    public static final class SqlDateKeySerializer extends BaseDateKeySerializer<java.sql.Date> {

        private static final SqlDateKeySerializer INSTANCE = new SqlDateKeySerializer();

        /**
         * @return an instance of {@link SqlDateKeySerializer}
         */
        public static SqlDateKeySerializer getInstance() {
            return INSTANCE;
        }

        private SqlDateKeySerializer() { }
    }

    /**
     * Default implementation of {@link BaseDateKeySerializer} for {@link Time}
     */
    public static final class SqlTimeKeySerializer extends BaseDateKeySerializer<Time> {

        private static final SqlTimeKeySerializer INSTANCE = new SqlTimeKeySerializer();

        /**
         * @return an instance of {@link SqlTimeKeySerializer}
         */
        public static SqlTimeKeySerializer getInstance() {
            return INSTANCE;
        }

        private SqlTimeKeySerializer() { }
    }

    /**
     * Default implementation of {@link BaseDateKeySerializer} for {@link Timestamp}
     */
    public static final class SqlTimestampKeySerializer extends BaseDateKeySerializer<Timestamp> {

        private static final SqlTimestampKeySerializer INSTANCE = new SqlTimestampKeySerializer();

        /**
         * @return an instance of {@link SqlTimestampKeySerializer}
         */
        public static SqlTimestampKeySerializer getInstance() {
            return INSTANCE;
        }

        private SqlTimestampKeySerializer() { }
    }

    @Override
    protected String doSerialize( @Nonnull Date value, JsonSerializationContext ctx ) {
        if ( ctx.isWriteDateKeysAsTimestamps() ) {
            return Long.toString( value.getTime() );
        } else {
            return DateFormat.format( value );
        }
    }
}
