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

/**
 * This class includes parameters defined through properties annotations like {@link JsonIgnoreProperties}. They are specific to one
 * {@link JsonDeserializer} and that's why they are not contained inside {@link JsonDeserializationContext}.
 * <p>
 * For container deserializers like {@link ListJsonDeserializer}, these parameters are passed to the value deserializer.
 * </p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class JsonDeserializerParameters {

    /** Constant <code>DEFAULT</code> */
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
    private String locale;

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
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     */
    public JsonDeserializerParameters setPattern( String pattern ) {
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
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     */
    public JsonDeserializerParameters setShape( Shape shape ) {
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
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     */
    public JsonDeserializerParameters setLocale( String locale ) {
        this.locale = locale;
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
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     */
    public JsonDeserializerParameters addIgnoredProperty( String ignoredProperty ) {
        if ( null == ignoredProperties ) {
            ignoredProperties = new HashSet<String>();
        }
        ignoredProperties.add( ignoredProperty );
        return this;
    }

    /**
     * <p>isIgnoreUnknown</p>
     *
     * @return a boolean.
     */
    public boolean isIgnoreUnknown() {
        return ignoreUnknown;
    }

    /**
     * <p>Setter for the field <code>ignoreUnknown</code>.</p>
     *
     * @param ignoreUnknown a boolean.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     */
    public JsonDeserializerParameters setIgnoreUnknown( boolean ignoreUnknown ) {
        this.ignoreUnknown = ignoreUnknown;
        return this;
    }

    /**
     * <p>Getter for the field <code>identityInfo</code>.</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.deser.bean.IdentityDeserializationInfo} object.
     */
    public IdentityDeserializationInfo getIdentityInfo() {
        return identityInfo;
    }

    /**
     * <p>Setter for the field <code>identityInfo</code>.</p>
     *
     * @param identityInfo a {@link com.github.nmorel.gwtjackson.client.deser.bean.IdentityDeserializationInfo} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     */
    public JsonDeserializerParameters setIdentityInfo( IdentityDeserializationInfo identityInfo ) {
        this.identityInfo = identityInfo;
        return this;
    }

    /**
     * <p>Getter for the field <code>typeInfo</code>.</p>
     *
     * @return a {@link com.github.nmorel.gwtjackson.client.deser.bean.TypeDeserializationInfo} object.
     */
    public TypeDeserializationInfo getTypeInfo() {
        return typeInfo;
    }

    /**
     * <p>Setter for the field <code>typeInfo</code>.</p>
     *
     * @param typeInfo a {@link com.github.nmorel.gwtjackson.client.deser.bean.TypeDeserializationInfo} object.
     * @return a {@link com.github.nmorel.gwtjackson.client.JsonDeserializerParameters} object.
     */
    public JsonDeserializerParameters setTypeInfo( TypeDeserializationInfo typeInfo ) {
        this.typeInfo = typeInfo;
        return this;
    }
}
