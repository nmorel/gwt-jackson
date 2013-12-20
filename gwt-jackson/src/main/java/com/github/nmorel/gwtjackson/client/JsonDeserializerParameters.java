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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nmorel.gwtjackson.client.deser.bean.IdentityDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.bean.TypeDeserializationInfo;
import com.github.nmorel.gwtjackson.client.deser.collection.ListJsonDeserializer;

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
     * Names of properties to ignore.
     */
    private String[] ignoredProperties;

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

    public String[] getIgnoredProperties() {
        return ignoredProperties;
    }

    public JsonDeserializerParameters setIgnoredProperties( String[] ignoredProperties ) {
        this.ignoredProperties = ignoredProperties;
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
