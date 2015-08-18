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

package com.github.nmorel.gwtjackson.client.ser;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;
import com.github.nmorel.gwtjackson.client.utils.DateFormat;

/**
 * Base implementation of {@link JsonSerializer} for dates.
 *
 * @author Nicolas Morel
 */
public abstract class BaseDateJsonSerializer<D extends Date> extends JsonSerializer<D> {

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
        protected void doSerialize( JsonWriter writer, @Nonnull Date value, JsonSerializationContext ctx, JsonSerializerParameters params ) {
            if ( (ctx.isWriteDatesAsTimestamps() || params.getShape().isNumeric()) && params.getShape() != Shape.STRING ) {
                writer.value( value.getTime() );
            } else {
                String date = DateFormat.format( params, value );
                if ( null == params.getPattern() ) {
                    writer.unescapeValue( date );
                } else {
                    writer.value( date );
                }
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
        protected void doSerialize( JsonWriter writer, @Nonnull java.sql.Date value, JsonSerializationContext ctx,
                                    JsonSerializerParameters params ) {
            writer.unescapeValue( value.toString() );
        }
    }

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
        protected void doSerialize( JsonWriter writer, @Nonnull Time value, JsonSerializationContext ctx, JsonSerializerParameters params
        ) {
            writer.unescapeValue( value.toString() );
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
        protected void doSerialize( JsonWriter writer, @Nonnull Timestamp value, JsonSerializationContext ctx, JsonSerializerParameters
                params ) {
            if ( ctx.isWriteDatesAsTimestamps() || params.getShape().isNumeric() ) {
                writer.value( value.getTime() );
            } else {
                String date = DateFormat.format( params, value );
                if ( null == params.getPattern() ) {
                    writer.unescapeValue( date );
                } else {
                    writer.value( date );
                }
            }
        }
    }

    @Override
    protected boolean isEmpty( @Nullable D value ) {
        return null == value || value.getTime() == 0l;
    }
}
