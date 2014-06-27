/*
 * Copyright 2014 Nicolas Morel
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
 * @author Nicolas Morel.
 */
final class PropertyInfoBuilder {

    private final String propertyName;

    private final JType type;

    private boolean ignored = false;

    private boolean required = false;

    private boolean rawValue = false;

    private Optional<String> managedReference = Optional.absent();

    private Optional<String> backReference = Optional.absent();

    private Optional<? extends FieldAccessor> getterAccessor = Optional.absent();

    private Optional<? extends FieldAccessor> setterAccessor = Optional.absent();

    private Optional<BeanIdentityInfo> identityInfo = Optional.absent();

    private Optional<BeanTypeInfo> typeInfo = Optional.absent();

    private Optional<JsonFormat> format = Optional.absent();

    private Optional<Include> include = Optional.absent();

    private Optional<Boolean> ignoreUnknown = Optional.absent();

    private Optional<String[]> ignoredProperties = Optional.absent();

    PropertyInfoBuilder( String propertyName, JType type ) {
        this.propertyName = propertyName;
        this.type = type;
    }

    String getPropertyName() {
        return propertyName;
    }

    JType getType() {
        return type;
    }

    boolean isIgnored() {
        return ignored;
    }

    void setIgnored( boolean ignored ) {
        this.ignored = ignored;
    }

    boolean isRequired() {
        return required;
    }

    void setRequired( boolean required ) {
        this.required = required;
    }

    boolean isRawValue() {
        return rawValue;
    }

    void setRawValue( boolean rawValue ) {
        this.rawValue = rawValue;
    }

    Optional<String> getManagedReference() {
        return managedReference;
    }

    void setManagedReference( Optional<String> managedReference ) {
        this.managedReference = managedReference;
    }

    Optional<String> getBackReference() {
        return backReference;
    }

    void setBackReference( Optional<String> backReference ) {
        this.backReference = backReference;
    }

    Optional<? extends FieldAccessor> getGetterAccessor() {
        return getterAccessor;
    }

    void setGetterAccessor( Optional<? extends FieldAccessor> getterAccessor ) {
        this.getterAccessor = getterAccessor;
    }

    Optional<? extends FieldAccessor> getSetterAccessor() {
        return setterAccessor;
    }

    void setSetterAccessor( Optional<? extends FieldAccessor> setterAccessor ) {
        this.setterAccessor = setterAccessor;
    }

    Optional<BeanIdentityInfo> getIdentityInfo() {
        return identityInfo;
    }

    void setIdentityInfo( Optional<BeanIdentityInfo> identityInfo ) {
        this.identityInfo = identityInfo;
    }

    Optional<BeanTypeInfo> getTypeInfo() {
        return typeInfo;
    }

    void setTypeInfo( Optional<BeanTypeInfo> typeInfo ) {
        this.typeInfo = typeInfo;
    }

    Optional<JsonFormat> getFormat() {
        return format;
    }

    void setFormat( Optional<JsonFormat> format ) {
        this.format = format;
    }

    Optional<Include> getInclude() {
        return include;
    }

    void setInclude( Optional<Include> include ) {
        this.include = include;
    }

    Optional<Boolean> getIgnoreUnknown() {
        return ignoreUnknown;
    }

    void setIgnoreUnknown( Optional<Boolean> ignoreUnknown ) {
        this.ignoreUnknown = ignoreUnknown;
    }

    Optional<String[]> getIgnoredProperties() {
        return ignoredProperties;
    }

    void setIgnoredProperties( Optional<String[]> ignoredProperties ) {
        this.ignoredProperties = ignoredProperties;
    }

    PropertyInfo build() {
        return new ImmutablePropertyInfo( propertyName, type, ignored, required, rawValue, managedReference, backReference,
                getterAccessor, setterAccessor, identityInfo, typeInfo, format, include, ignoreUnknown, ignoredProperties );
    }
}
