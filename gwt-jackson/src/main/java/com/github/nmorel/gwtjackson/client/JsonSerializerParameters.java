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

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.bean.IdentitySerializationInfo;
import com.github.nmorel.gwtjackson.client.ser.bean.TypeSerializationInfo;
import com.google.gwt.i18n.client.TimeZone;

/**
 * This class includes parameters defined through properties annotations like {@link JsonFormat}. They are specific to one
 * {@link JsonSerializer} and that's why they are not contained inside {@link JsonSerializationContext}.
 * <p>
 * For container serializers like {@link IterableJsonSerializer}, these parameters are passed to the value serializer.
 * </p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class JsonSerializerParameters {

    /** Constant <code>DEFAULT</code> */
    public static final JsonSerializerParameters DEFAULT = new JsonSerializerParameters();

    /**
     * Datatype-specific additional piece of configuration that may be used
     * to further refine formatting aspects. This may, for example, determine
     * low-level format String used for {@link java.util.Date} serialization;
     * however, exact use is determined by specific {@link JsonSerializer}
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
     * Locale to use for serialization (if needed).
     */
    private String locale;

    /**
     * Timezone to use for serialization (if needed).
     */
    private TimeZone timezone;

    /**
     * Names of properties to ignore.
     */
    private Set<String> ignoredProperties;

    /**
     * Inclusion rule to use.
     */
    private Include include;

    /**
     * Bean identity informations
     */
    private IdentitySerializationInfo identityInfo;

    /**
     * Bean type informations
     */
    private TypeSerializationInfo typeInfo;

    /**
     * If true, all the properties of an object will be serialized inside the current object.
     */
    private boolean unwrapped = false;

    /**
     * <p>Getter for the field <code>pattern</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * <p>Setter for the field <code>pattern</code>.</p>
     *
     * @param pattern a {@link java.lang.String} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters setPattern( String pattern ) {
        this.pattern = pattern;
        return this;
    }

    /**
     * <p>Getter for the field <code>shape</code>.</p>
     *
     * @return a {@link com.fasterxml.jackson.annotation.JsonFormat.Shape} object.
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * <p>Setter for the field <code>shape</code>.</p>
     *
     * @param shape a {@link com.fasterxml.jackson.annotation.JsonFormat.Shape} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters setShape( Shape shape ) {
        this.shape = shape;
        return this;
    }

    /**
     * <p>Getter for the field <code>locale</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getLocale() {
        return locale;
    }

    /**
     * <p>Setter for the field <code>locale</code>.</p>
     *
     * @param locale a {@link java.lang.String} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters setLocale( String locale ) {
        this.locale = locale;
        return this;
    }

    /**
     * <p>Getter for the field <code>timezone</code>.</p>
     *
     * @return a {@link com.google.gwt.i18n.client.TimeZone} object.
     */
    public TimeZone getTimezone() {
        return timezone;
    }

    /**
     * <p>Setter for the field <code>timezone</code>.</p>
     *
     * @param timezone a {@link com.google.gwt.i18n.client.TimeZone} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters setTimezone( TimeZone timezone ) {
        this.timezone = timezone;
        return this;
    }

    /**
     * <p>Getter for the field <code>ignoredProperties</code>.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    public Set<String> getIgnoredProperties() {
        return ignoredProperties;
    }

    /**
     * <p>addIgnoredProperty</p>
     *
     * @param ignoredProperty a {@link java.lang.String} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters addIgnoredProperty( String ignoredProperty ) {
        if ( null == ignoredProperties ) {
            ignoredProperties = new HashSet<String>();
        }
        ignoredProperties.add( ignoredProperty );
        return this;
    }

    /**
     * <p>Getter for the field <code>include</code>.</p>
     *
     * @return a {@link com.fasterxml.jackson.annotation.JsonInclude.Include} object.
     */
    public Include getInclude() {
        return include;
    }

    /**
     * <p>Setter for the field <code>include</code>.</p>
     *
     * @param include a {@link com.fasterxml.jackson.annotation.JsonInclude.Include} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters setInclude( Include include ) {
        this.include = include;
        return this;
    }

    /**
     * <p>Getter for the field <code>identityInfo</code>.</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.ser.bean.IdentitySerializationInfo} object.
     */
    public IdentitySerializationInfo getIdentityInfo() {
        return identityInfo;
    }

    /**
     * <p>Setter for the field <code>identityInfo</code>.</p>
     *
     * @param identityInfo a {@link com.github.nmorel.gwtjackson.client.ser.bean.IdentitySerializationInfo} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters setIdentityInfo( IdentitySerializationInfo identityInfo ) {
        this.identityInfo = identityInfo;
        return this;
    }

    /**
     * <p>Getter for the field <code>typeInfo</code>.</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.ser.bean.TypeSerializationInfo} object.
     */
    public TypeSerializationInfo getTypeInfo() {
        return typeInfo;
    }

    /**
     * <p>Setter for the field <code>typeInfo</code>.</p>
     *
     * @param typeInfo a {@link com.github.nmorel.gwtjackson.client.ser.bean.TypeSerializationInfo} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters setTypeInfo( TypeSerializationInfo typeInfo ) {
        this.typeInfo = typeInfo;
        return this;
    }

    /**
     * <p>isUnwrapped</p>
     *
     * @return a boolean.
     */
    public boolean isUnwrapped() {
        return unwrapped;
    }

    /**
     * <p>Setter for the field <code>unwrapped</code>.</p>
     *
     * @param unwrapped a boolean.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonSerializerParameters} object.
     */
    public JsonSerializerParameters setUnwrapped( boolean unwrapped ) {
        this.unwrapped = unwrapped;
        return this;
    }
}
