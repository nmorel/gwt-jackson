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

package com.github.nmorel.gwtjackson.rebind.bean;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

/**
 * <p>BeanTypeInfo class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class BeanTypeInfo {

    private final Id use;

    private final As include;

    private final String propertyName;

    private final ImmutableMap<JClassType, String> mapTypeToSerializationMetadata;

    private final ImmutableMap<JClassType, String> mapTypeToDeserializationMetadata;

    BeanTypeInfo( Id use, As include, String propertyName, ImmutableMap<JClassType, String> mapTypeToSerializationMetadata,
                  ImmutableMap<JClassType, String> mapTypeToDeserializationMetadata ) {
        this.use = use;
        this.include = include;
        this.propertyName = propertyName;
        this.mapTypeToSerializationMetadata = mapTypeToSerializationMetadata;
        this.mapTypeToDeserializationMetadata = mapTypeToDeserializationMetadata;
    }

    /**
     * <p>Getter for the field <code>use</code>.</p>
     *
     * @return a {@link com.fasterxml.jackson.annotation.JsonTypeInfo.Id} object.
     */
    public Id getUse() {
        return use;
    }

    /**
     * <p>Getter for the field <code>include</code>.</p>
     *
     * @return a {@link com.fasterxml.jackson.annotation.JsonTypeInfo.As} object.
     */
    public As getInclude() {
        return include;
    }

    /**
     * <p>Getter for the field <code>propertyName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * <p>Getter for the field <code>mapTypeToSerializationMetadata</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.collect.ImmutableMap} object.
     */
    public ImmutableMap<JClassType, String> getMapTypeToSerializationMetadata() {
        return mapTypeToSerializationMetadata;
    }

    /**
     * <p>Getter for the field <code>mapTypeToDeserializationMetadata</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.collect.ImmutableMap} object.
     */
    public ImmutableMap<JClassType, String> getMapTypeToDeserializationMetadata() {
        return mapTypeToDeserializationMetadata;
    }
}
