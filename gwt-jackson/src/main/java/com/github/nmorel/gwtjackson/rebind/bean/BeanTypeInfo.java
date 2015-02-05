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
 * @author Nicolas Morel
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

    public Id getUse() {
        return use;
    }

    public As getInclude() {
        return include;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public ImmutableMap<JClassType, String> getMapTypeToSerializationMetadata() {
        return mapTypeToSerializationMetadata;
    }

    public ImmutableMap<JClassType, String> getMapTypeToDeserializationMetadata() {
        return mapTypeToDeserializationMetadata;
    }
}
