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
public abstract class DateJsonDeserializer<D extends Date> extends JsonDeserializer<D> {

    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat( DateTimeFormat.PredefinedFormat.ISO_8601 );

    private static final DateTimeFormat SQL_DATE_FORMAT = DateTimeFormat.getFormat( "yyyy-MM-dd Z" );

    private static final DateJsonDeserializer<Date> DATE_INSTANCE = new DateJsonDeserializer<Date>() {

        @Override
        protected Date deserializeNumber( long millis ) {
            return new Date( millis );
        }

        @Override
        protected Date deserializeString( String date ) {
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
        protected java.sql.Date deserializeNumber( long millis ) {
            return new java.sql.Date( millis );
        }

        @Override
        protected java.sql.Date deserializeString( String date ) {
            Date d = SQL_DATE_FORMAT.parse( date + " +0000" );
            return new java.sql.Date( d.getTime() );
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
        protected Time deserializeNumber( long millis ) {
            return new Time( millis );
        }

        @Override
        protected Time deserializeString( String date ) {
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
        protected Timestamp deserializeNumber( long millis ) {
            return new Timestamp( millis );
        }

        @Override
        protected Timestamp deserializeString( String date ) {
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
