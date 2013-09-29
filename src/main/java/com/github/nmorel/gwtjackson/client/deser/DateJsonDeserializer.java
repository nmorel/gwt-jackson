package com.github.nmorel.gwtjackson.client.deser;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.JsonDecodingContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Base implementation of {@link com.github.nmorel.gwtjackson.client.JsonDeserializer} for dates.
 *
 * @author Nicolas Morel
 */
public abstract class DateJsonDeserializer<D extends Date> extends JsonDeserializer<D> {

    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 );

    private static final DateJsonDeserializer<Date> DATE_INSTANCE = new DateJsonDeserializer<Date>() {

        @Override
        protected Date decodeNumber( long millis ) {
            return new Date( millis );
        }

        @Override
        protected Date decodeString( String date ) {
            return DATE_FORMAT.parseStrict( date );
        }
    };

    /**
     * @return an instance of {@link DateJsonDeserializer} that deserialize {@link Date}
     */
    public static DateJsonDeserializer<Date> getDateInstance() {
        return DATE_INSTANCE;
    }

    private static final DateJsonDeserializer<java.sql.Date> SQL_DATE_INSTANCE = new DateJsonDeserializer<java.sql.Date>() {
        @Override
        protected java.sql.Date decodeNumber( long millis ) {
            return new java.sql.Date( millis );
        }

        @Override
        protected java.sql.Date decodeString( String date ) {
            return java.sql.Date.valueOf( date );
        }
    };

    /**
     * @return an instance of {@link DateJsonDeserializer} that deserialize {@link java.sql.Date}
     */
    public static DateJsonDeserializer<java.sql.Date> getSqlDateInstance() {
        return SQL_DATE_INSTANCE;
    }

    private static final DateJsonDeserializer<Time> SQL_TIME_INSTANCE = new DateJsonDeserializer<Time>() {
        @Override
        protected Time decodeNumber( long millis ) {
            return new Time( millis );
        }

        @Override
        protected Time decodeString( String date ) {
            return Time.valueOf( date );
        }
    };

    /**
     * @return an instance of {@link DateJsonDeserializer} that deserialize {@link Time}
     */
    public static DateJsonDeserializer<Time> getSqlTimeInstance() {
        return SQL_TIME_INSTANCE;
    }

    private static final DateJsonDeserializer<Timestamp> SQL_TIMESTAMP_INSTANCE = new DateJsonDeserializer<Timestamp>() {

        @Override
        protected Timestamp decodeNumber( long millis ) {
            return new Timestamp( millis );
        }

        @Override
        protected Timestamp decodeString( String date ) {
            return Timestamp.valueOf( date );
        }
    };

    /**
     * @return an instance of {@link DateJsonDeserializer} that deserialize {@link Timestamp}
     */
    public static DateJsonDeserializer<Timestamp> getSqlTimestampInstance() {
        return SQL_TIMESTAMP_INSTANCE;
    }

    @Override
    public D doDecode( JsonReader reader, JsonDecodingContext ctx ) throws IOException {
        if ( JsonToken.NUMBER.equals( reader.peek() ) ) {
            return decodeNumber( reader.nextLong() );
        } else {
            return decodeString( reader.nextString() );
        }
    }

    protected abstract D decodeNumber( long millis );

    protected abstract D decodeString( String date );
}
