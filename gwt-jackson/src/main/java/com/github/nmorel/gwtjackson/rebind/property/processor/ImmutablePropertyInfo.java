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

package com.github.nmorel.gwtjackson.rebind.property.processor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nmorel.gwtjackson.rebind.bean.BeanIdentityInfo;
import com.github.nmorel.gwtjackson.rebind.bean.BeanTypeInfo;
import com.github.nmorel.gwtjackson.rebind.property.FieldAccessor;
import com.github.nmorel.gwtjackson.rebind.property.PropertyInfo;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.base.Optional;

/**
 * @author Nicolas Morel
 */
final class ImmutablePropertyInfo implements PropertyInfo {

    private final String propertyName;

    private final JType type;

    private final boolean ignored;

    private final boolean required;

    private final boolean rawValue;

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

    ImmutablePropertyInfo( String propertyName, JType type, boolean ignored, boolean required, boolean rawValue,
                           Optional<String> managedReference, Optional<String> backReference,
                           Optional<? extends FieldAccessor> getterAccessor, Optional<? extends FieldAccessor> setterAccessor,
                           Optional<BeanIdentityInfo> identityInfo, Optional<BeanTypeInfo> typeInfo, Optional<JsonFormat> format,
                           Optional<Include> include, Optional<Boolean> ignoreUnknown, Optional<String[]> ignoredProperties ) {
        this.propertyName = propertyName;
        this.type = type;
        this.ignored = ignored;
        this.required = required;
        this.rawValue = rawValue;
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

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public JType getType() {
        return type;
    }

    @Override
    public boolean isIgnored() {
        return ignored;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean isRawValue() {
        return rawValue;
    }

    @Override
    public Optional<String> getManagedReference() {
        return managedReference;
    }

    @Override
    public Optional<String> getBackReference() {
        return backReference;
    }

    @Override
    public Optional<? extends FieldAccessor> getGetterAccessor() {
        return getterAccessor;
    }

    @Override
    public Optional<? extends FieldAccessor> getSetterAccessor() {
        return setterAccessor;
    }

    @Override
    public Optional<BeanIdentityInfo> getIdentityInfo() {
        return identityInfo;
    }

    @Override
    public Optional<BeanTypeInfo> getTypeInfo() {
        return typeInfo;
    }

    @Override
    public Optional<JsonFormat> getFormat() {
        return format;
    }

    @Override
    public Optional<Include> getInclude() {
        return include;
    }

    @Override
    public Optional<Boolean> getIgnoreUnknown() {
        return ignoreUnknown;
    }

    @Override
    public Optional<String[]> getIgnoredProperties() {
        return ignoredProperties;
    }
}
