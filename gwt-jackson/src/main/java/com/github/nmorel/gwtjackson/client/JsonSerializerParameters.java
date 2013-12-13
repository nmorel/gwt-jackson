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

package com.github.nmorel.gwtjackson.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_LOCALE;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_TIMEZONE;

/**
 * This class includes parameters defined through properties annotations like {@link JsonFormat}. They are specific to one
 * {@link JsonSerializer} and that's why they are not contained inside {@link JsonSerializationContext}.
 * <p/>
 * For container serializers like {@link IterableJsonSerializer}, these parameters are passed to the value serializer.
 *
 * @author Nicolas Morel
 */
public class JsonSerializerParameters {

    public static final JsonSerializerParameters DEFAULT = new JsonSerializerParameters();

    /**
     * Datatype-specific additional piece of configuration that may be used
     * to further refine formatting aspects. This may, for example, determine
     * low-level format String used for {@link java.util.Date} serialization;
     * however, exact use is determined by specific <code>JsonSerializer</code>
     */
    private String pattern;

    /**
     * Structure to use for serialization: definition of mapping depends on datatype,
     * but usually has straight-forward counterpart in data format (JSON).
     * Note that commonly only a subset of shapes is available; and if 'invalid' value
     * is chosen, defaults are usually used.
     */
    private Shape shape = Shape.ANY;

    /**
     * {@link java.util.Locale} to use for serialization (if needed).
     * Special value of {@link com.fasterxml.jackson.annotation.JsonFormat#DEFAULT_LOCALE}
     * can be used to mean "just use the default", where default is specified
     * by the serialization context, which in turn defaults to system
     * defaults ({@link java.util.Locale#getDefault()}) unless explicitly
     * set to another locale.
     */
    private String locale = DEFAULT_LOCALE;

    /**
     * {@link java.util.TimeZone} to use for serialization (if needed).
     * Special value of {@link com.fasterxml.jackson.annotation.JsonFormat#DEFAULT_TIMEZONE}
     * can be used to mean "just use the default", where default is specified
     * by the serialization context, which in turn defaults to system
     * defaults ({@link java.util.TimeZone#getDefault()}) unless explicitly
     * set to another locale.
     */
    private String timezone = DEFAULT_TIMEZONE;

    /**
     * Names of properties to ignore.
     */
    private String[] ignoredProperties;

    /**
     * Inclusion rule to use.
     */
    private Include include = Include.ALWAYS;

    public String getPattern() {
        return pattern;
    }

    public Shape getShape() {
        return shape;
    }

    public String getLocale() {
        return locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public String[] getIgnoredProperties() {
        return ignoredProperties;
    }

    public Include getInclude() {
        return include;
    }
}
