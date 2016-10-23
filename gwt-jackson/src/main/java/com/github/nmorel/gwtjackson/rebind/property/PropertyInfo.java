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
 * <p>PropertyInfo class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
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

    /**
     * <p>Getter for the field <code>propertyName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     */
    public JType getType() {
        return type;
    }

    /**
     * <p>isIgnored</p>
     *
     * @return a boolean.
     */
    public boolean isIgnored() {
        return ignored;
    }

    /**
     * <p>isRequired</p>
     *
     * @return a boolean.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * <p>isRawValue</p>
     *
     * @return a boolean.
     */
    public boolean isRawValue() {
        return rawValue;
    }

    /**
     * <p>isValue</p>
     *
     * @return a boolean.
     */
    public boolean isValue() {
        return value;
    }

    /**
     * <p>isAnyGetter</p>
     *
     * @return a boolean.
     */
    public boolean isAnyGetter() {
        return anyGetter;
    }

    /**
     * <p>isAnySetter</p>
     *
     * @return a boolean.
     */
    public boolean isAnySetter() {
        return anySetter;
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
     * <p>Getter for the field <code>managedReference</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<String> getManagedReference() {
        return managedReference;
    }

    /**
     * <p>Getter for the field <code>backReference</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<String> getBackReference() {
        return backReference;
    }

    /**
     * <p>Getter for the field <code>getterAccessor</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<? extends FieldAccessor> getGetterAccessor() {
        return getterAccessor;
    }

    /**
     * <p>Getter for the field <code>setterAccessor</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<? extends FieldAccessor> getSetterAccessor() {
        return setterAccessor;
    }

    /**
     * <p>Getter for the field <code>identityInfo</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<BeanIdentityInfo> getIdentityInfo() {
        return identityInfo;
    }

    /**
     * <p>Getter for the field <code>typeInfo</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<BeanTypeInfo> getTypeInfo() {
        return typeInfo;
    }

    /**
     * <p>Getter for the field <code>format</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<JsonFormat> getFormat() {
        return format;
    }

    /**
     * <p>Getter for the field <code>include</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<Include> getInclude() {
        return include;
    }

    /**
     * <p>Getter for the field <code>ignoreUnknown</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<Boolean> getIgnoreUnknown() {
        return ignoreUnknown;
    }

    /**
     * <p>Getter for the field <code>ignoredProperties</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.base.Optional} object.
     */
    public Optional<String[]> getIgnoredProperties() {
        return ignoredProperties;
    }
}
