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

package com.github.nmorel.gwtjackson.client.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;

/**
 * @author Nicolas Morel
 */
public final class DateFormat {

    private static class DateParser {

        protected final DateTimeFormat dateTimeFormat;

        protected DateParser( String pattern ) {
            this.dateTimeFormat = DateTimeFormat.getFormat( pattern );
        }

        protected Date parse( String date ) {
            return DateFormat.parse( dateTimeFormat, date );
        }
    }

    private static class DateParserNoTz extends DateParser {

        protected DateParserNoTz( String pattern ) {
            super( pattern + " Z" );
        }

        @Override
        protected Date parse( String date ) {
            return super.parse( date + " +0000" );
        }
    }

    /**
     * Defines a commonly used date format that conforms
     * to ISO-8601 date formatting standard, when it includes basic undecorated
     * timezone definition
     */
    public static final DateTimeFormat DATE_FORMAT_STR_ISO8601 = DateTimeFormat.getFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSZ" );

    /**
     * Same as 'regular' 8601, but handles 'Z' as an alias for "+0000"
     * (or "GMT")
     */
    public final static DateTimeFormat DATE_FORMAT_STR_ISO8601_Z = DateTimeFormat.getFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" );

    /**
     * ISO-8601 with just the Date part, no time
     */
    public final static DateTimeFormat DATE_FORMAT_STR_PLAIN = DateTimeFormat.getFormat( "yyyy-MM-dd" );

    /**
     * This constant defines the date format specified by
     * RFC 1123 / RFC 822.
     */
    public final static DateTimeFormat DATE_FORMAT_STR_RFC1123 = DateTimeFormat.getFormat( "EEE, dd MMM yyyy HH:mm:ss zzz" );

    /**
     * UTC TimeZone
     */
    public static final TimeZone UTC_TIMEZONE = TimeZone.createTimeZone( 0 );

    private static final Map<String, DateParser> CACHE_PARSERS = new HashMap<String, DateParser>();

    /**
     * Format a date using {@link #DATE_FORMAT_STR_ISO8601} and {@link #UTC_TIMEZONE}
     *
     * @param date date to format
     *
     * @return the formatted date
     */
    public static String format( Date date ) {
        return format( DateFormat.DATE_FORMAT_STR_ISO8601, DateFormat.UTC_TIMEZONE, date );
    }

    /**
     * Format a date using {@link JsonSerializerParameters} or default values : {@link #DATE_FORMAT_STR_ISO8601} and {@link #UTC_TIMEZONE}
     *
     * @param date date to format
     *
     * @return the formatted date
     */
    public static String format( JsonSerializerParameters params, Date date ) {
        DateTimeFormat format;
        if ( null == params.getPattern() ) {
            format = DateFormat.DATE_FORMAT_STR_ISO8601;
        } else {
            format = DateTimeFormat.getFormat( params.getPattern() );
        }

        TimeZone timeZone;
        if ( null == params.getTimezone() ) {
            timeZone = DateFormat.UTC_TIMEZONE;
        } else {
            timeZone = params.getTimezone();
        }

        return format( format, timeZone, date );
    }

    /**
     * Format a date using the {@link DateTimeFormat} given in parameter and {@link #UTC_TIMEZONE}.
     *
     * @param format format to use
     * @param date date to format
     *
     * @return the formatted date
     */
    public static String format( DateTimeFormat format, Date date ) {
        return format( format, UTC_TIMEZONE, date );
    }

    /**
     * Format a date using {@link #DATE_FORMAT_STR_ISO8601} and {@link TimeZone} given in parameter
     *
     * @param timeZone timezone to use
     * @param date date to format
     *
     * @return the formatted date
     */
    public static String format( TimeZone timeZone, Date date ) {
        return format( DateFormat.DATE_FORMAT_STR_ISO8601, timeZone, date );
    }

    /**
     * Format a date using the {@link DateTimeFormat} and {@link TimeZone} given in
     * parameters
     *
     * @param format format to use
     * @param timeZone timezone to use
     * @param date date to format
     *
     * @return the formatted date
     */
    public static String format( DateTimeFormat format, TimeZone timeZone, Date date ) {
        return format.format( date, timeZone );
    }

    /**
     * Parse a date using {@link #DATE_FORMAT_STR_ISO8601} and the browser timezone.
     *
     * @param date date to parse
     *
     * @return the parsed date
     */
    public static Date parse( String date ) {
        return parse( DateFormat.DATE_FORMAT_STR_ISO8601, date );
    }

    /**
     * Parse a date using the pattern given in parameter or {@link #DATE_FORMAT_STR_ISO8601} and the browser timezone.
     *
     * @param pattern pattern to use. If null, {@link #DATE_FORMAT_STR_ISO8601} will be used.
     * @param date date to parse
     *
     * @return the parsed date
     */
    public static Date parse( String pattern, String date ) {
        if ( null == pattern ) {
            return parse( DateFormat.DATE_FORMAT_STR_ISO8601, date );
        } else {
            DateParser parser = CACHE_PARSERS.get( pattern );
            if ( null == parser ) {
                if ( hasTz( pattern ) ) {
                    parser = new DateParser( pattern );
                } else {
                    // the pattern does not have a timezone, we use the UTC timezone as reference
                    parser = new DateParserNoTz( pattern );
                }
                CACHE_PARSERS.put( pattern, parser );
            }
            return parser.parse( date );
        }
    }

    /**
     * Find if a pattern contains informations about the timezone.
     *
     * @param pattern pattern
     *
     * @return true if the pattern contains informations about the timezone, false otherwise
     */
    private static boolean hasTz( String pattern ) {
        boolean inQuote = false;

        for ( int i = 0; i < pattern.length(); i++ ) {
            char ch = pattern.charAt( i );

            // If inside quote, except two quote connected, just copy or exit.
            if ( inQuote ) {
                if ( ch == '\'' ) {
                    if ( i + 1 < pattern.length() && pattern.charAt( i + 1 ) == '\'' ) {
                        // Quote appeared twice continuously, interpret as one quote.
                        ++i;
                    } else {
                        inQuote = false;
                    }
                }
                continue;
            }

            // Outside quote now.
            if ( "Zzv".indexOf( ch ) >= 0 ) {
                return true;
            }

            // Two consecutive quotes is a quote literal, inside or outside of quotes.
            if ( ch == '\'' ) {
                if ( i + 1 < pattern.length() && pattern.charAt( i + 1 ) == '\'' ) {
                    i++;
                } else {
                    inQuote = true;
                }
            }
        }

        return false;
    }

    /**
     * Parse a date using the {@link DateTimeFormat} given in
     * parameter and the browser timezone.
     *
     * @param format format to use
     * @param date date to parse
     *
     * @return the parsed date
     */
    public static Date parse( DateTimeFormat format, String date ) {
        return format.parseStrict( date );
    }

    private DateFormat() {}
}
