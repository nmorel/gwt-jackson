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

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nmorel.gwtjackson.client.deser.bean.IdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.bean.TypeDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.collection.ListJsonDeserializer;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_LOCALE;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_TIMEZONE;

/**
 * This class includes parameters defined through properties annotations like {@link JsonIgnoreProperties}. They are specific to one
 * {@link JsonDeserializer} and that's why they are not contained inside {@link JsonDeserializationContext}.
 * <p/>
 * For container deserializers like {@link ListJsonDeserializer}, these parameters are passed to the value deserializer.
 *
 * @author Nicolas Morel
 */
public final class JsonDeserializerParameters {

    public static final JsonDeserializerParameters DEFAULT = new JsonDeserializerParameters();

    /**
     * Datatype-specific additional piece of configuration that may be used
     * to further refine formatting aspects. This may, for example, determine
     * low-level format String used for {@link java.util.Date} serialization;
     * however, exact use is determined by specific {@link JsonDeserializer}
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
     * Locale to use for deserialization (if needed).
     */
    private String locale = DEFAULT_LOCALE;

    /**
     * TimeZone to use for deserialization (if needed).
     */
    private String timezone = DEFAULT_TIMEZONE;

    /**
     * Names of properties to ignore.
     */
    private Set<String> ignoredProperties;

    /**
     * Property that defines whether it is ok to just ignore any
     * unrecognized properties during deserialization.
     * If true, all properties that are unrecognized -- that is,
     * there are no setters or creators that accept them -- are
     * ignored without warnings (although handlers for unknown
     * properties, if any, will still be called) without
     * exception.
     */
    private boolean ignoreUnknown = false;

    /**
     * Bean identity informations
     */
    private IdentityDeserializationInfo identityInfo;

    /**
     * Bean type informations
     */
    private TypeDeserializationInfo typeInfo;

    public String getPattern() {
        return pattern;
    }

    public JsonDeserializerParameters setPattern( String pattern ) {
        this.pattern = pattern;
        return this;
    }

    public Shape getShape() {
        return shape;
    }

    public JsonDeserializerParameters setShape( Shape shape ) {
        this.shape = shape;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public JsonDeserializerParameters setLocale( String locale ) {
        this.locale = locale;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public JsonDeserializerParameters setTimezone( String timezone ) {
        this.timezone = timezone;
        return this;
    }

    public Set<String> getIgnoredProperties() {
        return ignoredProperties;
    }

    public JsonDeserializerParameters addIgnoredProperty( String ignoredProperty ) {
        if ( null == ignoredProperties ) {
            ignoredProperties = new HashSet<String>();
        }
        ignoredProperties.add( ignoredProperty );
        return this;
    }

    public boolean isIgnoreUnknown() {
        return ignoreUnknown;
    }

    public JsonDeserializerParameters setIgnoreUnknown( boolean ignoreUnknown ) {
        this.ignoreUnknown = ignoreUnknown;
        return this;
    }

    public IdentityDeserializationInfo getIdentityInfo() {
        return identityInfo;
    }

    public JsonDeserializerParameters setIdentityInfo( IdentityDeserializationInfo identityInfo ) {
        this.identityInfo = identityInfo;
        return this;
    }

    public TypeDeserializationInfo getTypeInfo() {
        return typeInfo;
    }

    public JsonDeserializerParameters setTypeInfo( TypeDeserializationInfo typeInfo ) {
        this.typeInfo = typeInfo;
        return this;
    }
}
