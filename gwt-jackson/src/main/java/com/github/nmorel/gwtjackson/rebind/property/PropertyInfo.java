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

package com.github.nmorel.gwtjackson.rebind.property;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.rebind.bean.BeanIdentityInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanTypeInfo;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;

/**
 * @author Nicolas Morel
 */
public final class PropertyInfo {

    private final String propertyName;

    private final JType type;

    private final boolean ignored;

    private final boolean required;

    private final boolean rawValue;

    private final boolean value;

    private final boolean anyGetter;

    private final boolean anySetter;

    private final boolean unwrapped;

    private final Optional<String> managedReference;

    private final Optional<String> backReference;

    private final Optional<? extends FieldAccessor> getterAccessor;

    private final Optional<? extends FieldAccessor> setterAccessor;

    private final Optional<BeanIdentityInfo> identityInfo;

    private final Optional<BeanTypeInfo> typeInfo;

    private final Optional<JsonFormat> format;

    private final Optional<Include> include;

    private final Optional<Boolean> ignoreUnknown;

    private final Optional<String[]> ignoredProperties;

    PropertyInfo( String propertyName, JType type, boolean ignored, boolean required, boolean rawValue, boolean value, boolean
            anyGetter, boolean anySetter, boolean unwrapped, Optional<String> managedReference, Optional<String> backReference,
                  Optional<? extends
                          FieldAccessor> getterAccessor, Optional<? extends FieldAccessor> setterAccessor, Optional<BeanIdentityInfo>
            identityInfo,
                  Optional<BeanTypeInfo> typeInfo, Optional<JsonFormat> format, Optional<Include> include, Optional<Boolean>
                          ignoreUnknown, Optional<String[]> ignoredProperties ) {
        this.propertyName = propertyName;
        this.type = type;
        this.ignored = ignored;
        this.required = required;
        this.rawValue = rawValue;
        this.value = value;
        this.anyGetter = anyGetter;
        this.anySetter = anySetter;
        this.unwrapped = unwrapped;
        this.managedReference = managedReference;
        this.backReference = backReference;
        this.getterAccessor = getterAccessor;
        this.setterAccessor = setterAccessor;
        this.identityInfo = identityInfo;
        this.typeInfo = typeInfo;
        this.format = format;
        this.include = include;
        this.ignoreUnknown = ignoreUnknown;
        this.ignoredProperties = ignoredProperties;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public JType getType() {
        return type;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isRawValue() {
        return rawValue;
    }

    public boolean isValue() {
        return value;
    }

    public boolean isAnyGetter() {
        return anyGetter;
    }

    public boolean isAnySetter() {
        return anySetter;
    }

    public boolean isUnwrapped() {
        return unwrapped;
    }

    public Optional<String> getManagedReference() {
        return managedReference;
    }

    public Optional<String> getBackReference() {
        return backReference;
    }

    public Optional<? extends FieldAccessor> getGetterAccessor() {
        return getterAccessor;
    }

    public Optional<? extends FieldAccessor> getSetterAccessor() {
        return setterAccessor;
    }

    public Optional<BeanIdentityInfo> getIdentityInfo() {
        return identityInfo;
    }

    public Optional<BeanTypeInfo> getTypeInfo() {
        return typeInfo;
    }

    public Optional<JsonFormat> getFormat() {
        return format;
    }

    public Optional<Include> getInclude() {
        return include;
    }

    public Optional<Boolean> getIgnoreUnknown() {
        return ignoreUnknown;
    }

    public Optional<String[]> getIgnoredProperties() {
        return ignoredProperties;
    }
}
