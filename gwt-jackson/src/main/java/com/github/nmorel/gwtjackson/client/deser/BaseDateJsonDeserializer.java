/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.deser;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import com.github.nmorel.gwtjackson.client.JsonDeserializationContext;
import com.github.nmorel.gwtjackson.client.JsonDeserializer;
import com.github.nmorel.gwtjackson.client.JsonDeserializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.JsonToken;
import com.github.nmorel.gwtjackson.client.utils.DateFormat;

/**
 * Base implementation of {@link JsonDeserializer} for dates.
 *
 * @author Nicolas Morel
 */
public abstract class BaseDateJsonDeserializer<D extends Date> extends JsonDeserializer<D> {

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
        protected Date deserializeNumber( long millis, JsonDeserializerParameters params ) {
            return new Date( millis );
        }

        @Override
        protected Date deserializeString( String date, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return DateFormat.parse(ctx.isAdjustDatesToContextTimeZone(), params.getPattern(), date );
        }
    }

    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

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
        protected java.sql.Date deserializeNumber( long millis, JsonDeserializerParameters params ) {
            return new java.sql.Date( millis );
        }

        @Override
        protected java.sql.Date deserializeString( String date, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return new java.sql.Date( DateFormat.parse(ctx.isAdjustDatesToContextTimeZone(), SQL_DATE_FORMAT, date).getTime() );
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
        protected Time deserializeNumber( long millis, JsonDeserializerParameters params ) {
            return new Time( millis );
        }

        @Override
        protected Time deserializeString( String date, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
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
        protected Timestamp deserializeNumber( long millis, JsonDeserializerParameters params ) {
            return new Timestamp( millis );
        }

        @Override
        protected Timestamp deserializeString( String date, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
            return new Timestamp( DateFormat.parse(ctx.isAdjustDatesToContextTimeZone(), params.getPattern(), date ).getTime() );
        }
    }

    @Override
    public D doDeserialize( JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        if ( params.getShape().isNumeric() || JsonToken.NUMBER.equals( reader.peek() ) ) {
            return deserializeNumber( reader.nextLong(), params );
        } else {
            return deserializeString( reader.nextString(), ctx, params );
        }
    }

    protected abstract D deserializeNumber( long millis, JsonDeserializerParameters params );

    protected abstract D deserializeString( String date, JsonDeserializationContext ctx, JsonDeserializerParameters params );
}
